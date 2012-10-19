package net.sciencecraft.src;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ElementsModKeyHandler extends KeyHandler
{
	//KeyBinds
	public static KeyBinding testKey = new KeyBinding("MyBind", Keyboard.KEY_M);
	
	//Booleans
	public static boolean testBoolean = false;
	
	public ElementsModKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) 
	{
		
		super(keyBindings, repeatings);
	}

	@Override
	public String getLabel() 
	{
		return "myKeyBindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) 
	{	
		if(testKey.isPressed())
		{
			testBoolean = true;
		}else
		{
			testBoolean = false;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) 
	{
		
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.CLIENT);
	}

}
