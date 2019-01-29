package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.struct.Permission;

public class CmdUnOwnerAll extends FCommand
{	
	public CmdUnOwnerAll()
	{
		this.aliases.add("unownerall");
		
		//this.requiredArgs.add("");
		//this.optionalArgs.put("", "");
		
		this.permission = Permission.OWNER.node;
		this.disableOnLock = true;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = Conf.ownedAreasModeratorsCanSet ? true : false;
		senderMustBeAdmin = Conf.ownedAreasModeratorsCanSet ? false : true;
	}
	
	@Override
	public void perform()
	{
		Board.unOwnerAll(fme, fme.getFactionId());
		myFaction.msg("%s<i> klandaki tüm alanlara unowner attı.", fme.describeTo(myFaction, true));
	}
	
}
