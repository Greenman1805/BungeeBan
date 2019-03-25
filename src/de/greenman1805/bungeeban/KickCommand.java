package de.greenman1805.bungeeban;

import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand() {
		super("kick");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length >= 2) {
				String name = args[0];
				if (UUIDs.hasEntry(name)) {
					UUID uuid = UUIDs.getUUID(name);
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);

					if (p != null) {
						if (!p.hasPermission("bungeeban.exempt")) {

							String reason = "";
							for (int i = 1; i <= args.length - 1; i++) {
								if (i == 1) {
									reason = args[i];
								} else {
									reason = reason + " " + args[i];
								}
							}
							BanAPI.sendBanMessage("§6" + p.getName() + " wurde gekickt [" + reason + "]");
							BanAPI.kickPlayer(p, "§cGekickt: §f" + reason);

						} else {
							sender.sendMessage(new TextComponent(Main.prefix + "§cDu kannst diesen Spieler nicht kicken!"));
						}

					} else {
						sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler ist nicht online!"));
					}
				} else {
					sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler nicht gefunden!"));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.prefix + "/kick <spieler> <grund>"));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));
		}
	}
}
