package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityDecoCoil extends TileEntityBase {
	private List<TileEntityDecoCoil> neighbours = new ArrayList<TileEntityDecoCoil>();
	
	@Override
	public void onLoad()
	{
		for (int x=-5; x<=5; x++)
		{
			for (int y=-5; y<=5; y++)
			{
				for (int z=-5; z<=5; z++)
				{
					TileEntity te = world.getTileEntity(new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z));
					if (te != this && te instanceof TileEntityDecoCoil)
					{
						neighbours.add((TileEntityDecoCoil)te);
					}
				}
			}
		}
	}
}