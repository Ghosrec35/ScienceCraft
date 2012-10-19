package net.sciencecraft.src.machineblocks.teleporter;

public interface ITeleportationManager
{
	public void onTeleport();
	public int timeToTeleportation();
	public Teleporter createNewTeleporter();
}//Simply added to allow for a certain teleporter instances' onTeleport() method to be called, other Functionality will be added later
