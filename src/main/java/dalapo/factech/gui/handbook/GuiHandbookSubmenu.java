package dalapo.factech.gui.handbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import dalapo.factech.FactoryTech;
import dalapo.factech.gui.handbook.HandbookEntry;
import dalapo.factech.gui.widget.WidgetCustomButton;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiHandbookSubmenu extends GuiHandbookBase
{
	private String name;
	private List<HandbookEntry> entries = new ArrayList<>();
	private List<String> allButtons = new ArrayList<>();
	private GuiButton prev;
	private GuiButton back;
	private GuiButton next;
	private int background;
	private int labelSet;
	private int page;
	private int lastPage;
	private static final int xSize = 256;
	private static final int ySize = 160;
	private GuiHandbook parent;
	private boolean canClickButtons = true;
	
	public GuiHandbookSubmenu(String name, int bg, int set, int page, List<HandbookEntry> entries, GuiHandbook parent)
	{
		super();
		this.entries.addAll(entries);
		this.background = bg;
		this.labelSet = set;
		this.parent = parent;
		this.page = page;
		this.name = name;

		back = new GuiButton(0, guiLeft + 120, guiTop + 160, 20, 20, "X");
		prev = new GuiButton(1, guiLeft + 100, guiTop + 160, 20, 20, "<");
		next = new GuiButton(2, guiLeft + 140, guiTop + 160, 20, 20, ">");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		fontRenderer.drawString(name, guiLeft + (128 - fontRenderer.getStringWidth(name) / 2), guiTop + 8, 0xFFFFFFFF);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		allButtons.clear();
		parent.setEntry(-1);
		int x = 0;
		int y = 0;
		int i = 0;
		
		back.y = guiTop + 120;
		prev.y = guiTop + 120;
		next.y = guiTop + 120;
		buttonList.add(back);
		buttonList.add(prev);
		buttonList.add(next);
		for (HandbookEntry entry : GuiHandbook.entries.get(labelSet))
		{
			allButtons.add(entry.title);
		}
		lastPage = (GuiHandbook.entries.get(labelSet).size() / 12);
		changePage(page);
	}
	
	public void changePage(int newPage)
	{
		for (int i=buttonList.size()-1; i>2; i--)
		{
			buttonList.remove(i);
		}
		background = FactoryTech.random.nextInt(4);
		
		if (prev != null) prev.enabled = newPage != 0;
		if (next != null) next.enabled = newPage != lastPage;
		for (int i=0; i<12; i++)
		{
			int index = (newPage * 12) + i;
//			String debug = Integer.toString(index) + ", " + allButtons.size();
			if (index < allButtons.size())
			{
//				debug += "; adding button";
				WidgetCustomButton wcb = new WidgetCustomButton(this, i+3, i<6?guiLeft+6:guiLeft+130, guiTop+24+((i%6)*14), 120, 12, allButtons.get(index), "button_handbook");
				buttonList.add(wcb);
			}
//			else break;
//			Logger.info(debug);
		}
		page = newPage;
		parent.setPage(newPage);
		canClickButtons = true;
	}
	
	@Override
	public void onGuiClosed()
	{
		parent.onGuiClosed();
	}
	
	@Override
	public void actionPerformed(GuiButton b)
	{
		if (canClickButtons)
		{
			canClickButtons = false;
			switch (b.id)
			{
			case 0:
				parent.setSection(-1);
				mc.displayGuiScreen(parent);
				break;
			case 1:
				changePage(page - 1);
				break;
			case 2:
				changePage(page + 1);
				break;
				default:
					parent.setEntry(page*12 + b.id - 3);
					HandbookEntry entry = getEntry(page*12 + b.id - 3);
					mc.displayGuiScreen(new GuiHandbookPage(entry, this));
			}
		}
	}
	
	protected GuiHandbook getParent()
	{
		return parent;
	}
	
	HandbookEntry getEntry(int id) // Default access
	{
		return GuiHandbook.entries.get(labelSet).get(id);
	}
}