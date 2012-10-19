package net.sciencecraft.src;

import java.util.EnumSet;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler 
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.equals(EnumSet.of(TickType.SERVER)))
		{
			MinecraftServer server = MinecraftServer.getServer();
			onServerTick(server);
		}
	}

	public void onServerTick(MinecraftServer server) 
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{

	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.WORLD, TickType.SERVER);
	}

	@Override
	public String getLabel() 
	{
		return null;
	}

}
