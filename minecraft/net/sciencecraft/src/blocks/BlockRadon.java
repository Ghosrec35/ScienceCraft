package net.sciencecraft.src.blocks;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class BlockRadon extends Block 
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	private Minecraft mc = ModLoader.getMinecraftInstance();
	
	public BlockRadon(int i, int j)
	{
		super(i, j, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		mc.fontRenderer.drawStringWithShadow("WARNING: RADIATION POISONING IMMINENT!", 100, 100, Color.red.getRGB());
		entity.attackEntityFrom(DamageSource.generic, 1);
		super.onEntityCollidedWithBlock(world, x, y, z, entity);
	}
	
	public boolean isAirBlock(World world, int x, int y, int z)
	{
		return true;
	}
	
	public void addCreativeItems(ArrayList itemList)
    {
    	itemList.add(new ItemStack(this));
    }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }
}
