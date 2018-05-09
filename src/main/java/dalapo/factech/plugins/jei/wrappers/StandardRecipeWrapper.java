package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class StandardRecipeWrapper extends BaseRecipeWrapper {
	
	public ItemStack input;
	public ItemStack output;
	private boolean worksWithBad;
	
	public StandardRecipeWrapper(IGuiHelper guiHelper, boolean worksWithBad, ItemStack in, ItemStack out)
	{
		input = in;
		output = out;
		this.worksWithBad = worksWithBad;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		if (!worksWithBad)
		{
			FacGuiHelper.bindTex("nostone");
			FacGuiHelper.drawTexturedModalRect(0, 28, 100, 0, 0, 16, 16);
//			minecraft.fontRenderer.drawString(I18n.format("factorytech:jei.nostone"), -16, 16, 0xD03030);
		}
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltips = new ArrayList<String>();
		if (!worksWithBad &&  FacMathHelper.isInRange(mouseX, 0, 16) && FacMathHelper.isInRange(mouseY, 28, 44))
		{
			tooltips.add(I18n.format("factorytech:jei.nostone"));
		}
		return tooltips;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
//		Logger.info(String.format("{%s, %s}", mouseX, mouseY));
		return false;
	}

}
