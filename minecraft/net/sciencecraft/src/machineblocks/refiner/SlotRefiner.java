package net.sciencecraft.src.machineblocks.refiner;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.sciencecraft.src.ScienceCraft;

public class SlotRefiner extends Slot
{
	private EntityPlayer player;
	private int stackSize;
	
	public SlotRefiner(EntityPlayer player, IInventory inventory, int x, int y, int z) 
	{
		super(inventory, x, y, z);
		this.player = player;
	}
	
	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}
	
	public ItemStack decrStackSize(int amt)
	{
		if(this.getHasStack())
		{
			this.stackSize += Math.min(amt, this.getStack().stackSize);
		}
		
		return super.decrStackSize(amt);
	}
	
	public void onPickupFromSlot(ItemStack itemstack)
	{
		this.onCrafting(itemstack);
		super.onPickupFromSlot(player, itemstack);
	}
	
	protected void onCrafting(ItemStack itemstack, int i)
	{
		this.stackSize += i;
		this.onCrafting(itemstack);
	}
	
	protected void onCrafting(ItemStack itemstack)
	{
		itemstack.onCrafting(this.player.worldObj, this.player, stackSize);
		
		if(!this.player.worldObj.isRemote)
		{
			int size = this.stackSize;
			float f = RefinerRecipes.refining().func_77601_c(itemstack.itemID);
			int var;
			
			if(f == 0.0F)
			{
				size = 0;
			}
			else
			if(f < 1.0F)
			{
				var = MathHelper.floor_float((float) size * f);
				
				if(var < MathHelper.ceiling_float_int((float)size * f) && (float)Math.random() < (float)size * f - (float) var)
				{
					++var;
				}
				
				size = var;
			}
			
			while(size > 0)
			{
				var = EntityXPOrb.getXPSplit(size);
				size -= var;
				this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX, this.player.posY + 0.5D, this.player.posZ, var));
			}
		}
		
		this.stackSize = 0;
		
		ScienceCraft.registry.onRefined(itemstack, player);
		
	}
}
