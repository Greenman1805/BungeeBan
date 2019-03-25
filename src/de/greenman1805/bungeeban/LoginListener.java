package de.greenman1805.bungeeban;

import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

	@EventHandler
	public void PreLoginEvent(LoginEvent e) {
		String ip = e.getConnection().getAddress().getAddress().getHostAddress();
		UUID uuid = e.getConnection().getUniqueId();

		if (BanAPI.isBanned(uuid)) {
			String reason = BanAPI.getReason(uuid);
			e.setCancelReason(new TextComponent("§cGebannt:\n§f" + reason));
			e.setCancelled(true);
		} else if (BanAPI.isTempBanned(uuid)) {
			String reason = BanAPI.getReason(uuid);
			long until = BanAPI.getUntil(uuid);
			long timeleft = (until - System.currentTimeMillis());
			int minutes = (int) ((timeleft / 1000) / 60);
			int days = minutes / (24 * 60);
			minutes -= days * (24 * 60);
			int hours = minutes / 60;
			minutes -= hours * 60;
			e.setCancelReason(new TextComponent("§cDu bist noch " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) gebannt!\n§cGrund: §f" + reason));
			e.setCancelled(true);

		} else if (Main.bannedIPs.containsKey(ip)) {
			e.setCancelReason(new TextComponent("§cDeine IP ist vom Netzwerk gebannt!"));
			e.setCancelled(true);
			BanAPI.sendBanMessage(Main.prefix + "§cEin §c2. §cAccount §f" + e.getConnection().getName() + " §cvon §f" + UUIDs.getName(Main.bannedIPs.get(ip)) + " §7(" + ip + ") §chat §csich §cversucht §ceinzuloggen!");
		}
	}

}
