package dalapo.factech.tileentity.specialized;

import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TileEntityDeepDrill extends TileEntityMachine
{
	private static ItemStack getOre()
	{
		double acc = 0;
		for (Pair<ItemStack, Double> p : MachineRecipes.DEEP_DRILL)
		{
			acc += p.b;
		}
		double r = FactoryTech.random.nextDouble() * acc;
		for (Pair<ItemStack, Double> p : MachineRecipes.DEEP_DRILL)
		{
			if (p.b >= r) return p.a.copy();
			r -= p.b;
		}
		return ItemStack.EMPTY;
	}
	
	public TileEntityDeepDrill() {
		super("deepdrill", 0, 4, 0, RelativeSide.ANY);
		setDisplayName("Terra Extractor");
	}

	@Override
	public boolean canRun()
	{
		return super.canRun() && pos.getY() <= 8;
	}

	@Override
	protected boolean performAction()
	{
//		Logger.info("Performing action");
		ItemStack ore = getOre();
		if (ore.isEmpty()) return false;
		EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, ore);
		ei.motionX = 0;
		ei.motionY = 0.2;
		ei.motionZ = 0;
		world.spawnEntity(ei);
		return true;
	}

	@Override
	public int getOpTime()
	{
		return 160;
	}

}