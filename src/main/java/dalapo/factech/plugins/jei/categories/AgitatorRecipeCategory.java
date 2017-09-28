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
import dalapo.factech.plugins.jei.BaseRecipeCategory;
import dalapo.factech.plugins.jei.wrappers.AgitatorRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;

public class AgitatorRecipeCategory extends BaseRecipeCategory<AgitatorRecipeWrapper> {

	public AgitatorRecipeCategory(IGuiHelper helper) {
		super(helper, "agitator", "agitator_gui", 4, 16, 140, 56);
		// TODO Auto-generated constructor stub
	}

	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new AgitatorRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "agitator");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<AgitatorRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<AgitatorRecipeWrapper> recipes = new ArrayList<>();
		for (AgitatorRecipe recipe : MachineRecipes.AGITATOR)
		{
			recipes.add(new AgitatorRecipeWrapper(recipe));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AgitatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 47, 30);
		guiItemstacks.init(1, false, 103, 30);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}
	
	@Override
	protected void addProgressBar(IGuiHelper helper) {
		// TODO Auto-generated method stub
		
	}

}
