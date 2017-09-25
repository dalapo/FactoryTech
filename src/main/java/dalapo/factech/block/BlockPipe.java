package dalapo.factech.block;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import dalapo.factech.render.PipeBakedModel;
import dalapo.factech.render.UnlistedPropertyAdjacentBlock;
import dalapo.factech.tileentity.automation.TileEntityFluidPuller;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPipe extends BlockTENoDir {

	public static final UnlistedPropertyAdjacentBlock NORTH = new UnlistedPropertyAdjacentBlock("north");
	public static final UnlistedPropertyAdjacentBlock SOUTH = new UnlistedPropertyAdjacentBlock("south");
	public static final UnlistedPropertyAdjacentBlock EAST = new UnlistedPropertyAdjacentBlock("east");
	public static final UnlistedPropertyAdjacentBlock WEST = new UnlistedPropertyAdjacentBlock("west");
	public static final UnlistedPropertyAdjacentBlock UP = new UnlistedPropertyAdjacentBlock("up");
	public static final UnlistedPropertyAdjacentBlock DOWN = new UnlistedPropertyAdjacentBlock("down");
	
	public BlockPipe(Material materialIn, String name) {
		super(materialIn, name);
		setUnlocalizedName(NameList.MODID + ".pipe");
//		setRegistryName("pipe");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		StateMapperBase ignoreState = new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return PipeBakedModel.BAKED_MODEL;
			}
		};
		ModelLoader.setCustomStateMapper(this, ignoreState);
	}
	
	@SideOnly(Side.CLIENT)
	public void initInvModel()
	{
		Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(NameList.MODID, "pipe"));	
		ModelResourceLocation loc = new ModelResourceLocation(getRegistryName(), "inventory");
		Minecraft.
		getMinecraft().
		getRenderItem().
		getItemModelMesher().
		register(itemBlock, 0, loc);
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		IProperty<?>[] listed = new IProperty[0];
		IUnlistedProperty<?>[] unlisted = new IUnlistedProperty[] {DOWN, UP, NORTH, SOUTH, WEST, EAST};
		return new ExtendedBlockState(this, listed, unlisted);
	}
	
	private boolean shouldConnect(IBlockAccess world, BlockPos pos, EnumFacing direction)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te == null) return false;
		if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())) return true;
		return te instanceof TileEntityFluidPuller && world.getBlockState(pos).getValue(StateList.directions) == direction.getOpposite();
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		IExtendedBlockState stateEx = (IExtendedBlockState)state;
		boolean[] sameBlocks = new boolean[6];
		for (int i=0; i<6; i++)
		{
			sameBlocks[i] = shouldConnect(world, FacMathHelper.withOffset(pos, EnumFacing.getFront(i)), EnumFacing.getFront(i));
		}
		return stateEx.withProperty(DOWN, sameBlocks[0])
				.withProperty(UP, sameBlocks[1])
				.withProperty(NORTH, sameBlocks[2])
				.withProperty(SOUTH, sameBlocks[3])
				.withProperty(WEST, sameBlocks[4])
				.withProperty(EAST, sameBlocks[5]);
	}
	
}