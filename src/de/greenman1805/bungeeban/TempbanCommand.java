package de.greenman1805.bungeeban;

import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TempbanCommand extends Command {

	public TempbanCommand() {
		super("tempban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length >= 3) {
				String name = args[0];
				if (UUIDs.hasEntry(name)) {
					UUID uuid = UUIDs.getUUID(name);
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);

					String reason = "";
					for (int i = 2; i <= args.length - 1; i++) {
						if (i == 2) {
							reason = args[i];
						} else {
							reason = reason + " " + args[i];
						}
					}

					if (!BanAPI.isBanned(uuid)) {
						String time = args[1].toLowerCase();
						if (time.endsWith("m") || time.endsWith("h") || time.endsWith("d")) {
							String unit = time.substring(time.length() - 1);
							int value = Integer.parseInt(time.substring(0, time.length() - 1));
							if (p != null) {
								if (!p.hasPermission("bungeeban.exempt")) {
									if (unit.equalsIgnoreCase("m")) {
										BanAPI.setTempBanned(uuid, value, reason);
										BanAPI.sendBanMessage("§4Spieler " + p.getName() + " §4wurde für " + value + " Minute(n) gebannt [" + reason + "]");
										BanAPI.kickPlayer(p, "§cDu wurdest für " + value + " Minute(n) gebannt!\n§cGrund: §f" + reason);
									}
									if (unit.equalsIgnoreCase("h")) {
										BanAPI.setTempBanned(uuid, value * 60, reason);
										BanAPI.sendBanMessage("§4Spieler " + p.getName() + " §4wurde für " + value + " Stunde(n) gebannt [" + reason + "]");
										BanAPI.kickPlayer(p, "§cDu wurdest für " + value + " Stunde(n) gebannt!\n§cGrund: §f" + reason);
									}
									if (unit.equalsIgnoreCase("d")) {
										BanAPI.setTempBanned(uuid, value * 1440, reason);
										BanAPI.sendBanMessage("§4Spieler " + p.getName() + " §4wurde für " + value + " Tag(e) gebannt [" + reason + "]");
										BanAPI.kickPlayer(p, "§cDu wurdest für " + value + " Tag(e) gebannt!\n§cGrund: §f" + reason);
									}
								} else {
									sender.sendMessage(new TextComponent(Main.prefix + "§cDu kannst diesen Spieler nicht bannen!"));
								}
							} else {
								if (unit.equalsIgnoreCase("m")) {
									BanAPI.setTempBanned(uuid, value, reason);
									BanAPI.sendBanMessage("§4Spieler " + UUIDs.getName(uuid) + " §4wurde für " + value + " Minute(n) gebannt [" + reason + "]");
								}
								if (unit.equalsIgnoreCase("h")) {
									BanAPI.setTempBanned(uuid, value * 60, reason);
									BanAPI.sendBanMessage("§4Spieler " + UUIDs.getName(uuid) + " §4wurde für " + value + " Stunde(n) gebannt [" + reason + "]");
								}
								if (unit.equalsIgnoreCase("d")) {
									BanAPI.setTempBanned(uuid, value * 1440, reason);
									BanAPI.sendBanMessage("§4Spieler " + UUIDs.getName(uuid) + " §4wurde für " + value + " Tag(e) gebannt [" + reason + "]");
								}
							}
						} else {
							sender.sendMessage(new TextComponent(Main.prefix + "§cFalsche Zeitangabe!"));
						}
					} else {
						sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler ist bereits permanent gebannt!"));
					}
				} else {
					sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler nicht gefunden!"));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.prefix + "/tempban <spieler> <zeit> <grund>"));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));
		}
	}
}
