package dalapo.factech.tileentity.specialized;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;

import java.util.ArrayDeque;
import java.util.Deque;

public class TileEntityWoodcutter extends TileEntityMachine
{
	private static final int MAX_RECURSIONS = 256;
	public TileEntityWoodcutter()
	{
		super("woodcutter", 0, 2, 1, RelativeSide.BACK);
		setDisplayName("Woodcutter");
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.SAW, 0.1F, 1.05F, 0.5F, 6);
		partsNeeded[1] = new MachinePart(PartList.MOTOR, 0.05F, 1.15F, 0.75F, 6);
	}
	
	private void cutAndProgress(BlockPos pos, Block type, int recursions)
	{
		int meta = world.getBlockState(pos).getValue(type == Blocks.LOG ? BlockOldLog.VARIANT : BlockNewLog.VARIANT).ordinal();
		if (type == Blocks.LOG2) meta-=4; // Dirty hack
		if (recursions < MAX_RECURSIONS && doOutput(new ItemStack(type, 1, meta)))
		{
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
			cutAndProgress(toCut, (BlockLog)world.getBlockState(toCut).getBlock(), 0);
			return true;
		}
		return false;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 200;
	}

}
