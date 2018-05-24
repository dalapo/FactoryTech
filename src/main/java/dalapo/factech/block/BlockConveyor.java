package dalapo.factech.block;

import org.lwjgl.opengl.GL11;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacRenderHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.reference.StateList;
import dalapo.factech.render.tesr.IAnimatedModel;
import dalapo.factech.render.tesr.TesrAnimatedModel;
import dalapo.factech.tileentity.TileEntityAnimatedModel;
import dalapo.factech.tileentity.automation.TileEntityConveyor;
import dalapo.factech.tileentity.automation.TileEntityElevator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockConveyor extends BlockDirectional // implements IAnimatedModel
{
	private static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
	public BlockConveyor(Material materialIn, String name, boolean locked) {
		super(materialIn, name, true);
		setHardness(2F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return boundingBox;
    }
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return boundingBox.offset(pos);
    }

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		return boundingBox;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		TileEntityConveyor conveyor = (TileEntityConveyor)world.getTileEntity(pos);
		conveyor.onLoad();
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state)
	{
		return false;
	}
	
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
		if (entity.posY % 1 < 0.25)
		{
			if (entity instanceof EntityItem && !entity.isDead)
			{
				entity.setDead();
				ItemStack is = ((EntityItem)entity).getItem();
				TileEntityConveyor te = (TileEntityConveyor)world.getTileEntity(pos);
				te.scheduleItemStack(is);
			}
			
	        if (entity instanceof EntityLivingBase && !entity.isSneaking())
	        {
	          	EnumFacing facing = world.getBlockState(pos).getValue(StateList.directions);
		    	// Need to add/subtract motion instead of setting it
		    	switch (facing)
		    	{
		    	case WEST:
		    		entity.motionX = 0.1;
		    		break;
		    	case EAST:
		    		entity.motionX = -0.1;
		    		break;
		    	case SOUTH:
		    		entity.motionZ = -0.1;
		    		break;
		    	case NORTH:
		    		entity.motionZ = 0.1;
		    		break;
		    		default:
		    			entity.motionX = 0.0;
		    			entity.motionZ = 0.0;
		    	}
	        }
		}
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		if (state.getValue(PART_ID) == 1) return true;
		return super.shouldSideBeRendered(state, world, pos, facing);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack itemstack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		Logger.info(world.getBlockState(pos).getValue(StateList.directions));
		world.setTileEntity(pos, new TileEntityConveyor());
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityConveyor();
	}
	
	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void animate(TesrAnimatedModel callback, TileEntityAnimatedModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		RenderHelper.enableStandardItemLighting();
		FacRenderHelper.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (Minecraft.isAmbientOcclusionEnabled())
		{
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		}
		else
		{
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}
		
		World world = te.getWorld();
		
		// laaaaaaag
		for (double i=0; i<1; i+=0.125)
		{
			GlStateManager.pushMatrix();
			Tessellator v5 = Tessellator.getInstance();
			BufferBuilder builder = v5.getBuffer();
			IBlockState state = getDefaultState().withProperty(PART_ID, 1);
			BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

			IBakedModel model = dispatcher.getModelForState(state);
			builder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			GlStateManager.translate(-0.49, 0.03125, i-0.46875);
			GlStateManager.scale(0.98, 1, 1);
			long angle = System.currentTimeMillis() / 8 % 360;
			if (!Minecraft.getMinecraft().isGamePaused()) GlStateManager.rotate(angle, -1, 0, 0);
			GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY()-0.03125, -te.getPos().getZ()-0.03125);
			dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), builder, false);	
			v5.draw();
			GlStateManager.popMatrix();
		}
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}
	*/
}
