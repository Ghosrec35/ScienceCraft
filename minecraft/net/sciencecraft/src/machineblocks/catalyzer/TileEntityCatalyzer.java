package net.sciencecraft.src.machineblocks.catalyzer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.sciencecraft.src.PacketHandler;
import net.sciencecraft.src.network.IPacketReceiver;

import com.google.common.io.ByteArrayDataInput;

public class TileEntityCatalyzer extends TileEntityElectricUnit implements IInventory, ISidedInventory, IPacketReceiver 
{
	public final int catalyzingTimeRequired = 360;
	public int catalyzingTicks = 0;
	public final float electricityRequired = 4;
	public float electricityStored = 0;
	
	public ItemStack[] catalyzerStacks = new ItemStack[4];
	
	public TileEntityCatalyzer()
	{
		ElectricityManager.registerElectricUnit(this);
	}
	
	@Override
	public float electricityRequest() 
	{
		if(!this.isDisabled() && this.canCatalyze())
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
	            
	            if (this.catalyzerStacks[1] != null)
	            {
	                if (this.catalyzerStacks[1].getItem() instanceof IItemElectric)
	                {
	                    IItemElectric item = (IItemElectric)this.catalyzerStacks[1].getItem();

	                    if (item.canProduceElectricity())
	                    {
	                        double receivedElectricity = item.onUseElectricity(item.getTransferRate(), this.catalyzerStacks[1]);
	                        this.electricityStored += receivedElectricity;
	                    }
	                }
	            }

	            this.electricityStored += watts;

	            if (this.electricityStored >= this.electricityRequired && !this.isDisabled())
	            {
	                if (this.catalyzerStacks[0] != null && this.canCatalyze() && this.catalyzingTicks == 0)
	                {
	                    this.catalyzingTicks = this.catalyzingTimeRequired;
	                }

	                if (this.canCatalyze() && this.catalyzingTicks > 0)
	                {
	                    this.catalyzingTicks -= this.getTickInterval();

	                    if (this.catalyzingTicks < 1 * this.getTickInterval())
	                    {
	                        this.catalyzeItem();
	                        this.catalyzingTicks = 0;
	                    }
	                }
	                else
	                {
	                    this.catalyzingTicks = 0;
	                }

	                this.electricityStored = 0;
	            }
	            
	            PacketHandler.sendTileEntityPacket(this, "ScienceCraft", this.catalyzingTicks, this.disabledTicks);
	        }
	}
	
	public boolean isRunning()
	{
		return catalyzingTicks > 1;
	}
	
	@Override
	public void handlePacketData(NetworkManager manager, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput input) 
	{
		try
		{
			this.catalyzingTicks = input.readInt();
			this.disabledTicks = input.readInt();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean canCatalyze()
	{
		if(CatalyzerRecipes.catalyzing().getCatalyzationResult(this.catalyzerStacks[0]) == null)
		{
			return false;
		}
		
		if(this.catalyzerStacks[0] == null)
		{
			return false;
		}
		
		if(this.catalyzerStacks[2] != null && this.catalyzerStacks[3] != null)
		{
			ItemStack[] itemstack = CatalyzerRecipes.catalyzing().getCatalyzationResult(this.catalyzerStacks[0]);
			
			if(!this.catalyzerStacks[2].isItemEqual(itemstack[0]) || !this.catalyzerStacks[3].isItemEqual(itemstack[1]))
			{
				return false;
			}
			
			if(this.catalyzerStacks[2].stackSize + 1 > 64 || this.catalyzerStacks[3].stackSize + 1 > 64)
			{
				return false;
			}
		}
		return true;
	}
	
	public void catalyzeItem()
	{
		if(this.canCatalyze())
		{
			ItemStack[] itemstack = CatalyzerRecipes.catalyzing().getCatalyzationResult(this.catalyzerStacks[0]);
			
			if(catalyzerStacks[2] == null)
			{
				catalyzerStacks[2] = itemstack[0].copy();
				catalyzerStacks[3] = itemstack[1].copy();
			}
			else
			if(catalyzerStacks[2].isItemEqual(itemstack[0]) && catalyzerStacks[3].isItemEqual(itemstack[1]))
			{
				catalyzerStacks[2].stackSize += itemstack[0].stackSize;
				catalyzerStacks[3].stackSize += itemstack[1].stackSize;
			}
			
			--catalyzerStacks[0].stackSize;
			if(catalyzerStacks[0].stackSize <= 0)
			{
				catalyzerStacks[0] = null;
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList list = par1NBTTagCompound.getTagList("CatalyzerItems");
		catalyzerStacks = new ItemStack[this.getSizeInventory()];
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbttag = (NBTTagCompound) list.tagAt(i);
			byte slot = nbttag.getByte("CatalyzerSlot");
			
			if(slot > 0 && slot < catalyzerStacks.length)
			{
				catalyzerStacks[i] = ItemStack.loadItemStackFromNBT(nbttag);
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
		par1NBTTagCompound.setShort("catalyzingTicks", (short)catalyzingTicks);
		NBTTagList list = new NBTTagList();

		for(int i = 0; i < catalyzerStacks.length; i++)
		{
			if(catalyzerStacks[i] != null)
			{
				NBTTagCompound nbttag = new NBTTagCompound();
				nbttag.setByte("CatalyzerSlot", (byte)i);
				catalyzerStacks[i].writeToNBT(nbttag);
                list.appendTag(nbttag);
			}
		}
		par1NBTTagCompound.setTag("CatalyzerItems", list);
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
		return catalyzerStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return catalyzerStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(catalyzerStacks[i] != null)
		{
			ItemStack itemstack;
			
			if(catalyzerStacks[i].stackSize <= j)
			{
				itemstack = catalyzerStacks[i];
				catalyzerStacks[i] = null;
				return itemstack;
			}
			else
			{
				itemstack = catalyzerStacks[i].splitStack(j);
				
				if(catalyzerStacks[i].stackSize == 0)
				{
					catalyzerStacks[i] = null;
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
		if(catalyzerStacks[i] != null)
		{
			ItemStack itemstack = catalyzerStacks[i];
			catalyzerStacks[i] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) 
	{
		catalyzerStacks[var1] = var2;
	}

	@Override
	public String getInvName() 
	{
		return "container.catalyzer";
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
