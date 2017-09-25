package dalapo.factech.gui;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiBasicInventory extends GuiFacInventory {

	int invRows;
	int invCols;
	String texName;
	IInventory playerInv;
	private TileEntityBasicInventory te;
	
	public GuiBasicInventory(ContainerBasicInventory container, IInventory player, String texName, TileEntityBasicInventory te) {
		super(container);
		invRows = container.getRows();
		invCols = container.getCols();
		// TODO: Stop hardcoding this
		this.xSize = 176;
		this.ySize = 166;
		this.te = te;
		this.texName = texName;
		this.playerInv = player;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, x, y);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(FacGuiHelper.formatTexName(texName)));
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String str = this.te.getDisplayName().getUnformattedText();
		fontRenderer.drawString(str, 88 - fontRenderer.getStringWidth(str) / 2, 6, 0x404040);
	}
}