package de.greenman1805.bungeeban;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.greenman1805.uuids.UUIDs;

public class Banlist {
	private List<String> names = new ArrayList<String>();

	public Banlist() {
		for (String id : Main.bans.getKeys()) {
			UUID uuid = UUID.fromString(id);
			if (BanAPI.isBanned(uuid)) {
				names.add("§c" + UUIDs.getName(uuid));
			} else if (BanAPI.isTempBanned(uuid)) {
				names.add("§6" + UUIDs.getName(uuid));
			}
		}
	}

	public String getBannedString() {
		String banned = "";
		for (String name : names) {
			banned += name + " ";
		}
		return banned;
	}

	public int getCount() {
		return names.size();
	}

}
