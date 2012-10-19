package net.sciencecraft.src.machineblocks.teleporter;

public class Teleporter 
{
	public final int teleporterID;
	private boolean isInstantaneous;
	
	public int xCoord, yCoord, zCoord;
	
	public Teleporter(int teleporterTypeID, int x, int y, int z)
	{
		if(TeleportationManager.getManagerInstance().getTeleporterInstance(teleporterTypeID) != null)
		{
			throw new IllegalArgumentException("Teleporter ID:" + teleporterTypeID + " has already been used!");
		}else
		{
			teleporterID = teleporterTypeID;
			isInstantaneous = false;
			this.xCoord = x;
			this.yCoord = y;
			this.zCoord = z;
		}
	}
	
	public Teleporter setInstantaneous(boolean b)
	{
		isInstantaneous = b;
		return this;
	}
	
	public Teleporter registerTeleporter()
	{
		TeleportationManager.getManagerInstance().registerTeleportor(this);
		return this;
	}
	//Far from complete, but just a placeholder for usage right now
}
