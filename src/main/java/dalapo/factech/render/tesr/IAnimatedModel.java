package dalapo.factech.render.tesr;

import dalapo.factech.tileentity.TileEntityAnimatedModel;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public interface IAnimatedModel // Should only be implemented by classes extending BlockBase
{
	public void animate(TesrAnimatedModel callback, TileEntityAnimatedModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha);
}