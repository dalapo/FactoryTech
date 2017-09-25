package dalapo.factech.item;

import dalapo.factech.auxiliary.Wrenchable;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.WrenchPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase {

	public ItemWrench(String name) {
		super(name);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if (world.isRemote && world.getBlockState(pos).getBlock() instanceof Wrenchable)
		{
			PacketHandler.sendToServer(new WrenchPacket(pos, side));
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}