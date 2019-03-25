package de.greenman1805.bungeeban;

import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

	public BanCommand() {
		super("ban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length >= 2) {
				String name = args[0];
				if (UUIDs.hasEntry(name)) {
					UUID uuid = UUIDs.getUUID(name);
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);
					Boolean banned = BanAPI.isBanned(uuid);

					String reason = "";
					for (int i = 1; i <= args.length - 1; i++) {
						if (i == 1) {
							reason = args[i];
						} else {
							reason = reason + " " + args[i];
						}
					}

					if (banned == false) {
						if (p != null) {
							if (!p.hasPermission("bungeeban.exempt")) {
								String ip = p.getAddress().getAddress().getHostAddress();
								BanAPI.setBanned(uuid, reason, ip);
								BanAPI.kickPlayer(p, "§cGebannt:\n§f" + reason);

								BanAPI.sendBanMessage("§4" + p.getName() + " wurde gebannt [" + reason + "]");
								sender.sendMessage(new TextComponent(Main.prefix + "§fDie IP Adresse §7" + ip + " §fwurde zusätzlich gebannt."));
							} else {
								sender.sendMessage(new TextComponent(Main.prefix + "§cDu kannst diesen Spieler nicht bannen!"));
							}
						} else {
							BanAPI.setBanned(uuid, reason, null);
							ProxyServer.getInstance().broadcast(new TextComponent("§4" + UUIDs.getName(uuid) + " wurde gebannt [" + reason + "]"));
						}
					} else {
						sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler ist bereits gebannt!"));
					}

				} else {
					sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler nicht gefunden!"));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.prefix + "/ban <spieler> <grund>"));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));
		}
	}
}
