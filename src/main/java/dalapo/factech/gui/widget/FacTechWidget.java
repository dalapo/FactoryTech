package dalapo.factech.gui.widget;

import dalapo.factech.gui.GuiFacInventory;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;

public abstract class FacTechWidget extends Gui
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int parX;
	protected int parY;
	protected GuiFacInventory parent;
	
	public FacTechWidget(GuiFacInventory parent, int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.parent = parent;
		
		parX = parent.getGuiLeft();
		parY = parent.getGuiTop();
	}
	
	public abstract void init();
	public abstract void handle(int mouseButton, boolean shift);
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	protected GuiFacInventory getParent()
	{
		return parent;
	}
	
	public boolean isPointInBounds(int x, int y)
	{
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
	}
	public abstract String getTooltip();
	public abstract void draw();
}