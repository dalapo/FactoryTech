package dalapo.factech.gui.widget;

import dalapo.factech.gui.GuiFacInventory;
import net.minecraft.inventory.Container;

public abstract class FacTechWidget {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int parX;
	protected int parY;
	private GuiFacInventory parent;
	
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
	
	protected GuiFacInventory getParent()
	{
		return parent;
	}
	
	public abstract void draw();
}