package dalapo.factech.gui.widget;

import dalapo.factech.gui.GuiFacInventory;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.SwitchTogglePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class WidgetToggleSwitch extends FacTechWidget
{
	private boolean state;
	private int id;
	private String onMessage;
	private String offMessage;
	
	public WidgetToggleSwitch(GuiFacInventory parent, int id, int x, int y, String off, String on)
	{
		super(parent, x, y, 18, 10);
		this.id = id;
		onMessage = on;
		offMessage = off;
	}

	public void init()
	{
		setState(parent.getTileEntity().getField(id) > 0);
	}
	
	public void handle(int mouseButton, boolean shift)
	{
		state = !state;
		PacketHandler.sendToServer(new SwitchTogglePacket(parent.getTileEntity().getPos(), (byte)id));
	}
	
	public int getID()
	{
		return id;
	}
	
	@Override
	public void draw()
	{
		GlStateManager.pushMatrix();
		FacGuiHelper.bindTex("widget_switch");
		GlStateManager.color(1F, 1F, 1F, 1F);
		this.zLevel = getParent().getZLevel() + 1;
		this.drawTexturedModalRect(x + getParent().getGuiLeft(), y + getParent().getGuiTop(), 0, state ? 10 : 0, width, height);
		GlStateManager.popMatrix();
	}
	
	public void setState(boolean val)
	{
		state = val;
	}
	
	@Override
	public String getTooltip()
	{
		return state ? onMessage : offMessage;
	}
}