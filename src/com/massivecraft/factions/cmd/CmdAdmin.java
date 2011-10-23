package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Rel;

public class CmdAdmin extends FCommand
{	
	public CmdAdmin()
	{
		super();
		this.aliases.add("admin");
		
		this.requiredArgs.add("player name");
		//this.optionalArgs.put("", "");
		
		this.permission = Permission.ADMIN.node;
		this.disableOnLock = true;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = true;
	}
	
	@Override
	public void perform()
	{
		FPlayer fyou = this.argAsBestFPlayerMatch(0);
		if (fyou == null) return;
		
		if (fyou.getFaction() != myFaction)
		{
			msg("%s<i> is not a member in your faction.", fyou.describeTo(fme, true));
			return;
		}
		
		if (fyou == fme)
		{
			msg("<b>The target player musn't be yourself.");
			return;
		}
		
		fme.setRole(Rel.OFFICER);
		fyou.setRole(Rel.LEADER);
		
		// Inform all players
		for (FPlayer fplayer : FPlayers.i.getOnline())
		{
			fplayer.msg("%s<i> gave %s<i> the leadership of %s", fme.describeTo(fplayer, true), fyou.describeTo(fplayer), myFaction.describeTo(fplayer));
		}
	}
	
}
