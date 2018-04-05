package dalapo.factech.tileentity.specialized;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;

import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

public class TileEntityWoodcutter extends TileEntityMachine
{
	
	public TileEntityWoodcutter()
	{
		super("woodcutter", 0, 2, 1, RelativeSide.BACK);
		setDisplayName("Woodcutter");
	}

	private void cutAndProgress(BlockPos pos, Block type, int recursions)
	{
		NonNullList<ItemStack> drops = NonNullList.<ItemStack>create();
		type.getDrops(drops, world, pos, world.getBlockState(pos), 0);
//		int meta = world.getBlockState(pos).getValue(type == Blocks.LOG ? BlockOldLog.VARIANT : BlockNewLog.VARIANT).ordinal();
//		if (type == Blocks.LOG2) meta-=4; // Dirty hack
		if (recursions < FacTechConfigManager.maxWoodcutterRecursions)
		{
			if (!doOutput(drops.get(0)))
			{
				ItemStack is = drops.get(0);
				EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, is);
				world.spawnEntity(ei);
			}
			world.destroyBlock(pos, false);
			for (int y=pos.getY()+1; y>=pos.getY()-1; y--)
			{
				for (int z=pos.getZ()+1; z>=pos.getZ()-1; z--)
				{
					for (int x=pos.getX()+1; x>=pos.getX()-1; x--)
					{
						BlockPos newPos = new BlockPos(x, y, z);
						if (world.getBlockState(newPos).getBlock().isWood(world, newPos))
						{
							cutAndProgress(newPos, type, recursions + 1);
						} // I
					} // love
				} // nested
			} // statements
		} // so
	} // much
	
	@Override
	protected boolean performAction() {
		BlockPos toCut = FacMathHelper.withOffset(pos, getFront());
		if (world.getBlockState(toCut).getBlock().isWood(world, toCut))
		{
			cutAndProgress(toCut, world.getBlockState(toCut).getBlock(), 0);
			return true;
		}
		return false;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 120;
	}

}
