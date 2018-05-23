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
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacArrayHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntitySluice extends TileEntityMachine {
	
	private static boolean isUniversal = false;
	public static final List<Pair<ItemStack, Double>> outputs = new ArrayList<>();
	private static List<Integer> allowedBiomes = new ArrayList<>();
	
	private boolean hasWater = true;
	
	public static void genBiomeWhitelist()
	{
		String[] raw = FacTechConfigManager.grateBiomes.split(",");
		for (String str : raw)
		{
			if (str.equals("all"))
			{
				isUniversal = true;
				break;
			}
			try
			{
				allowedBiomes.add(Integer.parseInt(str));
			}
			catch (NumberFormatException e)
			{
				continue;
			}
		}
	}
	
	public static void addValidOutput(ItemStack is, double chance)
	{
		outputs.add(new Pair(is, chance));
	}
	
	public TileEntitySluice() {
		super("sluice", 0, 1, 9, RelativeSide.SIDE);
		setDisplayName("River Grate");
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		recalcWater();
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
	
	private boolean isValidBiome()
	{
		return isUniversal || allowedBiomes.contains(Biome.getIdForBiome(world.getBiome(pos)));
	}
	
	public boolean isValidLocation()
	{
		return hasWater && FacMathHelper.isInRange(pos.getY(), 60, 70) && isValidBiome();
	}
	
	@Override
	public boolean canRun()
	{
		if (!super.canRun()) return false;
		
		return isValidLocation();
	}

	@Override
	public void update()
	{
		super.update();	
	}
	
	@Override
	public void onInventoryChanged(int slot)
	{
		super.onInventoryChanged(slot);
		recalcWater();
	}
	
	@Override
	protected boolean performAction() {
		for (Pair<ItemStack, Double> p : outputs)
		{
			if (Math.random() < p.b)
			{
				doOutput(p.a.copy());
			}
		}
		recalcWater();
		return true;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 240;
	}

}
