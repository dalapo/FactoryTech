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
import dalapo.factech.plugins.jei.wrappers.DisassemblerRecipeWrapper;
import dalapo.factech.plugins.jei.wrappers.DisassemblerRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
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

public class DisassemblerRecipeCategory extends BaseRecipeCategory<DisassemblerRecipeWrapper> {
	
	public DisassemblerRecipeCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "ftdisassembler", "gui_blank");
	}
	
	public static void register(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		
		registry.addRecipeCategories(new DisassemblerRecipeCategory(guiHelper));
	}
	
	public static void init(IModRegistry registry)
	{
		IJeiHelpers jeihelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeihelpers.getGuiHelper();
		registry.addRecipes(getRecipes(guiHelper), "ftdisassembler");
//		registry.addRecipeCatalyst(BlockRegistry.saw, "saw");
	}

	public static List<DisassemblerRecipeWrapper> getRecipes(IGuiHelper guiHelper)
	{
		List<DisassemblerRecipeWrapper> recipes = new ArrayList<>();
		for (Entry<Class<? extends EntityLivingBase>, List<ItemStack>> entry : MachineRecipes.DISASSEMBLER.entrySet())
		{
			recipes.add(new DisassemblerRecipeWrapper(guiHelper, entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DisassemblerRecipeWrapper recipeWrapper, IIngredients ingredients) {
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		IGuiItemStackGroup guiItemstacks = recipeLayout.getItemStacks();
		for (int i=0; i<outputs.size(); i++)
		{
			guiItemstacks.init(i, false, i*18, 20);
			guiItemstacks.set(i, outputs.get(i));
		}
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
