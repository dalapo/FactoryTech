package dalapo.factech.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAnimationTest extends BlockTENoDir
{
	public static final IProperty<Boolean> isCentre = PropertyBool.create("isCentre");
	public BlockAnimationTest(Material materialIn, String name)
	{
		super(materialIn, name);
		setDefaultState(blockState.getBaseState().withProperty(isCentre, false));
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(isCentre, false);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, isCentre);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
}