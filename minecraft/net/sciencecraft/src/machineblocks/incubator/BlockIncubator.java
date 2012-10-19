package net.sciencecraft.src.machineblocks.incubator;

import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;
import net.sciencecraft.src.ScienceCraft;

public class BlockIncubator extends BlockContainer
{
	private Random random = new Random();
	
	private final boolean isActive;
	
	public static boolean keepIncubatorInventory = false;
	
	public BlockIncubator(int i, boolean b) 
	{
		super(i, Material.rock);
		isActive = b;
		blockIndexInTexture = 33;
		this.setCreativeTab(CreativeTabs.tabDeco);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityIncubator();
	}
	
	public int idDropped(int i, Random random, int j)
	{
		return ScienceCraft.registry.incubatorInactive.blockID;
	}
	
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(World world, int x, int y, int z) 
	{
		if(!world.isRemote)
		{
			int blockID = world.getBlockId(x, y, - z);
			int blockID2 = world.getBlockId(x, y, + z);
			int blockID3 = world.getBlockId(x - 1, y, z);
			int blockID4 = world.getBlockId(x + 1, y, z);
			byte direction = 3;
			
			if(Block.opaqueCubeLookup[blockID] && !Block.opaqueCubeLookup[blockID2])
			{
				direction = 3;
			}
			
			if(Block.opaqueCubeLookup[blockID2] && !Block.opaqueCubeLookup[blockID])
			{
				direction = 2;
			}
			
			if(Block.opaqueCubeLookup[blockID3] && !Block.opaqueCubeLookup[blockID4])
			{
				direction = 5;
			}
			
			if(Block.opaqueCubeLookup[blockID4] && !Block.opaqueCubeLookup[blockID3])
			{
				direction = 4;
			}
			world.setBlockMetadataWithNotify(x, y, z, direction);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int texture)
	{
		if(texture == 1)
		{
			return this.blockIndexInTexture + 17;
		}
		else
		if(texture == 0)
		{
			return this.blockIndexInTexture + 17;
		}
		else
		{
			int metadata = blockAccess.getBlockMetadata(x, y, z);
			return texture != metadata ? this.blockIndexInTexture : (this.isActive ? this.blockIndexInTexture + 16 : this.blockIndexInTexture - 1);
		}
	}
	

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (this.isActive)
        {
            int metadata = world.getBlockMetadata(x, y, z);
            float fx = (float)x + 0.5F;
            float fy = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float fz = (float)z + 0.5F;
            float f = 0.52F;
            float f1 = random.nextFloat() * 0.6F - 0.3F;
            
            if(metadata == 4)
            {
            	//particle spawn code
            }else
            if(metadata == 5)
            {
            	//particle spawn code
            }else
            if(metadata == 2)
            {
            	//particle spawn code
            }else
            if(metadata == 3)
            {
            	//particle spawn code
            }
        }
    }
	
	public int getBlockTextureFromSide(int side)
	{
		return side == 1 ? this.blockIndexInTexture + 17 : (side == 0 ? this.blockIndexInTexture + 17 : (side == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture));
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		int metadata = world.getBlockMetadata(x, y, z);
		
		if(!world.isRemote)
		{
			player.openGui(ScienceCraft.instance(), 2, world, x, y, z);
			return true;
		}
		return true;
	}
	
	public static void updateIncubatorBlockState(boolean b, World world, int x, int y, int z)
	{
		int metadata = world.getBlockMetadata(x, y, z);
        TileEntity te = world.getBlockTileEntity(x, y, z);
        keepIncubatorInventory = true;

        if (b)
        {
        	world.setBlockWithNotify(x, y, z, Block.stoneOvenActive.blockID);
        }
        else
        {
        	world.setBlockWithNotify(x, y, z, Block.stoneOvenIdle.blockID);
        }

        keepIncubatorInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, metadata);

        if (te != null)
        {
            te.validate();
            world.setBlockTileEntity(x, y, z, te);
        }
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
	{
		int var6 = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (var6 == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2);
		}

		if (var6 == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5);
	    }

		if (var6 == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3);
		}

		if (var6 == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4);
		}
	} 
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepIncubatorInventory)
        {
            TileEntityIncubator te = (TileEntityIncubator)par1World.getBlockTileEntity(par2, par3, par4);

            if (te != null)
            {
                for (int i = 0; i < te.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = te.getStackInSlot(i);

                    if (itemstack != null)
                    {
                        float f = this.random.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int rand = this.random.nextInt(21) + 10;

                            if (rand > itemstack.stackSize)
                            {
                            	rand = itemstack.stackSize;
                            }

                            itemstack.stackSize -= rand;
                            EntityItem entity = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, rand, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                            	entity.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entity.motionX = (double)((float)this.random.nextGaussian() * f3);
                            entity.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                            entity.motionZ = (double)((float)this.random.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entity);
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
