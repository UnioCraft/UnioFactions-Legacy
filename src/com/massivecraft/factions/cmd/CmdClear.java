package com.massivecraft.factions.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;

import codes.biscuit.chunkbuster.ChunkBuster;

public class CmdClear extends FCommand
{
	private ChunkBuster chunkBuster;
	private List<String> confirmWaitList = new ArrayList<>();
	
	public CmdClear()
	{
		this.aliases.add("clear");

		this.permission = Permission.CLEAR.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
		
		chunkBuster = (ChunkBuster) Bukkit.getPluginManager().getPlugin("chunkBuster");
	}

	@Override
	public void perform()
	{
		if (chunkBuster == null) {
			msg("<b>Claim temizleme sistemi geçici olarak devre dışıdır.");
			return;
		}
		FLocation flocation = new FLocation(fme);
		Faction factionAtLocation = Board.getFactionAt(flocation);

		if (factionAtLocation.isSafeZone() || factionAtLocation.isWarZone() || factionAtLocation.isNone())
		{
			msg("<b>Sadece kendi claimlerinizi temizleyebilirsiniz.");
			return;
		}
		
		if ( ! fme.hasFaction())
		{
			msg("<b>Bunun için bir klanda olmalısınız.");
			return;
		}

		if (!fme.getRole().equals(Role.ADMIN))
		{
			msg("<b>Bunun için klan lideri olmalısınız.");
			return;
		}


		if ( myFaction != factionAtLocation)
		{
			msg("<b>Sadece kendi claimlerinizi temizleyebilirsiniz.");
			return;
		}

		int cost = 1000000000;
		if ( ! Econ.hasAtLeast(fme, cost, null)) {
			msg("<b>Claim temizleme işlemini karşılamak için paranız yeterli değil!");
			msg("<b>Claim temizleme işlemi 1 milyar TL (1,000,000,000) değerindedir!");
			return;
		}
		
		if (!confirmWaitList.contains(fme.getName())) {
			confirmWaitList.add(fme.getName());
			msg(ChatColor.DARK_RED + "" + ChatColor.BOLD + "DİKKAT -> " + ChatColor.RESET + "<b>Claim temizleme işlemini için hesabınızdan 1 milyar TL (1,000,000,000) para alınacaktır!");
			msg(ChatColor.DARK_RED + "" + ChatColor.BOLD + "DİKKAT -> " + ChatColor.RESET + "<b>Ayrıca claimde bulunan tüm bloklar, sandıklar ve eşyalar silinecektir!");
			msg(ChatColor.DARK_RED + "" + ChatColor.BOLD + "DİKKAT -> " + ChatColor.RESET + "<b>Claimi temizleme işlemini onaylamak için lütfen tekrar \"/f clear\" komutunu kullanın!");
			Bukkit.getScheduler().runTaskLater(P.p, () -> {
				confirmWaitList.remove(fme.getName());
			}, 200L);
			return;
		}
		confirmWaitList.remove(fme.getName());
		
		if (!Econ.modifyMoney(fme, -cost, null, null)) {
			msg("<b>Claim temizleme işlemini karşılamak için paranız yeterli değil! Claim temizleme işlemi 1 milyar TL (1,000,000,000) değerindedir!");
			return;
		}
		
		chunkBuster.bustChunk(fme.getPlayer(), fme.getPlayer().getLocation());
		msg("<i>Claim temizleme işlemi için hesabınızdan 1 milyar TL alındı!");
		
		P.p.log(fme.getName()+" cleared land at ("+flocation.getCoordString()+") from the faction: "+factionAtLocation.getTag());
			
	}

}
