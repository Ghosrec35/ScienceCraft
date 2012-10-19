package net.sciencecraft.src.client;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.sciencecraft.src.CommonProxy;
import net.sciencecraft.src.ScienceCraft;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.registry.TickRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCBlockFile);
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCItemFile);
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCMachinesFile);
	}
}
