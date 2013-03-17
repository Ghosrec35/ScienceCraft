package net.sciencecraft.src.machineblocks.teleporter;

import net.minecraftforge.common.ForgeDirection;

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
