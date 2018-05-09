package dalapo.factech.plugins.jei.categories;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.plugins.jei.BaseRecipeCategory;
import dalapo.factech.plugins.jei.wrappers.AgitatorRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.CircuitScribeRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

public class CircuitScribeRecipeCategory extends BaseRecipeCategory<CircuitScribeRecipeWrapper>
{
	public CircuitScribeRecipeCategory(IGuiHelper helper)
	{
		super(helper, "ftcircuitscribe", "circscribe_gui", 8, 16, 120, 80);
	}

	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new CircuitScribeRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftcircuitscribe");
	}
	
	public static List<CircuitScribeRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<CircuitScribeRecipeWrapper> recipes = new ArrayList<>();
		for (int i=0; i<4; i++)
		{
			recipes.add(new CircuitScribeRecipeWrapper(i));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CircuitScribeRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 0, 12);
		guiItemstacks.init(1, false, 0, 54);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}

	@Override
	protected void addProgressBar(IGuiHelper helper)
	{
		
	}
}