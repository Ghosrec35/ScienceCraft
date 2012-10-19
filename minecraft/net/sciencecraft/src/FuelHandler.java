package net.sciencecraft.src;

import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler 
{
	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		return 0;
	}
}
