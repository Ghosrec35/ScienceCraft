package net.sciencecraft.src.machineblocks.refiner;

import com.google.common.io.ByteArrayDataInput;

import universalelectricity.electricity.ElectricityManager;
import universalelectricity.electricity.TileEntityElectricUnit;
import universalelectricity.extend.IItemElectric;
import universalelectricity.network.PacketManager;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.ItemBlock;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.sciencecraft.src.network.IPacketReceiver;
import net.sciencecraft.src.PacketHandler;

public class TileEntityRefiner extends TileEntityElectricUnit implements IInventory, ISidedInventory, IPacketReceiver
{
	public final int refiningTimeRequired = 360;
	public int refiningTicks = 0;
	public final float electricityRequired = 4;
	public float electricityStored = 0;
	
	public ItemStack[] refinerStacks = new ItemStack[3];
	
	public TileEntityRefiner()
	{
		ElectricityManager.registerElectricUnit(this);
	}
	
	@Override
	public float electricityRequest()
	{
		if(!this.isDisabled() && this.canRefine())
		{
			return Math.max(0, electricityRequired - electricityStored);
		}
		return 0;
	}
	
	@Override
	public boolean canReceiveFromSide(ForgeDirection side) 
	{
		return side == ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
	}
	
	@Override
	public void onUpdate(float watts, float voltage, ForgeDirection side) 
	{
	      super.onUpdate(watts, voltage, side);

	        if(!this.worldObj.isRemote)
	        {
	            if (voltage > this.getVoltage())
	            {
	                this.worldObj.createExplosion((Entity)null, this.xCoord, this.yCoord, this.zCoord, 1F);
	            }
	            
	            if (this.refinerStacks[1] != null)
	            {
	                if (this.refinerStacks[1].getItem() instanceof IItemElectric)
	                {
	                    IItemElectric item = (IItemElectric)this.refinerStacks[1].getItem();

	                    if (item.canProduceElectricity())
	                    {
	                        double receivedElectricity = item.onUseElectricity(item.getTransferRate(), this.refinerStacks[1]);
	                        this.electricityStored += receivedElectricity;
	                    }
	                }
	            }

	            this.electricityStored += watts;

	            if (this.electricityStored >= this.electricityRequired && !this.isDisabled())
	            {
	                if (this.refinerStacks[0] != null && this.canRefine() && this.refiningTicks == 0)
	                {
	                    this.refiningTicks = this.refiningTimeRequired;
	                }

	                if (this.canRefine() && this.refiningTicks > 0)
	                {
	                    this.refiningTicks -= this.getTickInterval();

	                    if (this.refiningTicks < 1 * this.getTickInterval())
	                    {
	                        this.refineItem();
	                        this.refiningTicks = 0;
	                    }
	                }
	                else
	                {
	                    this.refiningTicks = 0;
	                }

	                this.electricityStored = 0;
	            }
	            
	            PacketHandler.sendTileEntityPacket(this, "ScienceCraft", this.refiningTicks, this.disabledTicks);
	        }
	}
	
	public boolean isRunning()
	{
		return refiningTicks > 0;
	}
	
	@Override
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		try
		{
			this.refiningTicks = dataStream.readInt();
			this.disabledTicks = dataStream.readInt();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean canRefine()
	{
		if(RefinerRecipes.refining().getRefiningResult(this.refinerStacks[0]) == null)
		{
			return false;
		}
		
		if(this.refinerStacks[0] == null)
		{
			return false;
		}
		
		if(this.refinerStacks[2] != null)
		{
			if(!this.refinerStacks[2].isItemEqual(RefinerRecipes.refining().getRefiningResult(this.refinerStacks[0])))
			{
				return false;
			}
			
			if(this.refinerStacks[2].stackSize + 1 > 64)
			{
				return false;
			}
		}
		return true;
	}
	
	public void refineItem()
	{
		if(this.canRefine())
		{
			ItemStack itemstack = RefinerRecipes.refining().getRefiningResult(refinerStacks[0]);
			
			if(refinerStacks[2] == null)
			{
				refinerStacks[2] = itemstack.copy();
			}
			else
			if(refinerStacks[2].isItemEqual(itemstack))
			{
				refinerStacks[2].stackSize += itemstack.stackSize;
			}
			
			--refinerStacks[0].stackSize;
			if(refinerStacks[0].stackSize <= 0)
			{
				refinerStacks[0] = null;
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList list = par1NBTTagCompound.getTagList("RefinerItems");
		refinerStacks = new ItemStack[this.getSizeInventory()];
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbttag = (NBTTagCompound) list.tagAt(i);
			byte slot = nbttag.getByte("RefinerSlot");
			
			if(slot > 0 && slot < refinerStacks.length)
			{
				refinerStacks[i] = ItemStack.loadItemStackFromNBT(nbttag);
			}
		}
	}
	
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("electricityStored", (short)electricityStored);
		par1NBTTagCompound.setShort("refiningTicks", (short)refiningTicks);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < refinerStacks.length; i++)
		{
			if(refinerStacks[i] != null)
			{
				NBTTagCompound nbttag = new NBTTagCompound();
				nbttag.setByte("RefinerSlot", (byte)i);
               	refinerStacks[i].writeToNBT(nbttag);
                list.appendTag(nbttag);
			}
		}
		par1NBTTagCompound.setTag("RefinerItems", list);
    }
	
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		if(side == side.DOWN || side == side.UP)
		{
			return side.ordinal();
		}
		
		return 2;
	}
	
	@Override
	public int getSizeInventorySide(ForgeDirection side)
	{
		return 1;
	}
	
	public int getSizeInventory()
	{
		return refinerStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return refinerStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(refinerStacks[i] != null)
		{
			ItemStack itemstack;
			
			if(refinerStacks[i].stackSize <= j)
			{
				itemstack = refinerStacks[i];
				refinerStacks[i] = null;
				return itemstack;
			}
			else
			{
				itemstack = refinerStacks[i].splitStack(j);
				
				if(refinerStacks[i].stackSize == 0)
				{
					refinerStacks[i] = null;
				}
				
				return itemstack;
			}
 		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		if(refinerStacks[i] != null)
		{
			ItemStack itemstack = refinerStacks[i];
			refinerStacks[i] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		refinerStacks[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() 
	{
		return "container.refiner";
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	 
	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
	
	@Override
	public float getVoltage()
	{
		return 120F;
	}
	
	@Override
	public int getTickInterval()
	{
		return 3;
	}
}
