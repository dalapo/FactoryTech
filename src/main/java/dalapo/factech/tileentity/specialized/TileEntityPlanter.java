package dalapo.factech.tileentity.specialized;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Pair;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;

public class TileEntityPlanter extends TileEntityAreaMachine
{
	public TileEntityPlanter()
	{
		super("planter", 1, 2, 0, RelativeSide.BOTTOM, 4);
	}

	@Override
	protected boolean performAction() {
		Item seeds = getInput().getItem();
		int dmg = getInput().getItemDamage();
		if (FacBlockHelper.CROPS.get(new Pair(seeds, dmg)) == null) return false;
		
		int r = getAdjustedRange();
		boolean flag = false;
		for (int x=-r; x<=r; x++)
		{
			for (int y=-r; y<=r; y++)
			{
				BlockPos dxdy = pos.add(x, 0, y);
				if (FactoryTech.random.nextInt(6) == 0 && world.getBlockState(dxdy).getBlock().canSustainPlant(world.getBlockState(dxdy), world, dxdy, EnumFacing.UP, FacBlockHelper.CROPS.get(new Pair<>(seeds, dmg))) &&
						world.isAirBlock(dxdy.up()))
				{
					world.setBlockState(dxdy.up(), FacBlockHelper.CROPS.get(new Pair<>(seeds, dmg)).getPlant(world, dxdy)); // TODO: Make seeds plant the right crop
					getInput().shrink(1);
					flag = true;
				}
			}
		}
		if (flag)
		{
			world.playEvent(2001, pos.up(), Block.getStateId(FacBlockHelper.CROPS.get(new Pair(seeds, dmg)).getPlant(world, pos)));
		}
		return flag;
	}

	@Override
	public int getOpTime()
	{
		return 60;
	}
}