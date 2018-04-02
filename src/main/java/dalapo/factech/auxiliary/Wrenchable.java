package dalapo.factech.auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Wrenchable {
	public void onWrenched(EntityPlayer ep, boolean isSneaking, World world, BlockPos pos, EnumFacing side);
}