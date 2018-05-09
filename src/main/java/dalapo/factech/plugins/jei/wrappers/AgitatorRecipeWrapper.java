package dalapo.factech.plugins.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;
import dalapo.factech.tileentity.specialized.TileEntityAgitator.AgitatorRecipe;

public class AgitatorRecipeWrapper extends BaseRecipeWrapper {

	private FluidStack fluidIn1;
	private FluidStack fluidIn2;
	private ItemStack itemIn;
	private FluidStack fluidOut;
	private ItemStack itemOut;
	public AgitatorRecipeWrapper(AgitatorRecipe recipe)
	{
		fluidIn1 = recipe.getInputFluid(0);
		fluidIn2 = recipe.getInputFluid(1);
		itemIn = recipe.getInputItem();
		fluidOut = recipe.getOutputFluid();
		itemOut = recipe.getOutputItem();
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		List<FluidStack> fluids = new ArrayList<>();
		fluids.add(fluidIn1);
		fluids.add(fluidIn2);
		ingredients.setInputs(FluidStack.class, fluids);
		ingredients.setInput(ItemStack.class, itemIn);
		ingredients.setOutput(FluidStack.class, fluidOut);
		ingredients.setOutput(ItemStack.class, itemOut);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		FacFluidRenderHelper.drawFluid(fluidIn1, 4, 50, 16, 3);
		FacFluidRenderHelper.drawFluid(fluidIn2, 22, 50, 16, 3);
		FacFluidRenderHelper.drawFluid(fluidOut, 76, 50, 16, 3);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltips = new ArrayList<String>();
		if (FacMathHelper.isInRange(mouseY, 4, 52))
		{
			if (FacMathHelper.isInRange(mouseX, 4, 20) && fluidIn1 != null)
			{
				tooltips.add(fluidIn1.getLocalizedName());
				tooltips.add("mB: " + fluidIn1.amount);
			}
			else if (FacMathHelper.isInRange(mouseX, 22, 38) && fluidIn2 != null)
			{
				tooltips.add(fluidIn2.getLocalizedName());
				tooltips.add("mB: " + fluidIn2.amount);
			}
			else if (FacMathHelper.isInRange(mouseX, 76, 92) && fluidOut != null)
			{
				tooltips.add(fluidOut.getLocalizedName());
				tooltips.add("mB: " + fluidOut.amount);
			}
		}
		return tooltips;
	}
}