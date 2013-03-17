package net.sciencecraft.src.machineblocks.dnasequencer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.sciencecraft.src.network.IPacketReceiver;
import net.sciencecraft.src.network.PacketHandler;

import com.google.common.io.ByteArrayDataInput;

public class TileEntityDNASequencer extends TileEntityElectricUnit implements IInventory, ISidedInventory, IPacketReceiver
{
	public final int sequencingTimeRequired = 360;
	public int sequencingTicks = 0;
	public final float electricityRequired = 4;
	public float electricityStored = 0;
	
	public ItemStack[] sequencerStacks = new ItemStack[14];
	
	public TileEntityDNASequencer()
	{
		ElectricityManager.registerElectricUnit(this);
	}
	
	@Override
	public float electricityRequest() 
	{
		if(!this.isDisabled() && this.canSequenceDNA())
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
			
			if(this.sequencerStacks[0] != null)
			{
				if(this.sequencerStacks[0].getItem() instanceof IItemElectric)
				{
					IItemElectric item = (IItemElectric)this.sequencerStacks[0].getItem();
					
					if(item.canProduceElectricity())
					{
						double receivedElectricity = item.onUseElectricity(item.getTransferRate(), this.sequencerStacks[0]);
						this.electricityStored += receivedElectricity;
					}
				}
			}
			
			this.electricityStored += watts;
			
			if(this.electricityStored >= this.electricityRequired && !this.isDisabled())
			{
				if(this.sequencerStacks[1] != null & this.canSequenceDNA() && sequencingTicks == 0)
				{
					this.sequencingTicks = this.sequencingTimeRequired;
				}
				
				if(this.canSequenceDNA() && this.sequencingTicks > 0)
				{
					this.sequencingTicks -= this.getTickInterval();
					
					if(this.sequencingTicks < 1 * this.getTickInterval())
					{
						this.canSequenceDNA();
						this.sequencingTicks = 0;
					}
				}
				else
				{
					this.sequencingTicks = 0;
				}
				this.electricityStored = 0;
			}
			PacketHandler.sendTileEntityPacket(this, "ScienceCraft", this.sequencingTicks, this.disabledTicks);
		}
	}
	
	public boolean isSequencing()
	{
		return sequencingTicks > 0;
	}
	
	@Override
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		try
		{
			sequencingTicks = dataStream.readInt();
			disabledTicks = dataStream.readInt();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean canSequenceDNA()
	{
		if(DNASequencerRecipes.sequencing().getSequencingResult(sequencerStacks) == null)
		{
			return false;
		}
		
		if(this.sequencerStacks[0] == null)
		{
			return false;
		}
		
		if(this.sequencerStacks[13] != null)
		{
			if(!this.sequencerStacks[13].isItemEqual(DNASequencerRecipes.sequencing().getSequencingResult(sequencerStacks)))
			{
				return false;
			}
			
			if(this.sequencerStacks[13].stackSize + 1 > 64)
			{
				return false;
			}
		}
		return true;
	}
	
	public void sequenceDNA()
	{
		if(this.canSequenceDNA())
		{
			ItemStack itemstack = DNASequencerRecipes.sequencing().getSequencingResult(sequencerStacks);
			
			if(this.sequencerStacks[13] == null)
			{
				this.sequencerStacks[13] = itemstack.copy();
			}
			else
			if(this.sequencerStacks[13].isItemEqual(itemstack))
			{
				this.sequencerStacks[13].stackSize += itemstack.stackSize;
			}
			
			for(int i = 1; i < (sequencerStacks.length - 1); i++)
			{
				--sequencerStacks[i].stackSize;
				
				if(sequencerStacks[i].stackSize <= 0)
				{
					sequencerStacks[i] = null;
				}
			}
		}
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
		return sequencerStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return sequencerStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if(sequencerStacks[i] != null)
		{
			ItemStack itemstack;
			
			if(sequencerStacks[i].stackSize <= j)
			{
				itemstack = sequencerStacks[i];
				sequencerStacks[i] = null;
				return itemstack;
			}
			else
			{
				itemstack = sequencerStacks[i].splitStack(j);
				
				if(sequencerStacks[i].stackSize == 0)
				{
					sequencerStacks[i] = null;
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
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		if(sequencerStacks[slot] != null)
		{
			ItemStack itemstack = sequencerStacks[slot];
			sequencerStacks[slot] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) 
	{
		sequencerStacks[slot] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();	
		}
	}

	@Override
	public String getInvName() 
	{
		return "container.dnasequencer";
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
}

