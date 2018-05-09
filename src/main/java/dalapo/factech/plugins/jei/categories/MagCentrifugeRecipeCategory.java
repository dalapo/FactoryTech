package dalapo.factech.plugins.jei.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.MachineRecipes.MachineRecipe;
import dalapo.factech.plugins.jei.BaseRecipeCategory;
import dalapo.factech.plugins.jei.wrappers.CentrifugeRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.MagCentrifugeRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;

public class MagCentrifugeRecipeCategory extends BaseRecipeCategory<MagCentrifugeRecipeWrapper> {

	public MagCentrifugeRecipeCategory(IGuiHelper helper) {
		super(helper, "ftmagcentrifuge", "centrifuge_gui");
		// TODO Auto-generated constructor stub
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new MagCentrifugeRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftmagcentrifuge");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<MagCentrifugeRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<MagCentrifugeRecipeWrapper> recipes = new ArrayList<>();
		for (MachineRecipe<ItemStack, ItemStack[]> entry : MachineRecipes.MAGNET_CENTRIFUGE)
		{
			recipes.add(new MagCentrifugeRecipeWrapper(guiHelper, entry.input(), entry.output()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MagCentrifugeRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 24, 27);
		guiItemstacks.init(1, false, 78, 27);
		guiItemstacks.init(2,  false, 78, 9);
		guiItemstacks.init(3, false, 78, 45);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
		guiItemstacks.set(2, outputs.get(1));
		guiItemstacks.set(3, outputs.get(2));
	}

	@Override
	protected void addProgressBar(IGuiHelper helper) {
		// TODO Auto-generated method stub
		
	}

}
