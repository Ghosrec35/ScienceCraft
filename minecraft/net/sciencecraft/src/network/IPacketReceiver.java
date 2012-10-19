package net.sciencecraft.src.network;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;

public interface IPacketReceiver 
{
	public void handlePacketData(NetworkManager manager, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput input);
}
