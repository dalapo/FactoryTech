package dalapo.factech.gui;

import dalapo.factech.tileentity.specialized.TileEntityAutoCrafter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class GuiAutoCrafter extends GuiBasicInventory {

	public GuiAutoCrafter(ContainerAutoCrafter inventorySlotsIn, InventoryPlayer inv, String texture, TileEntityAutoCrafter te) {
		super(inventorySlotsIn, inv, texture, te);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
}