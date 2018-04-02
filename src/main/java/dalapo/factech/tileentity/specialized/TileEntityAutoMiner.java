package dalapo.factech.tileentity.specialized;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import dalapo.factech.FactoryTech;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachine;

/*
 * TODO: Completely overhaul this machine to not be useless anymore
 */
public class TileEntityAutoMiner extends TileEntityAreaMachine {

	private BlockPos nextToMine;
	private int radius;
	private int range;
	private int curDist;
	private boolean isMining;
	
	public TileEntityAutoMiner()
	{
		super("miner", 0, 3, 9, RelativeSide.SIDE, 6);
		setDisplayName("Autominer");
		curDist = 1;
		range = 64;
	}
	
	public ItemStack getChunk(int yLevel)
	{
		int r = FactoryTech.random.nextInt(100);
		if (r == 0 && yLevel <= 12) return new ItemStack(Items.DIAMOND);
		else if (r <= 34) return new ItemStack(ItemRegistry.minedOre, 1, 1);
		else return new ItemStack(ItemRegistry.minedOre, 1, 0);
	}
	
	public int getDist()
	{
		return curDist;
	}

	private BlockPos findNextBlock()
	{
		for (int x=pos.getX()-getAdjustedRange(); x<=pos.getX()+getAdjustedRange(); x++)
		{
			for (int z=pos.getZ()-getAdjustedRange(); z<=pos.getZ()+getAdjustedRange(); z++)
			{
				BlockPos toBreak = new BlockPos(x, pos.getY() - curDist, z);
				Block target = world.getBlockState(toBreak).getBlock();
				if (target != Blocks.AIR && target != Blocks.WATER && target != Blocks.FLOWING_WATER && target != Blocks.BEDROCK)
				{
					return toBreak;
				}
			}
		}
		if (pos.getY() - curDist > 0 && curDist < range)
		{
			curDist++;
			return findNextBlock();
		}
		return null;
	}
	
	@Override
	protected boolean performAction() {
		nextToMine = findNextBlock();
		if (nextToMine == null) return false;
		
		IBlockState stateAtDrop = world.getBlockState(nextToMine);
		NonNullList<ItemStack> drops = NonNullList.<ItemStack>create();
		stateAtDrop.getBlock().getDrops(drops, world, nextToMine, stateAtDrop, 0);
		if (stateAtDrop.getBlock().equals(Blocks.COAL_ORE)) drops.get(0).grow(1);
		if (!stateAtDrop.getBlock().equals(Blocks.BEDROCK))
		{
//			Logger.info(String.format("Breaking block at (%s, %s, %s)", nextToMine.getX(), nextToMine.getY(), nextToMine.getZ()));
			world.setBlockToAir(nextToMine);
		}
		
		// TODO: Send to output instead of dropping in the world
		for (ItemStack is : drops)
		{
			EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, is);
			world.spawnEntity(ei);
		}
		
		if (FactoryTech.random.nextDouble() < FacTechConfigManager.quarryChunkChance)
		{
			ItemStack chunk = getChunk(nextToMine.getY());
			EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, chunk);
			world.spawnEntity(ei);
		}
		return true;
	}

	// 1 block per second is balanced, right?
	@Override
	public int getOpTime() {
		return 20;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (nextToMine != null)
		{
			NBTTagList next = new NBTTagList();
			next.appendTag(new NBTTagInt(nextToMine.getX()));
			next.appendTag(new NBTTagInt(nextToMine.getY()));
			next.appendTag(new NBTTagInt(nextToMine.getZ()));
			nbt.setTag("nextBlock", next);
		}
		nbt.setInteger("dist", curDist);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("nextBlock"))
		{
			NBTTagList next = nbt.getTagList("nextBlock", 3);
			nextToMine = new BlockPos(next.getIntAt(0), next.getIntAt(1), next.getIntAt(2));
		}
		if (nbt.hasKey("dist"))
		{
			curDist = nbt.getInteger("dist");
		}
	}
}
