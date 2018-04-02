package dalapo.factech.plugins.jei.wrappers;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;

public class CrucibleRecipeWrapper extends BaseRecipeWrapper {

	private ItemStack input;
	private FluidStack output;
	
	public CrucibleRecipeWrapper(IGuiHelper helper, ItemStack in, FluidStack out)
	{
		input = in;
		output = out;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(FluidStack.class, output);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FacFluidRenderHelper.drawFluid(output, 56, 46, 32, 1);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = super.getTooltipStrings(mouseX, mouseY);
		if (FacMathHelper.isInRange(mouseX, 56, 88) && FacMathHelper.isInRange(mouseY, 30, 46))
		{
			tooltip.add(output.getLocalizedName());
			tooltip.add(String.format("mB: %s", output.amount));
		}
		return tooltip;
	}
	
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
			int mouseButton) {
		// TODO Auto-generated method stub
		return false;
	}

}
