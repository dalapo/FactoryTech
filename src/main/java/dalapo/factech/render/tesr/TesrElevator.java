package dalapo.factech.render.tesr;

import java.util.Deque;

import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.tileentity.automation.TileEntityElevator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class TesrElevator extends TileEntitySpecialRenderer<TileEntityElevator>
{
	static final TESRELEV auth = new TESRELEV();
	
	public void render(TileEntityElevator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        Deque<ItemStack> stacks = te.getStacks(auth);
        ItemStack andOneMore = te.getLegacy();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        int i = 0;
        for (ItemStack stack : stacks)
        {
        	if (!stack.isEmpty())
        	{
        		GlStateManager.pushMatrix();
        		GlStateManager.translate(1, 1 - (i - partialTicks) / 20, 0.5);
        		GlStateManager.scale(0.25, 0.25, 0.25);
        		FacTesrHelper.renderStack(stack);
        		GlStateManager.popMatrix();
        	}
        	i++;
        }
        GlStateManager.pushMatrix();
		GlStateManager.translate(1, 1 + (partialTicks / 20), 0.5);
		GlStateManager.scale(0.25, 0.25, 0.25);
		FacTesrHelper.renderStack(andOneMore);
		GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
	
	public static final class TESRELEV { private TESRELEV() {}}
}