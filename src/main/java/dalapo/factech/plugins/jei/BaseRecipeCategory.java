package dalapo.factech.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.plugins.jei.saw.SawRecipeWrapper;
import dalapo.factech.reference.NameList;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeCategory<T extends BaseRecipeWrapper> implements IRecipeCategory<T> {

	private final String UID;
	protected IDrawableStatic background;
	
	public BaseRecipeCategory(IGuiHelper helper, String uid, String guiTex)
	{
		this(helper, uid, guiTex, 8, 4, 120, 60);
	}
	
	public BaseRecipeCategory(IGuiHelper helper, String uid, String guiTex, int x, int y, int w, int h)
	{
		UID = uid;
		background = helper.createDrawable(new ResourceLocation(FacGuiHelper.formatTexName(guiTex)), x, y, w, h);
	}
	
	@Override
	public String getUid() {
		// TODO Auto-generated method stub
		return UID;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return I18n.translateToLocal("factorytech:jei." + UID);
	}
	@Override
	public String getModName() {
		// TODO Auto-generated method stub
		return NameList.MODNAME;
	}
	
	@Override
	public IDrawable getBackground() {
		// TODO Auto-generated method stub
		return background;
	}
	
	@Override
	@Nullable
	public IDrawable getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void drawExtras(Minecraft minecraft) {
		
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}
}