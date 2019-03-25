package de.greenman1805.bungeeban;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BanlistCommand extends Command {

	public BanlistCommand() {
		super("banlist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("bungeeban.admin")) {
			if (args.length == 0) {
				Banlist b = new Banlist();
				if (b.getCount() == 0) {
					sender.sendMessage(new TextComponent("§cKeine gebannten Spieler!"));
				} else {
					sender.sendMessage(new TextComponent("§9" + b.getCount() + " gebannte(r) Spieler:"));
					sender.sendMessage(new TextComponent(b.getBannedString()));
				}
			}
		} else {
			sender.sendMessage(new TextComponent(Main.prefix + "§cKeine Permissions!"));
		}
	}
}
