package com.massivecraft.factions.cmd;

import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;

public class CmdAutoUnclaim extends FCommand
{
	public CmdAutoUnclaim()
	{
		super();
		this.aliases.add("autounclaim");
		
		//this.requiredArgs.add("");
		this.optionalArgs.put("faction", "your");
		
		this.permission = Permission.AUTOUNCLAIM.node;
		this.disableOnLock = true;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform()
	{
		if (fme.getAutoUnClaimEnabled())
		{
			fme.setAutoUnClaimEnabled(false);
			msg("<i>Otomatik claim bırakma devre dışı!");
			return;
		}
		
		if ( ! assertHasFaction())
		{
			return;
		}
		
		if ( ! assertMinRole(Role.MODERATOR))
		{
			return;
		}
		
		fme.setAutoUnClaimEnabled(true);
		
		msg("<i>Otomatik claim bırakma etkin!");
		me.getPlayer().performCommand("f unclaim");
	}
	
}