package net.sciencecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import universalelectricity.extend.BlockMachine;
import universalelectricity.extend.IElectricUnit;
import universalelectricity.network.IPacketReceiver;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.sciencecraft.src.machineblocks.refiner.TileEntityRefiner;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler, IPacketReceiver
{
	public enum SCPacketType
	{
		TILEENTITY,
		ENTITY,
		UNSPECIFIED;
		
		public static SCPacketType get(int type)
		{
			if(type >= 0 && SCPacketType.values().length - 1 < 3)
			{
				return SCPacketType.values()[type];
			}
			return UNSPECIFIED;
		}
	}

	@Override
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) 
	{
		
	}

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
		SCPacketType packetType = SCPacketType.get(data.readInt());

		int x;
		int y;
		int z;
		
		if(packetType == SCPacketType.TILEENTITY)
		{
			x = data.readInt();
			y = data.readInt();
			z = data.readInt();
			
			World world = ((EntityPlayer)player).worldObj;
			
			if(world != null)
			{
				TileEntity te = world.getBlockTileEntity(x, y, z);
				
				if(te != null)
				{
					if(te instanceof IPacketReceiver)
					{
						((IPacketReceiver)te).handlePacketData(manager, packet, (EntityPlayer)player, data);
					}
				}
			}
		}
		else
		if(packetType == SCPacketType.ENTITY)
		{
			x = data.readInt();
			y = data.readInt();
			z = data.readInt();
			
			World world = ((EntityPlayer)player).worldObj;
			
			if(world != null)
			{
				Entity entity = null;
				
				if(entity != null)
				{
					if(entity instanceof IEntityPacket)
					{
						((IEntityPacket)entity).handlePacketData(manager, packet, (EntityPlayer)player, data);
					}
				}
			}
		}else
		{
			this.handlePacketData(manager, packet, (EntityPlayer)player, data);
		}
	}
	
	public static void sendEntityPacket(Entity entity, String channel, Object... sendData)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try
		{
			dos.writeInt(SCPacketType.ENTITY.ordinal());
			
			dos.writeInt(entity.chunkCoordX);
			dos.writeInt(entity.chunkCoordY);
			dos.writeInt(entity.chunkCoordZ);
			
			packetPrepAndDispatchToClients(channel, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendTileEntityPacket(TileEntity te, String channel, Object... sendData) 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try
		{
			dos.writeInt(SCPacketType.TILEENTITY.ordinal());
			
			dos.writeInt(te.xCoord);
			dos.writeInt(te.yCoord);
			dos.writeInt(te.zCoord);
			
			packetPrepAndDispatchToClients(channel, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendUnspecifiedPacket(String channelName, Object... sendData)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try
		{
			dos.writeInt(SCPacketType.UNSPECIFIED.ordinal());
			
			packetPrepAndDispatchToClients(channelName, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	
	public static void sendEntityPacketToServer(Entity entity, String channel, Object... sendData)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try
		{
			dos.writeInt(SCPacketType.ENTITY.ordinal());
			
			dos.writeInt(entity.chunkCoordX);
			dos.writeInt(entity.chunkCoordY);
			dos.writeInt(entity.chunkCoordZ);
			
			packetPrepAndDispatchToServer(channel, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sendTileEntityPacketToServer(TileEntity te, String channel, Object... sendData) 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try
		{
			dos.writeInt(SCPacketType.TILEENTITY.ordinal());
			
			dos.writeInt(te.xCoord);
			dos.writeInt(te.yCoord);
			dos.writeInt(te.zCoord);
			
			packetPrepAndDispatchToServer(channel, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendUnspecifiedPacketToServer(String channelName, Object... sendData)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		try
		{
			dos.writeInt(SCPacketType.UNSPECIFIED.ordinal());
			
			packetPrepAndDispatchToServer(channelName, baos, dos, sendData);
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	
	private static void packetPrepAndDispatchToClients(String channel, ByteArrayOutputStream baos, DataOutputStream dos, Object[] sendData) 
	{
		try
		{
			for(Object data : sendData)
			{
				if(data instanceof Integer)
				{
					dos.writeInt((Integer)data);
				}else
				if(data instanceof Double)
				{
					dos.writeDouble((Double)data);
				}else
				if(data instanceof Byte)
				{
					dos.writeByte((Byte)data);
				}else
				if(data instanceof Long)
				{
					dos.writeLong((Long)data);
				}else
				if(data instanceof Short)
				{
					dos.writeShort((Short)data);
				}else
				if(data instanceof String)
				{
					dos.writeUTF((String)data);
				}else
				if(data instanceof Character)
				{
					dos.writeChar((Character)data);
				}else
				if(data instanceof Boolean)
				{
					dos.writeBoolean((Boolean)data);
				}else
				if(data instanceof Float)
				{
					dos.writeFloat((Float)data);
				}
				else
				{
					throw new IllegalArgumentException("Unable to write this data type" + data.toString() + "to the DataOutputStream");
				}
			}
		
		
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = channel;
			packet.data = baos.toByteArray();
			packet.length = packet.data.length;
		
			PacketDispatcher.sendPacketToAllPlayers(packet);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void packetPrepAndDispatchToServer(String channel, ByteArrayOutputStream baos, DataOutputStream dos, Object[] sendData) 
	{
		try
		{
			for(Object data : sendData)
			{
				if(data instanceof Integer)
				{
					dos.writeInt((Integer)data);
				}else
				if(data instanceof Double)
				{
					dos.writeDouble((Double)data);
				}else
				if(data instanceof Byte)
				{
					dos.writeByte((Byte)data);
				}else
				if(data instanceof Long)
				{
					dos.writeLong((Long)data);
				}else
				if(data instanceof Short)
				{
					dos.writeShort((Short)data);
				}else
				if(data instanceof String)
				{
					dos.writeUTF((String)data);
				}else
				if(data instanceof Character)
				{
					dos.writeChar((Character)data);
				}else
				if(data instanceof Boolean)
				{
					dos.writeBoolean((Boolean)data);
				}else
				if(data instanceof Float)
				{
					dos.writeFloat((Float)data);
				}
				else
				{
					throw new IllegalArgumentException("Unable to write this data type" + data.toString() + "to the DataOutputStream");
				}
			}
		
		
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = channel;
			packet.data = baos.toByteArray();
			packet.length = packet.data.length;
		
			PacketDispatcher.sendPacketToServer(packet);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
