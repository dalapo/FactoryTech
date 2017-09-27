package dalapo.factech.plugins.jei.saw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.plugins.jei.BaseRecipeCategory;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;

public class SawRecipeCategory extends BaseRecipeCategory<SawRecipeWrapper> {
	
	public SawRecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "saw", "saw_gui");
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new SawRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "saw");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<SawRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<SawRecipeWrapper> recipes = new ArrayList<>();
		for (Entry<ItemStack, ItemStack> entry : MachineRecipes.SAW.entrySet())
		{
			recipes.add(new SawRecipeWrapper(guiHelper, entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SawRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 31, 27);
		guiItemstacks.init(1, false, 85, 27);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}
	
	@Override
	@Nullable
	public IDrawable getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		background.draw(minecraft);
	}

}
