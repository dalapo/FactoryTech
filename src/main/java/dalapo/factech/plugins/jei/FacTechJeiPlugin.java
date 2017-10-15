package dalapo.factech.plugins.jei;

import net.minecraft.item.ItemStack;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.plugins.jei.categories.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class FacTechJeiPlugin implements IModPlugin
{
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		SawRecipeCategory.register(registry);
		DrillGrinderRecipeCategory.register(registry);
		ElectroplaterRecipeCategory.register(registry);
		AgitatorRecipeCategory.register(registry);
		GrindstoneRecipeCategory.register(registry);
		CentrifugeRecipeCategory.register(registry);
		MetalCutterRecipeCategory.register(registry);
		ElecFurnaceRecipeCategory.register(registry);
		CrucibleRecipeCategory.register(registry);
		FridgeRecipeCategory.register(registry);
		TempererRecipeCategory.register(registry);
		CompressorRecipeCategory.register(registry);
	}
	
	@Override
	public void register(IModRegistry registry)
	{
		SawRecipeCategory.init(registry);
		DrillGrinderRecipeCategory.init(registry);
		ElectroplaterRecipeCategory.init(registry);
		AgitatorRecipeCategory.init(registry);
		GrindstoneRecipeCategory.init(registry);
		CentrifugeRecipeCategory.init(registry);
		MetalCutterRecipeCategory.init(registry);
		ElecFurnaceRecipeCategory.init(registry);
		CrucibleRecipeCategory.init(registry);
		FridgeRecipeCategory.init(registry);
		TempererRecipeCategory.init(registry);
		CompressorRecipeCategory.init(registry);
		
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.saw), "saw");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.oredrill), "drillgrinder");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.electroplater), "electroplater");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.agitator), "agitator");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.grindstone), "grindstone");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.centrifuge), "centrifuge");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.metalCutter), "metalcutter");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.htfurnace), "elecfurnace");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.crucible), "crucible");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.refrigerator), "fridge");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.temperer), "temperer");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.compressionChamber), "compressor");
	}
}