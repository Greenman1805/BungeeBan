package de.greenman1805.bungeeban;

import java.util.Map.Entry;
import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

	public UnbanCommand() {
		super("unban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length == 1) {
				if (UUIDs.hasEntry(args[0])) {
					UUID uuid = UUIDs.getUUID(args[0]);
					Boolean banned = BanAPI.isBanned(uuid);
					Boolean tempbanned = BanAPI.isTempBanned(uuid);

					if ((banned == true) || (tempbanned == true)) {
						String playername = UUIDs.getName(uuid);
						BanAPI.setUnbanned(uuid);
						BanAPI.sendBanMessage("§a" + playername + " wurde entbannt!");

						for (Entry<String, UUID> entry : Main.bannedIPs.entrySet()) {
							if (entry.getValue().toString().equalsIgnoreCase(uuid.toString())) {
								Main.bannedIPs.remove(entry.getKey());
							}
						}
					} else {
						sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler ist nicht gebannt!"));
					}
				} else {
					sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler nicht gefunden!"));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.prefix + "/unban <spieler>"));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));
		}
	}
}
