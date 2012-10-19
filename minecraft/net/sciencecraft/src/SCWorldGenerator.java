package net.sciencecraft.src;

import java.util.Random;

import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class SCWorldGenerator implements IWorldGenerator 
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.worldType)
		{
		case -1 : generateNether(world, random, chunkX * 16, chunkZ * 16); break;
		case 0 : generateSurface(world, random, chunkX * 16, chunkZ * 16); break;
		case 1 : generateEnd(world, random, chunkX * 16, chunkZ * 16); break;
		}
	}

	private void generateNether(World world, Random random, int blockX, int blockZ) 
	{
	}

	private void generateSurface(World world, Random random, int blockX, int blockZ) 
	{
		for(int i = 0; i < 40; i++)
		{
			int posX = blockX + random.nextInt(16);
			int posY = random.nextInt(64);
			int posZ = blockZ + random.nextInt(16);
			(new WorldGenMinable(ScienceCraft.registry.oreSalt.blockID, 40)).generate(world, random, posX, posY, posZ);
		}
	}

	private void generateEnd(World world, Random random, int i, int j) 
	{
		
	}
}
