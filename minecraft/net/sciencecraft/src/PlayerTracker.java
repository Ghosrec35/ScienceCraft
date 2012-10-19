package net.sciencecraft.src;

import net.minecraft.src.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker 
{
	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		player.openGui(ScienceCraft.instance(), 100, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		player.addChatMessage("Science must continue!");
	}
}
