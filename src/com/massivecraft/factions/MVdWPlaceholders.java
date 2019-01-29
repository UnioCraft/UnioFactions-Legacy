package com.massivecraft.factions;

import org.bukkit.entity.Player;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class MVdWPlaceholders {

	public MVdWPlaceholders() {
		PlaceholderAPI.registerPlaceholder(P.p, "uniofactions_online_member_count", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				Player p = e.getPlayer();
				
				if (p != null) {
					FPlayer fplayer = FPlayers.i.get(p);
					Faction faction = fplayer.getFaction();
					
					if (fplayer == null || faction == null) {
						return "0";
					}
					
					if (faction.isNone()) {
						return "1";
					}
					
					return String.valueOf(faction.getOnlinePlayers().size());
				}else {
					return "0";
				}
				
			}
		});
		
		PlaceholderAPI.registerPlaceholder(P.p, "uniofactions_power", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				Player p = e.getPlayer();
				
				if (p != null) {
					FPlayer fplayer = FPlayers.i.get(p);
					
					if (fplayer == null) {
						return "0";
					}
					
					return String.valueOf((int)Math.round(fplayer.getPower()));
				}else {
					return "0";
				}
				
			}
		});
		
		PlaceholderAPI.registerPlaceholder(P.p, "uniofactions_faction_power", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				Player p = e.getPlayer();
				
				if (p != null) {
					FPlayer fplayer = FPlayers.i.get(p);
					Faction faction = fplayer.getFaction();
					
					if (fplayer == null || faction == null) {
						return "0";
					}
					
					if (faction.isNone()) {
						return "0";
					}
					
					return String.valueOf((int)faction.getPower());
				}else {
					return "0";
				}
				
			}
		});
		
		PlaceholderAPI.registerPlaceholder(P.p, "uniofactions_faction_name", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
				Player p = e.getPlayer();
				
				if (p != null) {
					FPlayer fplayer = FPlayers.i.get(p);
					Faction faction = fplayer.getFaction();
					
					if (fplayer == null || faction == null) {
						return "Yok";
					}
					
					if (faction.isNone()) {
						return "Yok";
					}
					
					return faction.getTag();
				}else {
					return "Yok";
				}
				
			}
		});
	}

}