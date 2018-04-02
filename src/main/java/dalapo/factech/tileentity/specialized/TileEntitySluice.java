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
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBase;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntitySluice extends TileEntityMachine {
	
	private static boolean isUniversal = false;
	private static final List<ItemStack> outputs = new ArrayList<ItemStack>();
	private static List<Integer> allowedBiomes = new ArrayList<>();
	
	private boolean hasWater = true;
	
	static {
		outputs.add(new ItemStack(Items.IRON_NUGGET));
		outputs.add(new ItemStack(ItemRegistry.oreProduct, 1, 5));
		outputs.add(new ItemStack(ItemRegistry.oreProduct, 1, 4));
	}
	
	public static void genBiomeWhitelist()
	{
		String[] raw = FacTechConfigManager.grateBiomes.split(",");
		for (String str : raw)
		{
			Logger.info(str);
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
	
	public TileEntitySluice() {
		super("sluice", 0, 1, 4, RelativeSide.SIDE);
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
		if (FactoryTech.random.nextInt(2) == 0)
		{
			doOutput(Items.IRON_NUGGET, 1, 0, 0);
		}
		else if (FactoryTech.random.nextInt(2) == 0)
		{
			doOutput(ItemRegistry.oreProduct, 1, 5, 1);
		}
		else
		{
			doOutput(ItemRegistry.oreProduct, 1, 4, 2);
		}
		
		if (FactoryTech.random.nextInt(8) == 0)
		{
			doOutput(Items.FISH, 1, 0, 3);
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
