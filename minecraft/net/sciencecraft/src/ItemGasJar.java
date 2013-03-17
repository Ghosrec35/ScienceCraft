package net.sciencecraft.src;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGasJar extends Item 
{
	private int idFilled;
	
	public ItemGasJar(int par1, int itemIDFilled) 
	{
		super(par1);
		this.idFilled = itemIDFilled;
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == this.itemID)
		{
			list.add("A jar used for collecting gases!");
		}
	}
}
