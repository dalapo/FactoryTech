package dalapo.factech.plugins.jei.categories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.plugins.jei.BaseRecipeCategory;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

public class StabilizerRecipeCategory extends BaseRecipeCategory<StandardRecipeWrapper> {
	
	public StabilizerRecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "ftsaw", "saw_gui");
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new StabilizerRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftsaw");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<StandardRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<StandardRecipeWrapper> recipes = new ArrayList<>();
		for (Entry<ItemStack, ItemStack> entry : MachineRecipes.SAW.entrySet())
		{
			recipes.add(new StandardRecipeWrapper(guiHelper, entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, StandardRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 24, 27);
		guiItemstacks.init(1, false, 78, 27);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
//		background.draw(minecraft);
		FacGuiHelper.renderItemStack(new ItemStack(ItemRegistry.machinePart, 1, 0), 52, 28);
	}

	@Override
	protected void addProgressBar(IGuiHelper helper) {
		
	}

}
