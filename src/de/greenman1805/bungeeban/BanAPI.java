package de.greenman1805.bungeeban;

import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class BanAPI {

	public static void setBanned(UUID uuid, String reason, String ip) {
		Main.bans.set(uuid + ".ban", true);
		Main.bans.set(uuid + ".tempban", false);
		Main.bans.set(uuid + ".until", null);
		Main.bans.set(uuid + ".reason", reason);
		if (ip != null) {
			Main.bannedIPs.put(ip, uuid);
		}
	}
	
	public static void setTempBanned(UUID uuid, long minutes, String reason) {
		long until = (System.currentTimeMillis() + (minutes * 60 * 1000));
		Main.bans.set(uuid + ".tempban", true);
		Main.bans.set(uuid + ".ban", false);
		Main.bans.set(uuid + ".reason", reason);
		Main.bans.set(uuid + ".until", until);
	}

	public static void sendBanMessage(String message) {
		for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
			if (pp.hasPermission("bungeeban.admin")) {
				pp.sendMessage(new TextComponent(message));
			}
		}
	}


	public static void setUnbanned(UUID uuid) {
		Main.bans.set(uuid.toString(), null);
	}

	public static void kickPlayer(ProxiedPlayer p, String reason) {
		p.disconnect(new TextComponent(reason));
	}

	public static String getReason(UUID uuid) {
		String reason = Main.bans.getString(uuid + ".reason");
		return reason;
	}

	public static long getUntil(UUID uuid) {
		long until = Main.bans.getLong(uuid + ".until");
		return until;
	}

	public static Boolean isBanned(UUID uuid) {
		Boolean banned = Main.bans.getBoolean(uuid + ".ban");
		return banned;
	}

	public static Boolean isTempBanned(UUID uuid) {
		long until = getUntil(uuid);
		long timeleft = (until - System.currentTimeMillis());

		if (timeleft <= 0) {
			BanAPI.setUnbanned(uuid);
			return false;
		}
		Boolean tempbanned = Main.bans.getBoolean(uuid + ".tempban");
		return tempbanned;
	}

}
