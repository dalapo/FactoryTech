package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.Logger;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DisassemblerRecipeWrapper extends BaseRecipeWrapper
{	
	public String input;
	public List<ItemStack> output;
	
	public DisassemblerRecipeWrapper(IGuiHelper guiHelper, String in, List<ItemStack> out)
	{
		input = in;
		output = out;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setOutputs(ItemStack.class, output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString(input, 0, 0, 0x0000000);
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		return false;
	}

}
