package net.sciencecraft.src.machineblocks.incubator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sciencecraft.src.ScienceCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockElectricIncubator extends BlockMachine 
{	
	private Random random = new Random();
	private final boolean isActive;
	public static boolean keepIncubatorInventory = false;
	
	public BlockElectricIncubator(int id, boolean bool)
	{
		super("electricIncubator", id, Material.rock);
		isActive = bool;
		blockIndexInTexture = 16;
		this.setCreativeTab(CreativeTabs.tabDeco);
	}
	
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityElectricIncubator();
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
	
	@Override
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer player)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		
		if(!world.isRemote)
		{
			player.openGui(ScienceCraft.instance(), 3, world, x, y, z);
			return true;
		}
		return true;
	}
	
	public static void updateIncubatorBlockState(boolean b, World world, int xCoord, int yCoord, int zCoord)
	{
		int block = world.getBlockMetadata(xCoord, yCoord, zCoord);
		TileEntity te = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		keepIncubatorInventory = true;
		
		if(b)
		{
			world.setBlockWithNotify(xCoord, yCoord, zCoord, ScienceCraft.registry.incubatorActive.blockID);
		}
		else
		{
			world.setBlockWithNotify(xCoord, yCoord, zCoord, ScienceCraft.registry.incubatorInactive.blockID);
		}
		
		keepIncubatorInventory = false;
		world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, block);
		
		if(te != null)
		{
			te.validate();
			world.setBlockTileEntity(xCoord, yCoord, zCoord, te);
		}
	}
	
	public String getTextureFile()
	{
		return ScienceCraft.SCDirectory + ScienceCraft.SCMachinesFile;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
	{
		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if(direction == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2);
		}
		
		if(direction == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5);
		}
		
		if(direction == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3);
		}
		
		if(direction == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4);
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, int i, int j)
	{
		if(!keepIncubatorInventory)
		{
			TileEntityElectricIncubator ter = (TileEntityElectricIncubator)world.getBlockTileEntity(x, y, z);
			
			if(ter != null)
			{
				for(int stack = 0; stack < ter.getSizeInventory(); stack++)
				{
					ItemStack itemstack = ter.getStackInSlot(stack);
					
					if(itemstack != null)
					{
						float f = this.random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;
						
						while(itemstack.stackSize > 0)
						{
							int size = this.random.nextInt(21) + 10;
							
							if(size > itemstack.stackSize)
							{
								size = itemstack.stackSize;
							}
							
							itemstack.stackSize -= size;
							EntityItem entity = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, size, itemstack.getItemDamage()));
							
							if(itemstack.hasTagCompound())
							{
								entity.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
							}
							
							float f4 = 0.05F;
							entity.motionX = (double)((float)this.random.nextGaussian() * f4);
							entity.motionY = (double)((float)this.random.nextGaussian() * f4 + 0.2F);
							entity.motionZ = (double)((float)this.random.nextGaussian() * f4);
							world.spawnEntityInWorld(entity);
						}
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, i, j);
	}
}
