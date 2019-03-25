package de.greenman1805.bungeeban;

import java.util.UUID;

import de.greenman1805.uuids.UUIDs;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BaninfoCommand extends Command {

	public BaninfoCommand() {
		super("baninfo");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length == 1) {
				String playername = args[0];
				if (UUIDs.hasEntry(playername)) {
					UUID uuid = UUIDs.getUUID(playername);
					if (BanAPI.isBanned(uuid) == true) {
						sender.sendMessage(new TextComponent("§7Infos über §a" + UUIDs.getName(uuid) + "§7:"));
						sender.sendMessage(new TextComponent("§7Gebannt: §cJa"));
						sender.sendMessage(new TextComponent("§7Zeit: §cpermanent"));
						sender.sendMessage(new TextComponent("§7Grund: §2" + BanAPI.getReason(uuid)));
					} else if (BanAPI.isTempBanned(uuid)) {
						long until = BanAPI.getUntil(uuid);
						long timeleft = (until - System.currentTimeMillis());
						int minutes = (int) ((timeleft / 1000) / 60);
						int days = minutes / (24 * 60);
						minutes -= days * (24 * 60);
						int hours = minutes / 60;
						minutes -= hours * 60;

						sender.sendMessage(new TextComponent("§7Infos über §a" + UUIDs.getName(uuid) + "§7:"));
						sender.sendMessage(new TextComponent("§7Gebannt: §cJa"));
						sender.sendMessage(new TextComponent("§7Zeit: §2" + days + " Tage " + hours + " Stunden " + minutes + " Minuten"));
						sender.sendMessage(new TextComponent("§7Grund: §2" + BanAPI.getReason(uuid)));
					} else {
						sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler ist nicht gebannt!"));
					}

				} else {
					sender.sendMessage(new TextComponent(Main.prefix + "§cSpieler nicht gefunden!"));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.prefix + "/baninfo <spieler>"));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));

		}
	}
}
