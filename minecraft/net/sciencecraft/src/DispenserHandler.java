package net.sciencecraft.src;

import java.util.Random;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import cpw.mods.fml.common.IDispenserHandler;

public class DispenserHandler implements IDispenserHandler 
{
	@Override
	public int dispense(int x, int y, int z, int xVelocity, int zVelocity, World world, ItemStack item, Random random, double entX, double entY, double entZ) 
	{
		return 0;
	}
}
