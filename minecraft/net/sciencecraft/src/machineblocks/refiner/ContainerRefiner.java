package net.sciencecraft.src.machineblocks.refiner;

import java.util.Iterator;

import universalelectricity.extend.IItemElectric;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerRefiner extends Container 
{
	 private TileEntityRefiner refiner;
	 
	 public ContainerRefiner(InventoryPlayer inventory, TileEntityRefiner refiner)
	 {
		 this.refiner = refiner;
		 this.addSlotToContainer(new Slot(refiner, 0, 56, 17));
		 this.addSlotToContainer(new Slot(refiner, 1, 56, 53));
		 this.addSlotToContainer(new SlotRefiner(inventory.player, refiner, 2, 116, 35));
		 
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
	    
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return this.refiner.isUseableByPlayer(player);
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
				if(itemstack1.getItem() instanceof IItemElectric)
				{
					if(!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
				else
				if(RefinerRecipes.refining().getRefiningResult(itemstack1) != null)
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
