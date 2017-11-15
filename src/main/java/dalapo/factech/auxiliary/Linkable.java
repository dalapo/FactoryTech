package dalapo.factech.auxiliary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Linkable
{
	public void onLinked(World world, EntityPlayer ep, BlockPos origin, BlockPos thisPos, ItemStack linker);
}