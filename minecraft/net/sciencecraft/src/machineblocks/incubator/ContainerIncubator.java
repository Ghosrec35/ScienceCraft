package net.sciencecraft.src.machineblocks.incubator;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerIncubator extends Container
{
	private TileEntityIncubator incubator;
	private int lastFuelTime = 0;
	private int lastWarmTime = 0;
	private int lastIncubateTime = 0;
	
	
	public ContainerIncubator(InventoryPlayer inventory, TileEntityIncubator te)
	{
		this.incubator = te;
		this.addSlotToContainer(new Slot(incubator, 0, 56, 17));
		this.addSlotToContainer(new Slot(incubator, 1, 56, 53));
		this.addSlotToContainer(new SlotIncubator(inventory.player, incubator, 2, 116, 35));
		
		int var;
		 
		 for(var = 0; var < 3; ++var)
		 {
			 for(int var1 = 0; var1 < 9; ++var1)
			 {
				 this.addSlotToContainer(new Slot(inventory, var1 + var * 9 + 9, 8 + var1 * 18, 84 + var * 18));
			 }
		 }
		 
		 for(var = 0; var < 9; var++)
		 {
			 this.addSlotToContainer(new Slot(inventory, var, 8 + var * 18, 142));
		 }
	}
	
	public void addCraftingtoCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		crafting.updateCraftingInventoryInfo(this, 0, this.incubator.incubatorWarmTime);
		crafting.updateCraftingInventoryInfo(this, 1, this.incubator.incubatorFuelTime);
		crafting.updateCraftingInventoryInfo(this, 2, this.incubator.currentItemWarmTime);
	}
	
	public void updateCrafting()
	{
		   super.updateCraftingResults();
	        Iterator var1 = this.crafters.iterator();

	        while (var1.hasNext())
	        {
	            ICrafting var2 = (ICrafting)var1.next();

	            if (this.lastFuelTime != this.incubator.incubatorFuelTime)
	            {
	                var2.updateCraftingInventoryInfo(this, 0, this.incubator.incubatorFuelTime);
	            }

	            if (this.lastWarmTime != this.incubator.incubatorWarmTime)
	            {
	                var2.updateCraftingInventoryInfo(this, 1, this.incubator.incubatorWarmTime);
	            }

	            if (this.lastIncubateTime != this.incubator.currentItemWarmTime)
	            {
	                var2.updateCraftingInventoryInfo(this, 2, this.incubator.currentItemWarmTime);
	            }
	        }
	        this.lastFuelTime = this.incubator.incubatorFuelTime;
	        this.lastWarmTime = this.incubator.incubatorWarmTime;
	        this.lastIncubateTime = this.incubator.currentItemWarmTime;
	}
	
	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int par1, int par2)
	    {
	        if (par1 == 0)
	        {
	            this.incubator.incubatorFuelTime = par2;
	        }

	        if (par1 == 1)
	        {
	            this.incubator.incubatorWarmTime = par2;
	        }

	        if (par1 == 2)
	        {
	            this.incubator.currentItemWarmTime = par2;
	        }
	    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return this.incubator.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(int inventorySlot)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(inventorySlot);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(inventorySlot == 2)
			{
				if(!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			}
			else
			if(inventorySlot != 1 && inventorySlot != 0)
			{
				if(IncubatorRecipes.incubating().getIncubatingResult(itemstack1) != null)
				{
					if(!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
				else
				if(TileEntityIncubator.isItemFuel(itemstack1))
				{
					if(!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return null;
					}
				}
				else
				if(inventorySlot >= 3 && inventorySlot < 30)
				{
					if(!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return null;
					}
				}
				else
				if(inventorySlot >= 30 && inventorySlot < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return null;
				}
			}
			else
			if(!this.mergeItemStack(itemstack1, 3, 39, false))
			{
				return null;
			}
			
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(itemstack1);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(itemstack1);
		}
		return itemstack;
	}
	
}
