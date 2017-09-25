package dalapo.factech.auxiliary;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;

// Credit to KingLemming and CoFH for this code
public class StateMap extends StateMapperBase implements ItemMeshDefinition {

	public ModelResourceLocation location;
	
	public StateMap(String mod, String file, String model)
	{
		location = new ModelResourceLocation(mod + ":" + file, model);
	}
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return location;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return location;
	}
}