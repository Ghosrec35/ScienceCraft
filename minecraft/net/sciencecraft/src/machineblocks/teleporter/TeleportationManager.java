package net.sciencecraft.src.machineblocks.teleporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

public class TeleportationManager 
{
	private static Map<Integer, ITeleportationManager> teleporterMapRegister = new HashMap<Integer, ITeleportationManager>();
	private static List<ISpecialTeleporter> specialTeleporters = new ArrayList<ISpecialTeleporter>();
	private static final TeleportationManager instance = new TeleportationManager();

	private static int teleportationID = 0;
	
	private TeleportationManager(){/*Added to disallow outside instantiation of new TeleportationManager instances*/}
	
	public static TeleportationManager getManagerInstance()
	{
		return instance;
	}
	
	public static Teleporter normalTeleporter = newTeleporter(TeleportationManager.getManagerInstance().getNextFreeTeleportationID(), new TileEntityTeleporter()).setInstantaneous(true);
	
	public void addTeleporter(int teleporterID, ITeleportationManager teleporterInstance)
	{
		teleporterMapRegister.put(teleporterID, teleporterInstance);
	}
	
	public ITeleportationManager getTeleporterInstance(int teleporterID)
	{
		try
		{
			return (ITeleportationManager) teleporterMapRegister.get(teleporterID);
		}
		catch (Exception e)
		{
			e = new Exception("Attempted to get an instance of a teleporter from the Teleporter HashMap that does not exist! This is a bug!");
		}
		return null;
	}
	
	public void removeTeleporter(int teleporterID)
	{
		try
		{
			teleporterMapRegister.remove(teleporterID);
		}
		catch(Exception e)
		{
			e = new Exception("Attempted to remove a teleportation instance from the Teleporter HashMap which was already null!");
		}
	}
	
	public void onTeleport(int teleporterID)
	{
		if(teleporterMapRegister.get(teleporterID) != null)
		{
			((ITeleportationManager)teleporterMapRegister.get(teleporterID)).onTeleport();
			
			if(teleporterMapRegister.get(teleporterID) instanceof ISpecialTeleporter)
			{
				onSpecialTeleport(((ITeleportationManager)teleporterMapRegister.get(teleporterID)));
			}
		}
		else
		{
			throw new NullPointerException("The teleporter ID you attempted to use to retrieve the teleporter instance from the teleporter HashMap is invalid! This is either a bug involving forgetting to add the Teleporter mapping to the HashMap, or you attempted to access the incorrect teleporter ID!");
		}
	}
	
	public void onSpecialTeleport(ITeleportationManager teleporter)
	{
		((ISpecialTeleporter)teleporter).onSpecialTeleport();
	}
	
	public void registerSpecialTeleporter(ISpecialTeleporter teleporter)
	{
		if(!specialTeleporters.contains(teleporter))
		{
			specialTeleporters.add(teleporter);
		}
	}
	
	public int getTimeToTeleportation(int teleporterID)
	{
		return ((ITeleportationManager)teleporterMapRegister.get(teleporterID)).timeToTeleportation();
	}
	
	public static Teleporter newTeleporter(int teleporterID, TileEntity te)
	{
		return new Teleporter(teleporterID, te.xCoord, te.yCoord, te.zCoord);
	}
	
	public int getNextFreeTeleportationID()
	{
		if(this.getTeleporterInstance(teleportationID) != null)
		{
			teleportationID++;
			return this.getNextFreeTeleportationID();
		}else
		{
			return teleportationID;
		}
	}

	public void registerTeleportor(Teleporter teleporter) 
	{
		this.teleporterMapRegister.put(teleporter.teleporterID, (ITeleportationManager) teleporter);
	}
}
