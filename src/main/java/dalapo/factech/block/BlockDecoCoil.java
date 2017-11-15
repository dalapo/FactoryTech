package dalapo.factech.block;

import dalapo.factech.auxiliary.Linkable;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.tileentity.specialized.TileEntityDecoCoil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDecoCoil extends BlockTENoDir implements Linkable
{
	public BlockDecoCoil(Material materialIn, String name) {
		super(materialIn, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLinked(World world, EntityPlayer ep, BlockPos origin, BlockPos thisPos, ItemStack linker)
	{
		TileEntity te = world.getTileEntity(origin);
		if (te instanceof TileEntityDecoCoil)
		{
			((TileEntityDecoCoil)te).setNeighbour(thisPos);
			if (world.isRemote) FacChatHelper.sendChatToPlayer(ep, "Linked!");
		}
	}
}