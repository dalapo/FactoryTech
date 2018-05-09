package dalapo.factech.gui.handbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;

import dalapo.factech.FactoryTech;
import dalapo.factech.gui.GuiCircuitScribe;
import dalapo.factech.gui.widget.WidgetCustomButton;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiHandbook extends GuiHandbookBase
{
	protected static final int GRID_X_OFFSET = 124;
	protected static final int GRID_Y_OFFSET = 96;
	
	private static int[] backgroundLookup = new int[64];
	public static List<ArrayList<HandbookEntry>> entries = new ArrayList<>();
	public static Map<String, IRecipe[]> recipes = new HashMap<>();
	
	private ItemStack book;
	
	private static final int[] pageCounts = new int[7];
	private boolean drawGrid = false;
	private boolean drawBoard = false;
	private ItemStack[][] recipe;
	private boolean[][] board;
	private ItemStack result;
	
	GuiButton[] buttons = new GuiButton[10];
	
	int page = 0;
	int entry = 0;
	int numPages = 0;
	int section = -1;
	
	private static final String[] submenuNames = new String[] {"The Basics", "Machine info", "Part specifications", "Tools and Devices", "Automation components", "Resources", "Miscellaneous"};
	
	public static void initBackgrounds()
	{
		for (int i=0; i<backgroundLookup.length; i++)
		{
			backgroundLookup[i] = FactoryTech.random.nextInt(4);
		}
	}
	
	public static void setPageCount(int section, int count)
	{
		if (FacMathHelper.isInRange(section, 0, pageCounts.length))
		{
			pageCounts[section] = count;
		}
	}
	
	public GuiHandbook(ItemStack is)
	{
		super();
		
		book = is;
		if (is.hasTagCompound())
		{
			NBTTagCompound nbt = is.getTagCompound();
			if (nbt.hasKey("section")) section = nbt.getInteger("section");
			else section = -1;
			page = nbt.getInteger("page");
		}
		recipe = new ItemStack[3][3];
		resetGrid();
	}
	
	public GuiHandbook(EntityPlayer ep)
	{
		this(ep.getHeldItemMainhand());
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		if (!book.hasTagCompound())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("section", section);
			nbt.setInteger("page", page);
			nbt.setInteger("entry", entry);
			book.setTagCompound(nbt);
		}
		else
		{
			book.getTagCompound().setInteger("section", section);
			book.getTagCompound().setInteger("page", page);
			book.getTagCompound().setInteger("entry", entry);
		}
//		Logger.info(String.format("Closing GUI. Section %s, entry %s, page %s", section, entry, page));
	}
	
	private void resetGrid()
	{
		recipes.clear();
		for (int i=0; i<3; i++)
		{
			for (int j=0; j<3; j++)
			{
				recipe[i][j] = ItemStack.EMPTY;
			}
		}
		result = ItemStack.EMPTY;
	}
	
	@Override
	public void initGui()
	{
		showMenu(0);
		section = -1;
		
		section = -1;
		if (book.hasTagCompound())
		{
			NBTTagCompound nbt = book.getTagCompound();
			int s = nbt.getInteger("section");
			int e = nbt.getInteger("entry");
			int p = nbt.getInteger("page");
			if (s != -1)
			{
				goToPage(s, e, p);
			}
		}
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		for (GuiButton b : buttonList)
		{
			if (b.visible) b.drawButton(mc, mouseX, mouseY, partialTicks);
		}
	}
	
	private void showMenu(int menuID)
	{
		String[] names = new String[] {"The Basics", "Machine info", "Part specifications", "Tools and Devices", "Automation components", "Resources", "Miscellaneous"};
//		Logger.info(String.format("Width: %s; height: %s in GuiHandbook", width, height));
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		for (int i=0; i<names.length; i++)
		{
			addButton(buttons[i] = new WidgetCustomButton(this, i, guiLeft + 66, guiTop + 28 + (i*14), 120, 12, names[i], "button_handbook"));
		}
	}
	@Override
	public void actionPerformed(GuiButton b)
	{
		buttonList.clear();
		section = b.id;
		mc.displayGuiScreen(new GuiHandbookSubmenu(submenuNames[b.id], FactoryTech.random.nextInt(4), b.id, 0, entries.get(b.id), this));
	}
	
	protected void setSection(int s)
	{
		this.section = s;
	}
	
	protected void setEntry(int e)
	{
		this.entry = e;
	}
	
	protected void setPage(int p)
	{
		this.page = p;
	}
	
	// Go to a specified section/entry/page, leaving a breadcrumb trail.
	// Perhaps some day I will write good code, but that day is not today.
	private void goToPage(int section, int entry, int page)
	{
		this.section = section;
		this.page = page;
		this.entry = entry;
		GuiHandbookSubmenu submenu = new GuiHandbookSubmenu(submenuNames[section], FactoryTech.random.nextInt(4), section, 0, entries.get(section), this);
		if (entry == -1)
		{
			submenu.changePage(page);
			mc.displayGuiScreen(submenu);
		}
		else
		{
			GuiHandbookPage bookPage = new GuiHandbookPage(submenu.getEntry(entry), submenu);
			bookPage.page = page;
			mc.displayGuiScreen(bookPage);
		}
	}
}