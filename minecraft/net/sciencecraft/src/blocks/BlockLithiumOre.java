package net.sciencecraft.src.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.sciencecraft.src.ScienceCraft;

public class BlockLithiumOre extends Block 
{
	private Minecraft mc = Minecraft.getMinecraft();
	
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockLithiumOre(int i, int j)
	{
		super(i, j, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	 public void onBlockAdded(World world, int x, int y, int z)
	    {
	        super.onBlockAdded(world, x, y, z);

	        if (world.getBlockMaterial(x - 1, y, z) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x + 1, y, z) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y - 1, z) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y + 1, z) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y, z - 1) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	        
	        if (world.getBlockMaterial(x, y, z + 1) == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	    }

	    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
	    {
	        if (par5 > 0 && Block.blocksList[par5].blockMaterial == Material.water)
	        {
	            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	        }
	    }

	    public int quantityDropped(Random par1Random)
	    {
	        return 0;
	    }

	    public void onBlockDestroyedByExplosion(World par1World, int x, int y, int z)
	    {
	        if (!par1World.isRemote)
	        {
	            EntityTNTPrimed var5 = new EntityTNTPrimed(par1World, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F));
	            var5.fuse = par1World.rand.nextInt(var5.fuse / 4) + var5.fuse / 8;
	            par1World.spawnEntityInWorld(var5);
	        }
	    }

	    public void onBlockDestroyedByPlayer(World par1World, int x, int y, int z, int par5)
	    {
	        if (!par1World.isRemote && !mc.thePlayer.capabilities.isCreativeMode)
	        {
	            if ((par5 & 1) == 0)
	            {
	               this.dropBlockAsItem_do(par1World, x, y, z, new ItemStack(ScienceCraft.registry.lithiumOre));
	            }
	            else
	            if(!par1World.isRemote && mc.thePlayer.capabilities.isCreativeMode)
	            {
	            	par1World.setBlockWithNotify(x, y, z, 0);
	            }
	            else
	            {
	                par1World.playSoundEffect(x, y, z, "random.fuse", 1.0F, 1.0F);
	            }
	        }
	    }


	    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer entityplayer)
	    {
	        super.onBlockClicked(world, x, y, z, entityplayer);
	    }

	    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityplayer)
	    {
	        if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().itemID == Item.bucketWater.shiftedIndex)
	        {
	            world.createExplosion((Entity)null, x, y, z, 4.0F);
	            world.setBlockWithNotify(x, y, z, 0);
	            entityplayer.inventorySlots.inventoryItemStacks.remove(Item.bucketWater);
	            entityplayer.inventorySlots.inventoryItemStacks.add(Item.bucketEmpty);
	        }
	        return true;
	    }

	    protected ItemStack createStackedBlock(int par1)
	    {
	        return null;
	    }
	    
	    public void addCreativeItems(ArrayList itemList)
        {
	    	itemList.add(new ItemStack(this));
        }
}
