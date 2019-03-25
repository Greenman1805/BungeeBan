package de.greenman1805.bungeeban;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
	public static Main plugin;
	public static String prefix = "§8[§9BungeeBan§8] §f";
	public static String datafolder = "plugins//BungeeBan";
	File file = new File(Main.datafolder, "bans.yml");
	public static Configuration bans;

	public static HashMap<String, UUID> bannedIPs = new HashMap<String, UUID>();

	public void onEnable() {
		plugin = this;
		checkPluginDirectory();

		ProxyServer.getInstance().getPluginManager().registerListener(this, new LoginListener());

		ProxyServer.getInstance().getPluginManager().registerCommand(this, new BanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new BanlistCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new BaninfoCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnbanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new KickCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new TempbanCommand());

		try {
			bans = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void onDisable() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(bans, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkPluginDirectory() {
		File file1 = new File(datafolder);
		File file2 = new File(datafolder, "bans.yml");

		if (!file1.isDirectory()) {
			file1.mkdir();
		}

		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
