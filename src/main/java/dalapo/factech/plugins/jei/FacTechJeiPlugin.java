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
		MagnetizerRecipeCategory.register(registry);
		DisassemblerRecipeCategory.register(registry);
		StabilizerRecipeCategory.register(registry);
		MagCentrifugeRecipeCategory.register(registry);
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
		MagnetizerRecipeCategory.init(registry);
		DisassemblerRecipeCategory.init(registry);
		StabilizerRecipeCategory.init(registry);
		MagCentrifugeRecipeCategory.init(registry);
		
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.saw), "ftsaw");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.oredrill), "ftdrillgrinder");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.electroplater), "ftelectroplater");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.agitator), "ftagitator");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.grindstone), "ftgrindstone");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.centrifuge), "ftcentrifuge");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.metalCutter), "ftmetalcutter");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.htfurnace), "ftelecfurnace");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.crucible), "ftcrucible");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.refrigerator), "ftfridge");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.temperer), "fttemperer");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.compressionChamber), "ftcompressor");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.magnetizer), "ftmagnetizer");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.disassembler), "ftdisassembler");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.stabilizer), "ftstabilizer");
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.magCentrifuge), "ftmagcentrifuge");
	}
}