package dalapo.factech.gui;

import dalapo.factech.gui.widget.WidgetToggleSwitch;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.SwitchTogglePacket;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.automation.TileEntityInventorySensor;
import net.minecraft.inventory.IInventory;

public class GuiInventorySensor extends GuiBasicInventory
{
	private TileEntityInventorySensor tile;
	public GuiInventorySensor(ContainerBasicInventory container, IInventory player, String texName, TileEntityBasicInventory te)
	{
		super(container, player, texName, te);
		addWidget(new WidgetToggleSwitch(this, 0, 136, 20, "Match Any", "Match All"));
		addWidget(new WidgetToggleSwitch(this, 1, 136, 32, "At Least", "Exactly"));
		addWidget(new WidgetToggleSwitch(this, 2, 136, 44, "Ignore Damage", "Use Damage"));
		tile = (TileEntityInventorySensor)te;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
}