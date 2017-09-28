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
import dalapo.factech.plugins.jei.wrappers.FridgeRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.FridgeRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
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

public class FridgeRecipeCategory extends BaseRecipeCategory<FridgeRecipeWrapper> {
	
	public FridgeRecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "fridge", "fridge_gui", 4, 16, 140, 56);
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new FridgeRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "fridge");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<FridgeRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<FridgeRecipeWrapper> recipes = new ArrayList<>();
		for (Entry<FluidStack, ItemStack> entry : MachineRecipes.REFRIGERATOR.entrySet())
		{
			recipes.add(new FridgeRecipeWrapper(guiHelper, entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FridgeRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, false, 93, 21);
		guiItemstacks.set(0, outputs.get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
//		background.draw(minecraft);
//		FacGuiHelper.renderItemStack(new ItemStack(ItemRegistry.machinePart, 1, 0), 52, 28);
	}

	@Override
	protected void addProgressBar(IGuiHelper helper) {
		
	}

}
