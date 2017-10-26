package dalapo.factech.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityStabilizer;

public class GuiStabilizer extends GuiBasicMachine {

	TileEntityStabilizer stabilizer;
	public GuiStabilizer(ContainerBasicMachine inventorySlotsIn,
			IInventory player, TileEntityMachine te) {
		super(inventorySlotsIn, player, "stabilizer_gui", te);
		stabilizer = (TileEntityStabilizer)te;
	}
	
	@Override
	public void drawProgressBar() {}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		List<Pair<Integer, Integer>> cheatycheaty = new ArrayList<>();
		cheatycheaty.add(new Pair(80, 10));
		cheatycheaty.add(new Pair(80, 60));
		cheatycheaty.add(new Pair(56, 35));
		cheatycheaty.add(new Pair(104, 35));
		
		for (int i=0; i<4; i++)
		{
			if (stabilizer.getStrength(i) == -1)
			{
				this.drawTexturedModalRect(guiLeft + cheatycheaty.get(i).a-1, guiTop + cheatycheaty.get(i).b-1, 176, 0, 18, 18);
			}
			else
			{
				int colour = 0;
				short str = stabilizer.getStrength(i);
				if (stabilizer.isCooling(i)) colour = 0xFF202020;
				else if (str <= 512) colour = 0xFF8080FF;
				else if (str < 768) colour = 0xFF40FF40;
				else if (str < 850) colour = 0xFFBBBB00;
				else if (str < 950) colour = 0xFFEC7100;
				else colour = 0xFFFF0000;
				this.drawRect(guiLeft + cheatycheaty.get(i).a, guiTop + cheatycheaty.get(i).b, guiLeft+cheatycheaty.get(i).a+16, guiTop+cheatycheaty.get(i).b+16, colour);
			}
		}
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		// TODO: Field strength numbers have different background colours depending on
		// how close to overcharging they are
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		// The lengths I go to to avoid having to type...
		List<Pair<Integer, Integer>> cheatycheaty = new ArrayList<>();
		cheatycheaty.add(new Pair(88, 14));
		cheatycheaty.add(new Pair(88, 64));
		cheatycheaty.add(new Pair(64, 39));
		cheatycheaty.add(new Pair(112, 39));
		for (int i=0; i<4; i++)
		{
			String str = Integer.toString(stabilizer.getStrength(i));
			if (!str.equals("-1"))
			{
				drawCenteredString(fontRenderer, str, cheatycheaty.get(i).a, cheatycheaty.get(i).b, 0xFFFFFF);
			}
		}
		fontRenderer.drawString(String.format("Stability: %s", stabilizer.getStability()), 80, 72, 0x404040);
	}
}
