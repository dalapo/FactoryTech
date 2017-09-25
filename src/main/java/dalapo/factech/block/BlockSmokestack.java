package dalapo.factech.block;

import java.util.Random;

import dalapo.factech.auxiliary.EnumBrickType;
import dalapo.factech.auxiliary.EnumSmokestackType;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Delete TileEntitySmokestack
public class BlockSmokestack extends BlockBase implements IBlockSubtypes {

	public BlockSmokestack(Material materialIn, String name) {
		super(materialIn, name);
		setDefaultState(blockState.getBaseState().withProperty(StateList.smokestacktype, EnumSmokestackType.BRICKS));
		setTickRandomly(true);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ResourceLocation[] arr = new ResourceLocation[EnumSmokestackType.values().length];
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = new ResourceLocation(NameList.MODID + ":" + name + "_" + EnumSmokestackType.values()[i].getName());
		}
		ModelBakery.registerItemVariants(Item.getItemFromBlock(this), arr);
		
		for (int i=0; i<arr.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(NameList.MODID + ":smokestack", EnumSmokestackType.values()[i].getName()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> stacks)
	{
		if (tab == TabRegistry.FACTECH)
		{
			for (int i=0; i<EnumSmokestackType.values().length; i++)
			{
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
//		Logger.info("Updating tick");
		if (worldIn.isRemote)
		{
	        for (int i = 0; i < 10; ++i)
	        {
	        	double offX = rand.nextDouble() - 0.5;
	        	double offZ = rand.nextDouble() - 0.5;
	        	double motion = (rand.nextDouble() / 5.0) + 0.1;
	        	worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, pos.getX()+0.5+offX, pos.getY()+1.2, pos.getZ()+0.5+offZ, 0, motion, 0);
	        }
		}
    }
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, StateList.smokestacktype);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(StateList.smokestacktype, EnumSmokestackType.getType(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.smokestacktype).getMeta();
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(StateList.smokestacktype).getMeta();
	}

	@Override
	public String getName(ItemStack is)
	{
		return name;
	}
}