package net.sciencecraft.src.blocks;

import java.util.ArrayList;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockHydrogen extends Block
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockHydrogen(int i, int j)
	{
		super(i, j, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public boolean isAirBlock(World world, int x, int y, int z)
	{
		return true;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }
	
	 public void onBlockAdded(World world, int x, int y, int z)
	    {
	        if (world.getBlockMaterial(x - 1, y, z) == Material.fire || world.getBlockId(x - 1, y, z) == Block.torchWood.blockID)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x + 1, y, z) == Material.fire || world.getBlockId(x + 1, y, z) == Block.torchWood.blockID)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y - 1, z) == Material.fire || world.getBlockId(x, y - 1, z) == Block.torchWood.blockID)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y + 1, z) == Material.fire || world.getBlockId(x, y + 1, z) == Block.torchWood.blockID) 
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y, z - 1) == Material.fire || world.getBlockId(x, y, z - 1) == Block.torchWood.blockID)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y, z + 1) == Material.fire || world.getBlockId(x, y, z + 1) == Block.torchWood.blockID)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        super.onBlockAdded(world, x, y, z);
	    }
	
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        if (par5 > 0 && Block.blocksList[par5].blockMaterial == Material.fire)
        {
            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
            world.createExplosion((Entity)null, x, y, z, 4.0F);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }
    
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityplayer)
    {
        if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == Item.flintAndSteel.shiftedIndex)
        {
            world.createExplosion((Entity)null, x, y, z, 4.0F);
            world.setBlockWithNotify(x, y, z, 0);
            entityplayer.getCurrentEquippedItem().damageItem(4, entityplayer);
        }
        return true;
    }
}
