package dalapo.factech.gui.handbook;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.MachinePart;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.config.MachineDefaults;
import dalapo.factech.gui.GuiCircuitScribe;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.Logger;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.translation.I18n;

public class GuiHandbookPage extends GuiHandbookBase
{
	protected static final int GRID_X_OFFSET = 80;
	protected static final int GRID_Y_OFFSET = 40;
	
	protected String title;
	protected int page = 0;
	protected int lastPage;
	protected String machineClass;
	protected List<String> text = new ArrayList<String>();
	protected List<IRecipe> recipes = new ArrayList<IRecipe>();
	protected ItemStack[][] recipe = new ItemStack[3][3];
	protected ItemStack result = ItemStack.EMPTY;
	protected GuiHandbookSubmenu parent;
	
	public GuiHandbookPage(HandbookEntry entry, GuiHandbookSubmenu parent)
	{
		super();
		title = entry.title;
		text.addAll(entry.pages);
		recipes.addAll(entry.recipe);
		this.parent = parent;
		this.machineClass = entry.machine;
		resetGrid();
		if (machineClass != null && !machineClass.isEmpty()) text.add(getPartSpecs());
		lastPage = text.size()-1;
		if (!recipes.isEmpty()) lastPage++;
		
	}
	
	private boolean[][] getCircuitBoard(int pattern)
	{
		return GuiCircuitScribe.getBoard(pattern);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
//		Logger.info(String.format("%s, %s", title, page, parent.getParent()));
		buttonList.add(new GuiButton(0, guiLeft + 24, guiTop + 136, 20, 20, "X"));
		buttonList.add(new GuiButton(1, guiLeft + 4, guiTop + 136, 20, 20, "<"));
		buttonList.add(new GuiButton(2, guiLeft + 44, guiTop + 136, 20, 20, ">"));
		
		buttonList.get(1).enabled = page != 0;
		buttonList.get(2).enabled = page != lastPage;
	}
	
	@Override
	public void onGuiClosed()
	{
		parent.onGuiClosed();
	}
	
	@Override
	public void actionPerformed(GuiButton b)
	{
//		Logger.info(String.format("%s, %s", title, page));
		if (b.id == 0) mc.displayGuiScreen(parent);
		else if (b.id == 1 && page != 0) page--;
		else if (b.id == 2 && page < lastPage) page++;
		
		parent.getParent().setPage(page);
		background = FactoryTech.random.nextInt(4);
		buttonList.get(1).enabled = page != 0;
		buttonList.get(2).enabled = page != lastPage;
	}
	
	private String describePart(MachinePart part)
	{
		return String.format("%s: Min %s ops, %s break chance, %s increase, %s salvage\n", FacMiscHelper.capitalizeFirstLetter(part.id.getName()).replace('_', ' '), part.getMinOperations(), part.getBaseChance(), part.getIncrease(), part.getSalvageChance());
	}
	
	private String getPartSpecs()
	{
		MachineDefaults md = MachineDefaults.getFromString(machineClass);
		int numParts = md.partsNeeded.length;
		MachinePart[] toCopy = FacTechConfigManager.allParts.get(md.clazz);
		String str = "Parts Needed:\n\n";
		for (int i=0; i<toCopy.length; i++)
		{
			str += describePart(toCopy[i]) + '\n';
		}
		return str;
	}
	
	protected void showRecipes()
	{
		if (recipes.size() != 0)
		{
			for (int i=0; i<3; i++)
			{
				for (int j=0; j<3; j++)
				{
					recipe[i][j] = ItemStack.EMPTY;
				}
			}
			IRecipe recipe = recipes.get((int)((System.currentTimeMillis() / 1000) % recipes.size()));
			for(int row=0; row<3; row++)
			{
				for (int col=0; col<3; col++)
				{
					// TODO: Fix this in a more elegant way than try/catch
					try {
						if (recipe.getIngredients().get(row*3 + col).getMatchingStacks().length > 0)
						{
							ItemStack is = recipe.getIngredients().get(row*3 + col).getMatchingStacks()[0];
							this.recipe[row][col] = is;
						}
					}
					catch (ArrayIndexOutOfBoundsException | NullPointerException ex)
					{
						break;
					}
				}
			}
			result = recipe.getRecipeOutput();
		}
		else
		{
			resetGrid();
		}
	}
	
	public void resetGrid()
	{
		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				recipe[i][j] = ItemStack.EMPTY;
			}
		}
		result = ItemStack.EMPTY;
	}
	
	// TODO: Show circuit board pattern
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (page < text.size())
		{
			fontRenderer.drawString(title, guiLeft + 4, guiTop + 4, 0xFFFFFFFF);
			fontRenderer.drawSplitString(text.get(page), guiLeft + 4, guiTop + 16, 248, 0xFFFFFFFF);
		}
		
		// I feel like Linus Torvalds is the Gordon Ramsay of programming
		// He'd certainly have some choice words to say about this implementation
		if (title.equals("Circuit Board") && FacMathHelper.isInRangeInclusive(page, 2, 5))
		{
			int boardLeft = guiLeft + 64;
			int boardTop = guiTop + 120;
			
			boolean[][] board = getCircuitBoard(page - 2);
			for (int x=0; x<board.length*16; x+=16)
			{
				for (int y=0; y<board[x/16].length*16; y+=16)
				{
					if (board[x/16][y/16])
					{
						drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFF808080);
					}
					else
					{
						drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFFFFFFFF);
					}
				}
			}
		}
		
		if (recipes.size() > 0 && page == lastPage)
		{
			showRecipes();
			FacGuiHelper.bindTex("handbook_gui_0");
			this.drawTexturedModalRect(guiLeft + GRID_X_OFFSET - 1, guiTop + GRID_Y_OFFSET - 1, 0, 160, 116, 54);
			
			for (int row=0; row<3; row++)
			{
				for (int col=0; col<3; col++)
				{
					FacGuiHelper.renderItemStack(recipe[row][col], guiLeft + col*18 + GRID_X_OFFSET, guiTop + row*18 + GRID_Y_OFFSET);
				}
			}
			FacGuiHelper.renderItemStack(result, guiLeft + 94 + GRID_X_OFFSET, guiTop + 18 + GRID_Y_OFFSET);
		}
	}
}