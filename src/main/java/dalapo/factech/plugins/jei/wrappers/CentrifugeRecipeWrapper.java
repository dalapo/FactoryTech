package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

public class CentrifugeRecipeWrapper extends BaseRecipeWrapper {

	ItemStack input;
	ItemStack[] outputs;
	boolean worksWithBad;
	public CentrifugeRecipeWrapper(IGuiHelper helper, boolean worksWithBad, ItemStack in, ItemStack[] out)
	{
		input = in;
		outputs = out;
		this.worksWithBad = worksWithBad;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, input);
		List<ItemStack> runningOutputs = new ArrayList<ItemStack>();
		for (int i=0; i<3; i++)
		{
			if (i < outputs.length) runningOutputs.add(outputs[i]);
			else runningOutputs.add(ItemStack.EMPTY);
		}
		ingredients.setOutputs(ItemStack.class, runningOutputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (!worksWithBad)
		{
			FacGuiHelper.bindTex("nostone");
			FacGuiHelper.drawTexturedModalRect(0, 28, 100, 0, 0, 16, 16);
//			minecraft.fontRenderer.drawString(I18n.format("factorytech:jei.nostone"), -16, 16, 0xD03030);
		}
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltips = new ArrayList<>();
		if (!worksWithBad &&  FacMathHelper.isInRange(mouseX, 0, 16) && FacMathHelper.isInRange(mouseY, 28, 44))
		{
			tooltips.add(I18n.format("factorytech:jei.nostone"));
		}
		return tooltips;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		// TODO Auto-generated method stub
		return false;
	}

}
