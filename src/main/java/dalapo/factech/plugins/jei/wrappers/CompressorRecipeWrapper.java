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
import dalapo.factech.tileentity.specialized.TileEntityCompressionChamber.CompressorRecipe;

public class CompressorRecipeWrapper extends BaseRecipeWrapper {

	private FluidStack fluidIn;
	private ItemStack itemIn;
	private ItemStack itemOut;
	private boolean worksWithBad;
	public CompressorRecipeWrapper(CompressorRecipe recipe)
	{
		fluidIn = recipe.getFluidIn();
		itemIn = recipe.getItemIn();
		itemOut = recipe.getItemOut();
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(FluidStack.class, fluidIn);
		ingredients.setInput(ItemStack.class, itemIn);
		ingredients.setOutput(ItemStack.class, itemOut);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		FacFluidRenderHelper.drawFluid(fluidIn, 22, 50, 16, 3);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltips = new ArrayList<String>();
		if (FacMathHelper.isInRange(mouseY, 4, 52))
		{
			if (FacMathHelper.isInRange(mouseX, 22, 38) && fluidIn != null)
			{
				tooltips.add(fluidIn.getLocalizedName());
				tooltips.add("mB: " + fluidIn.amount);
			}
		}
		return tooltips;
	}
}