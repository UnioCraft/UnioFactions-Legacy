package com.massivecraft.factions.integration;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;

import me.crafter.mc.lockettepro.LocketteProAPI;

public class LocketteProFeatures 
{
	private static boolean lockette = false;

	public static void setup()
	{
		Plugin lockettePlugin = Bukkit.getServer().getPluginManager().getPlugin("LockettePro");
		if(lockettePlugin == null || !lockettePlugin.isEnabled()) return;
		
		lockette = true;
		
		P.p.log("Successfully hooked into LockettePro!");
	}

	public static boolean getEnabled()
	{
		return lockette;
	}

	public static boolean canPlayerInteractFactions(Player player, Block block){
		if (LocketteProAPI.isLocked(block) && LocketteProAPI.isUser(block, player))	{
			Faction blockFaction = Board.getFactionAt(new FLocation(block));
			Faction playerFaction = FPlayers.i.get(player).getFaction();

			if (playerFaction == null) {
				return false;
			}else {
				if (playerFaction == blockFaction) {
					return true;
				}else {
					return false;
				}
			}
		}else {
			return false;
		}
	}
}
