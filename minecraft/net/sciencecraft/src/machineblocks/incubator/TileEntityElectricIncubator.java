package net.sciencecraft.src.machineblocks.incubator;

import com.google.common.io.ByteArrayDataInput;

import universalelectricity.electricity.ElectricityManager;
import universalelectricity.electricity.TileEntityElectricUnit;
import universalelectricity.extend.IItemElectric;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.sciencecraft.src.network.IPacketReceiver;
import net.sciencecraft.src.network.PacketHandler;

public class TileEntityElectricIncubator extends TileEntityElectricUnit implements IInventory, ISidedInventory, IPacketReceiver
{
	public final int incubatingTimeRequired = 72000;
	public int incubatingTicks = 0;
	public final float electricityRequired = 12;
	public float electricityStored = 0;
	
	public ItemStack[] incubatorStacks = new ItemStack[3];
	
	public TileEntityElectricIncubator()
	{
		ElectricityManager.registerElectricUnit(this);
	}
	
	@Override
	public float electricityRequest() 
	{
		if(!this.isDisabled() && this.canIncubate())
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
			if(voltage > this.getVoltage())
			{
				this.worldObj.createExplosion((Entity)null, this.xCoord, this.yCoord, this.zCoord, 1F);
			}
			
			if(this.incubatorStacks[1] != null)
			{
				if(this.incubatorStacks[1].getItem() instanceof IItemElectric)
				{
					IItemElectric item = (IItemElectric)this.incubatorStacks[1].getItem();
					
					if(item.canProduceElectricity())
					{
						double receivedElectricity = item.onUseElectricity(item.getTransferRate(), this.incubatorStacks[1]);
						this.electricityStored += receivedElectricity;
					}
				}
			}
			
			this.electricityStored += watts;
			
			if(this.electricityStored >= this.electricityRequired && !this.isDisabled())
			{
				if(this.incubatorStacks[0] != null & this.canIncubate() && incubatingTicks == 0)
				{
					this.incubatingTicks = this.incubatingTimeRequired;
				}
				
				if(this.canIncubate() && this.incubatingTicks > 0)
				{
					this.incubatingTicks -= this.getTickInterval();
					
					if(this.incubatingTicks < 1 * this.getTickInterval())
					{
						this.incubateItem();
						this.incubatingTicks = 0;
					}
				}
				else
				{
					this.incubatingTicks = 0;
				}
				this.electricityStored = 0;
			}
			PacketHandler.sendTileEntityPacket(this, "ScienceCraft", this.incubatingTicks, this.disabledTicks);
		}
	}
	
	public boolean isRunning()
	{
		return incubatingTicks > 0;
	}
	
	@Override
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		try
		{
			this.incubatingTicks = dataStream.readInt();
			this.disabledTicks = dataStream.readInt();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean canIncubate()
	{
		if(IncubatorRecipes.incubating().getIncubatingResult(this.incubatorStacks[0]) == null)
		{
			return false;
		}
		
		if(this.incubatorStacks[0] == null)
		{
			return false;
		}
		
		if(this.incubatorStacks[2] != null)
		{
			if(!this.incubatorStacks[2].isItemEqual(IncubatorRecipes.incubating().getIncubatingResult(this.incubatorStacks[0])))
			{
				return false;
			}
			
			if(this.incubatorStacks[2].stackSize + 1 > 64)
			{
				return false;
			}
		}
		return true;
	}
	
	public void incubateItem()
	{
		if(this.canIncubate())
		{
			ItemStack itemstack = IncubatorRecipes.incubating().getIncubatingResult(this.incubatorStacks[0]);
			
			if(this.incubatorStacks[2] == null)
			{
				this.incubatorStacks[2] = itemstack.copy();
			}
			else
			if(this.incubatorStacks[2].isItemEqual(itemstack))
			{
				incubatorStacks[2].stackSize += itemstack.stackSize;
			}
			
			--incubatorStacks[0].stackSize;
			if(incubatorStacks[0].stackSize <= 0)
			{
				incubatorStacks[0] = null;
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList list = par1NBTTagCompound.getTagList("IncubatorItems");
		incubatorStacks = new ItemStack[this.getSizeInventory()];
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbttag = (NBTTagCompound) list.tagAt(i);
			byte slot = nbttag.getByte("IncubatorSlot");
			
			if(slot > 0 && slot < incubatorStacks.length)
			{
				incubatorStacks[i] = ItemStack.loadItemStackFromNBT(nbttag);
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
		par1NBTTagCompound.setShort("incubatingTicks", (short)incubatingTicks);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < incubatorStacks.length; i++)
		{
			if(incubatorStacks[i] != null)
			{
				NBTTagCompound nbttag = new NBTTagCompound();
				nbttag.setByte("IncubatorSlot", (byte)i);
				incubatorStacks[i].writeToNBT(nbttag);
                list.appendTag(nbttag);
			}
		}
		par1NBTTagCompound.setTag("IncubatorItems", list);
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

	@Override
	public int getSizeInventory() 
	{
		return incubatorStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return incubatorStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(incubatorStacks[i] != null)
		{
			ItemStack itemstack;
			
			if(incubatorStacks[i].stackSize <= j)
			{
				itemstack = incubatorStacks[i];
				incubatorStacks[i] = null;
				return itemstack;
			}
			else
			{
				itemstack = incubatorStacks[i].splitStack(j);
				
				if(incubatorStacks[i].stackSize == 0)
				{
					incubatorStacks[i] = null;
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
		if(incubatorStacks[i] != null)
		{
			ItemStack itemstack = incubatorStacks[i];
			incubatorStacks[i] = null;
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
		incubatorStacks[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() 
	{
		return "container.incubator";
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
}
