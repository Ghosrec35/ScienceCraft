package net.sciencecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler 
{
	//This is how you check if a player has crafted something that returns the block/item you want them to gain an achievement for
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) 
	{
		
	}

	
	//This is how you check if a player has smelted something that returns the block/item you want them to gain an achievement for
	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) 
	{
		
	}
}
