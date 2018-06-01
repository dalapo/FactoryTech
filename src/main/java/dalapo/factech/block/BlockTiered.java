package dalapo.factech.block;

import net.minecraft.block.material.Material;

public class BlockTiered extends BlockDirectionalTile
{
	public BlockTiered(Material materialIn, String name, String teid, boolean locked, int tier) {
		super(materialIn, name, teid, locked);
		this.tier = tier;
	}
	public final int tier;
}