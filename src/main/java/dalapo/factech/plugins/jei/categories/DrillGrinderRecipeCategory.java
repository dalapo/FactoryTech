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
import dalapo.factech.plugins.jei.BaseRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.CentrifugeRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;

public class DrillGrinderRecipeCategory extends BaseRecipeCategory<StandardRecipeWrapper> {

	public DrillGrinderRecipeCategory(IGuiHelper helper) {
		super(helper, "ftdrillgrinder", "drill_gui", 19, 17, 140, 64);
	}

	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipeCategories(new DrillGrinderRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftdrillgrinder");
	}
	
	public static List<StandardRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<StandardRecipeWrapper> recipes = new ArrayList<>();
		for (MachineRecipe<ItemStack, ItemStack> entry : MachineRecipes.OREDRILL)
		{
			recipes.add(new StandardRecipeWrapper(guiHelper, entry.worksWithBad(), entry.input(), entry.output()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, StandardRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 60, 0);
		guiItemstacks.init(1, false, 60, 37);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}

	@Override
	protected void addProgressBar(IGuiHelper helper)
	{
		
	}
}
