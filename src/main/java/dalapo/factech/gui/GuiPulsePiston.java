package dalapo.factech.gui;

import dalapo.factech.gui.widget.WidgetToggleSwitch;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.automation.TileEntityItemPusher;
import net.minecraft.inventory.IInventory;

public class GuiPulsePiston extends GuiBasicInventory
{
	private TileEntityItemPusher tile;
	public GuiPulsePiston(ContainerBasicInventory container, IInventory player, String texName, TileEntityBasicInventory te)
	{
		super(container, player, texName, te);
		addWidget(new WidgetToggleSwitch(this, 0, 136, 44, "Use Durability", "Ignore Durability"));
		tile = (TileEntityItemPusher)te;
	}
}