package dalapo.factech.tileentity;

import static dalapo.factech.FactoryTech.DEBUG_PACKETS;
import dalapo.factech.helper.Logger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileEntityBase extends TileEntity {
//	public abstract void sendInfoPacket(EntityPlayer ep);
	public boolean isPowered()
	{
		return world.isBlockIndirectlyGettingPowered(pos) > 0;
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		if (DEBUG_PACKETS)
		{
			Logger.info("Entered getUpdateTag()");
			Thread.dumpStack();
		}
		NBTTagCompound nbt = super.getUpdateTag();
		return writeToNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		if (DEBUG_PACKETS)
		{
			Logger.info(String.format("Entered getUpdatePacket; thread = %s", Thread.currentThread()));
			Thread.dumpStack();
		}
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
//		Logger.info(String.format("Entered onDataPacket; thread = %s", Thread.currentThread()));
		this.readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}