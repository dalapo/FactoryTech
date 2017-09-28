package dalapo.factech.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeWrapper implements IRecipeWrapper {
	private static final boolean DEBUG_MOUSE = false;
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		if (DEBUG_MOUSE)
		{
			List coords = new ArrayList<String>();
			coords.add(String.format("%s, %s", mouseX, mouseY));
			return coords;
		}
		return new ArrayList<String>();
	}
}