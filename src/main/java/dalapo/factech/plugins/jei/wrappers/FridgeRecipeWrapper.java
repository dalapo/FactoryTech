package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

public class FridgeRecipeWrapper extends BaseRecipeWrapper {

	private FluidStack input;
	private ItemStack output;
	
	public FridgeRecipeWrapper(IGuiHelper guiHelper, FluidStack in, ItemStack out) {
		input = in;
		output = out;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(FluidStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth,
			int recipeHeight, int mouseX, int mouseY) {
		FacFluidRenderHelper.drawFluid(input, 22, 50, 16, 3);
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltips = super.getTooltipStrings(mouseX, mouseY);
		if (FacMathHelper.isInRange(mouseY, 4, 52) && FacMathHelper.isInRange(mouseX, 22, 38) && input != null)
		{
			tooltips.add(input.getLocalizedName());
			tooltips.add("mB: " + input.amount);
		}
		return tooltips;
	}
}
