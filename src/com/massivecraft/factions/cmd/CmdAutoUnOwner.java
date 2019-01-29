package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;

public class CmdAutoUnOwner extends FCommand
{
	public CmdAutoUnOwner()
	{
		super();
		this.aliases.add("autounowner");
		
		//this.requiredArgs.add("");
		this.optionalArgs.put("faction", "your");
		
		this.permission = Permission.OWNER.node;
		this.disableOnLock = true;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform()
	{
		if (fme.getAutoUnOwnerEnabled())
		{
			fme.setAutoUnOwnerEnabled(false);
			msg("<i>Otomatik claim sahipliği salma devre dışı!");
			return;
		}
		
		boolean hasBypass = fme.isAdminBypassing();
		
		if ( ! hasBypass && ! assertHasFaction()) {
			return;
		}

		if ( ! Conf.ownedAreasEnabled)
		{
			fme.msg("<b>Üzgünüm, ancak sahipli alanlar bu sunucuda devre dışı.");
			return;
		}

		if ( ! hasBypass && Conf.ownedAreasLimitPerFaction > 0 && myFaction.getCountOfClaimsWithOwners() >= Conf.ownedAreasLimitPerFaction)
		{
			fme.msg("<b>Sunucunun klan başına sahipli alan limitini aştın. Maksimum <h>%d <b>sahipli alanın olabilir.", Conf.ownedAreasLimitPerFaction);
			return;
		}

		if ( ! hasBypass && !assertMinRole(Conf.ownedAreasModeratorsCanSet ? Role.MODERATOR : Role.ADMIN))
		{
			return;
		}
		
		fme.setAutoUnOwnerEnabled(true);
		
		msg("<i>Otomatik claim sahipliği salma etkin!");
		fme.attemptUnOwner(new FLocation(fme), false);
	}
	
}