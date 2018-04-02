package dalapo.factech.plugins.jei.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Pair;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.plugins.jei.BaseRecipeCategory;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.StandardRecipeWrapper;

public class ElectroplaterRecipeCategory extends BaseRecipeCategory<StandardRecipeWrapper> {
	
	public ElectroplaterRecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "ftelectroplater", "electroplater_gui", 4, 16, 134, 58);
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new ElectroplaterRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftelectroplater");
	}

	public static List<StandardRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<StandardRecipeWrapper> recipes = new ArrayList<>();
		for (Pair<ItemStack, ItemStack> entry : MachineRecipes.ELECTROPLATER)
		{
			recipes.add(new StandardRecipeWrapper(guiHelper, entry.a, entry.b));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, StandardRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		guiItemstacks.init(0, true, 57, 21);
		guiItemstacks.init(1, false, 111, 21);
		guiItemstacks.set(0, inputs.get(0));
		guiItemstacks.set(1, outputs.get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		minecraft.fontRenderer.drawString("Sulphuric acid: 500mB", 42, 48, 0x00000000);
		FacFluidRenderHelper.drawFluid(new FluidStack(ModFluidRegistry.h2so4, 250), 22, 50, 16, 3);
	}

	@Override
	protected void addProgressBar(IGuiHelper helper) {
		IDrawableStatic fullBar = helper.createDrawable(new ResourceLocation(FacGuiHelper.formatTexName("electroplater_gui")), 176, 0, 30, 8);
		progressBar = helper.createAnimatedDrawable(fullBar, 40, StartDirection.LEFT, false);
	}
}