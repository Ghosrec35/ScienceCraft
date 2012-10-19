package net.sciencecraft.src;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ItemStack;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.client.event.sound.SoundResultEvent;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class EventManager 
{
	@ForgeSubscribe
	public void soundLoader(SoundLoadEvent event)
	{
		//event.manager.soundPoolSounds.addSound("refiner.running", new File(Minecraft.getMinecraftDir(), "/ScienceCraft/Refiner/RefinerRunning.ogg"));
	}
}
