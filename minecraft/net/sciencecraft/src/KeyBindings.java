package net.sciencecraft.src;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyBindings extends KeyHandler 
{
	private static KeyBinding[] keyBindings = new KeyBinding[4];

	private static KeyBinding useItem = new KeyBinding("", Keyboard.KEY_Y);
	
	static
	{
		keyBindings[0] = useItem;
	}
	
	public KeyBindings() 
	{
		super(keyBindings);
	}

	@Override
	public String getLabel() 
	{
		return "";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) 
	{
		for(int i = 0; i < keyBindings.length; i++)
		{
			if(kb.keyCode == useItem.keyCode)
			{
				
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) 
	{

	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return null;
	}

}
