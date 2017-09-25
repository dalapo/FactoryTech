package dalapo.factech.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
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
	
	// TODO: Display 5 strings to show core stability and magnet strengths.

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
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
			
			drawCenteredString(fontRenderer, str, cheatycheaty.get(i).a, cheatycheaty.get(i).b, 0xFFFFFF);
		}
		fontRenderer.drawString(String.format("Stability: %s", stabilizer.getStability()), 120, 72, 0x404040);
	}
}
