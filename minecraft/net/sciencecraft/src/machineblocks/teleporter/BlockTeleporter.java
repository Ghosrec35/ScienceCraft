package net.sciencecraft.src.machineblocks.teleporter;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockMachineTeleporter 
{
	TileEntityTeleporter te = new TileEntityTeleporter();
	
	public BlockTeleporter(String name, int par1, Teleporter teleporter) 
	{
		super(name, par1, Material.rock, teleporter);
	}
	
	@Override
	public void onTeleport() 
	{		
		Minecraft.getMinecraft().thePlayer.setPosition(te.xCoord, te.yCoord + 2, te.zCoord);
	}

	@Override
	public int timeToTeleportation() 
	{
		return 10;
	}

	@Override
	public Teleporter createNewTeleporter() 
	{
		return TeleportationManager.getManagerInstance().newTeleporter(TeleportationManager.getManagerInstance().getNextFreeTeleportationID(), te).registerTeleporter();
	}
	
    @Override
    public TileEntity createNewTileEntity(World var1)
    {
    	return te;
    }
}
