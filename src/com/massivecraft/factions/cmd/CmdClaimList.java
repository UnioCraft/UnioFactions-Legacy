package com.massivecraft.factions.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Permission;


public class CmdClaimList extends FCommand
{

	public CmdClaimList()
	{
		super();
		this.aliases.add("claimlist");

		//this.requiredArgs.add("");
		this.optionalArgs.put("page", "1");

		this.permission = Permission.CLAIMLIST.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = true;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}	

	@Override
	public void perform()
	{
		Faction faction = fme.getFaction();
		if (!fme.hasFaction()) {
			sendMessage("<b>Bu komutu kullanabilmek için bir klanda olmalısınız.");
			return;
		}
		List<FLocation> claims = new ArrayList<FLocation>();
		claims.addAll(Board.getKeysByValue(Board.getFlocations(), faction.getId()));

		final int pageheight = 9;
		int pagenumber = this.argAsInt(0, 1);
		int pagecount = (claims.size() / pageheight) + 1;
		if (pagenumber > pagecount)
			pagenumber = pagecount;
		else if (pagenumber < 1)
			pagenumber = 1;
		int start = (pagenumber - 1) * pageheight;
		int end = start + pageheight;
		if (end > claims.size())
			end = claims.size();

		List<String> lines = new ArrayList<String>();
		lines.add(p.txt.titleize("Claim Listesi "+pagenumber+"/"+pagecount));

		for (FLocation claim : claims.subList(start, end))
		{
			lines.add(ChatColor.DARK_RED + ""+ChatColor.BOLD + "> "+ChatColor.RESET+ChatColor.DARK_RED+"XYZ: "+ChatColor.RED +(claim.getX() << 4) + ", 60, " + (claim.getZ() << 4));
		}
		if (pagenumber == 1) {
			if (claims.size() > 9) {
				lines.add(ChatColor.GOLD + "Sonraki sayfaya geçmek için "+ ChatColor.YELLOW + "/f claimlist 2"+ChatColor.GOLD+" yazın.");
			}
		}

		sendMessage(lines);
	}
}

