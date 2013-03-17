package net.sciencecraft.src.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;

public interface IPacketReceiver 
{
	public void handlePacketData(INetworkManager manager, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput input);
}
