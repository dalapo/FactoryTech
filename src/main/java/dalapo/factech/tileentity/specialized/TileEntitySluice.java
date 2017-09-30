package dalapo.factech.tileentity.specialized;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntitySluice extends TileEntityMachine {
	
	private static final List<ItemStack> outputs = new ArrayList<ItemStack>();
	
	private boolean hasWater = true;
	
	static {
		outputs.add(new ItemStack(Items.IRON_NUGGET));
		outputs.add(new ItemStack(ItemRegistry.oreProduct, 1, 5));
		outputs.add(new ItemStack(ItemRegistry.oreProduct, 1, 4));
	}
	
	public TileEntitySluice() {
		super("sluice", 0, 1, 3, RelativeSide.SIDE);
		setDisplayName("River Grate");
	}
	
	private void recalcWater()
	{
		int waterBlocks = 0;
		for (int x=pos.getX()-1; x<=pos.getX()+1; x++)
		{
			for (int y=pos.getY()-1; y<=pos.getY()+1; y++)
			{
				for (int z=pos.getZ()-1; z<=pos.getZ()+1; z++)
				{
					if (world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.WATER)) waterBlocks++;
				}
			}
		}
		if (waterBlocks >= 8) hasWater = true;
		else hasWater = false;
	}
	@Override
	public boolean canRun()
	{
		if (!super.canRun()) return false;
		
		return hasWater && FacMathHelper.isInRange(pos.getY(), 60, 70) && world.getBiome(pos).equals(Biomes.RIVER);
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.MESH, new ItemStack(Items.STICK, 3), 0.05F, 1.2F, 0.75F, 8);
	}

	@Override
	protected boolean performAction() {
		if (FactoryTech.random.nextInt(2) == 0)
		{
			doOutput(outputs.get(0).copy(), 0);
		}
		else if (FactoryTech.random.nextInt(2) == 0)
		{
			doOutput(outputs.get(1).copy(), 1);
		}
		else
		{
			doOutput(outputs.get(2).copy(), 2);
		}
		recalcWater();
		return true;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 280;
	}

}
