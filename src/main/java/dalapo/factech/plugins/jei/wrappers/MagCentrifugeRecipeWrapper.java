package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

public class MagCentrifugeRecipeWrapper extends BaseRecipeWrapper {

	ItemStack input;
	ItemStack[] outputs;
	
	public MagCentrifugeRecipeWrapper(IGuiHelper helper, ItemStack in, ItemStack[] out)
	{
		input = in;
		outputs = out;
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
	public void drawInfo(Minecraft minecraft, int recipeWidth,
			int recipeHeight, int mouseX, int mouseY) {
		
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		// TODO Auto-generated method stub
		return false;
	}

}
