package dalapo.factech.block;

import dalapo.factech.auxiliary.Linkable;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.tileentity.specialized.TileEntityDecoCoil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDecoCoil extends BlockTENoDir implements Linkable
{
	public BlockDecoCoil(Material materialIn, String name)
	{
		super(materialIn, name);
	}

	@Override
	public void onLinked(World world, EntityPlayer ep, BlockPos thisPos, BlockPos otherPos, ItemStack linker)
	{
		TileEntity thisTE = world.getTileEntity(thisPos);
		TileEntity otherTE = world.getTileEntity(otherPos);
		if (thisTE instanceof TileEntityDecoCoil && otherTE instanceof TileEntityDecoCoil && FacMathHelper.absDist(thisPos, otherPos) < FacTechConfigManager.maxDecoCoilRange)
		{
			String msg;
			if (TileEntityDecoCoil.link((TileEntityDecoCoil)thisTE, (TileEntityDecoCoil)otherTE, true)) msg = "Linked!";
			else msg = "Unlinked!";
			if (world.isRemote) FacChatHelper.sendChatToPlayer(ep, msg);
		}
	}
}