package dalapo.factech.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import dalapo.factech.FacTechConfigManager;
import dalapo.factech.auxiliary.EnumOreType;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGenerator implements IWorldGenerator {
	
	public OreGenerator()
	{
		if (FacTechConfigManager.genCopper) ores.add(new OreGen("copper", BlockRegistry.ore.getDefaultState().withProperty(StateList.oretype, EnumOreType.COPPER), 0, 48, Blocks.STONE, 8, 5, 75));
		if (FacTechConfigManager.genNickel) ores.add(new OreGen("nickel", BlockRegistry.ore.getDefaultState().withProperty(StateList.oretype, EnumOreType.NICKEL), 0, 24, Blocks.STONE, 8, 4, 60));
	}
	
	public static class OreGen
	{
		int minY;
		int maxY;
		WorldGenMinable genMinable;
		String name;
		int chunkOccurrence;
		int weight;
		
		public OreGen(String name, IBlockState state, int minY, int maxY, Block block, int veinSize, int chancesToSpawn, int weight)
		{
			this.name = name;
			this.weight = weight;
			this.minY = minY;
			this.maxY = maxY;
			this.genMinable = new WorldGenMinable(state, veinSize);
			this.chunkOccurrence = chancesToSpawn;
		}
		
		public void generate(World world, Random rand, int x, int z)
		{
			BlockPos pos;
			for (int i=0; i<chunkOccurrence; i++)
			{
				if (rand.nextInt(100) < weight)
				{
					pos = new BlockPos((x * 16) + rand.nextInt(16), minY + rand.nextInt(maxY - minY), (z * 16) + rand.nextInt(16));
					genMinable.generate(world, rand, pos);
				}
			}
		}
	}

	public List<OreGen> ores = new ArrayList<>();
	public static List<Integer> dimBlackList = new ArrayList<>();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider.getDimension() == 0)
		{
			for (OreGen ore : ores)
			{
				ore.generate(world, random, chunkX, chunkZ);
			}
		}
	}
}