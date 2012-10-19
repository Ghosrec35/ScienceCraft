package net.sciencecraft.src.machineblocks.teleporter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraftforge.common.ForgeDirection;

import universalelectricity.electricity.TileEntityElectricUnit;

public class TileEntityTeleporter extends TileEntityElectricUnit
{
	private static final TileEntityTeleporter teleporterBase = new TileEntityTeleporter();
	
	public static final TileEntityTeleporter instance()
	{
		return teleporterBase;
	}
	
	public TileEntityTeleporter()
	{
		
	}
	
	@Override
	public float electricityRequest() 
	{
		return 0;
	}

	@Override
	public boolean canReceiveFromSide(ForgeDirection side) 
	{
		return side == ForgeDirection.getOrientation(this.blockMetadata).getOpposite();
	}
}
