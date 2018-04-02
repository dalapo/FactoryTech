package dalapo.factech.block;

import java.util.Random;

import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockElevator extends BlockTENoDir {
	public BlockElevator(Material materialIn, String name, boolean locked) {
		super(materialIn, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		if (worldIn.isRemote && worldIn.isBlockPowered(pos))
		{
        	double offX = rand.nextDouble() - 0.5;
        	double offZ = rand.nextDouble() - 0.5;
        	double motion = (rand.nextDouble() / 5.0) + 0.1;
        	worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, true, pos.getX()+0.5+offX, pos.getY()+1.2, pos.getZ()+0.5+offZ, 0, motion, 0);
		}
    }
}