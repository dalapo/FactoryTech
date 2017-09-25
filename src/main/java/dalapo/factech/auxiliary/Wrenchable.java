package dalapo.factech.auxiliary;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Wrenchable {
	public void onWrenched(boolean isSneaking, World world, BlockPos pos, EnumFacing side);
}