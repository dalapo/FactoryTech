package dalapo.factech.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.plugins.jei.categories.MetalCutterRecipeCategory;
import dalapo.factech.reference.NameList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeCategory<T extends BaseRecipeWrapper> implements IRecipeCategory<T> {

	private final String UID;
	protected IDrawableAnimated progressBar;
	protected IDrawableStatic background;
	protected IDrawable icon;
	protected boolean worksWithBad;
	
	public BaseRecipeCategory(IGuiHelper helper, String uid, String guiTex)
	{
		this(helper, uid, guiTex, 10, 7, 120, 60);
	}
	
	public BaseRecipeCategory(IGuiHelper helper, String uid, String guiTex, int x, int y, int w, int h)
	{
		UID = uid;

//		icon = helper.createDrawable(new ResourceLocation("textures/items/gear"), 0, 0, 16, 16);
		background = helper.createDrawable(new ResourceLocation(FacGuiHelper.formatTexName(guiTex)), x, y, w, h);
		addProgressBar(helper);
	}
	
	protected abstract void addProgressBar(IGuiHelper helper);
	
	@Override
	public String getUid() {
		// TODO Auto-generated method stub
		return UID;
	}
	@Override
	public String getTitle()
	{
		return I18n.format("factorytech:jei." + UID);
	}
	@Override
	public String getModName() {
		return NameList.MODNAME;
	}
	
	@Override
	public IDrawable getBackground() {
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
		if (progressBar != null) progressBar.draw(minecraft);
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<String>();
	}
}