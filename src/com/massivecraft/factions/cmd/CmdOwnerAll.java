package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.struct.Permission;

public class CmdOwnerAll extends FCommand
{	
	public CmdOwnerAll()
	{
		this.aliases.add("ownerall");
		
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
		Board.ownerAll(fme, fme.getFactionId());
		myFaction.msg("%s<i> klandaki tüm alanlara owner attı.", fme.describeTo(myFaction, true));
	}
	
}
