package dalapo.factech.plugins.jei.wrappers;

import dalapo.factech.gui.GuiCircuitScribe;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.plugins.jei.BaseRecipeWrapper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class CircuitScribeRecipeWrapper extends BaseRecipeWrapper
{
	public CircuitScribeRecipeWrapper(int id)
	{
		outputID = id;
	}
	
	int outputID;
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		boolean[][] board = GuiCircuitScribe.getBoard(outputID);
		int boardLeft = 23;
		int boardTop = 80;
		for (int x=0; x<board.length*16; x+=16)
		{
			for (int y=0; y<board[x/16].length*16; y+=16)
			{
				if (board[x/16][y/16])
				{
					minecraft.currentScreen.drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFF808080);
				}
				else
				{
					minecraft.currentScreen.drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFFFFFFFF);
				}
			}
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, new ItemStack(ItemRegistry.circuitIntermediate, 1, 8));
		ingredients.setOutput(ItemStack.class, new ItemStack(ItemRegistry.circuitIntermediate, 1, outputID));
	}
}
