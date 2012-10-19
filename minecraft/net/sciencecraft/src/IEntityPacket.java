package net.sciencecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;

public interface IEntityPacket 
{
	/**
	 * Used to Handle Non TileEntity Entity Packets
	 * */
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream);
}
