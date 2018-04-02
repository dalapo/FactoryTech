package dalapo.factech.auxiliary;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class FacTechRecipeFactory implements IRecipeFactory
{

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
		
		ShapedPrimer primer = new ShapedPrimer();
		primer.width = recipe.getWidth();
		primer.height = recipe.getHeight();
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = recipe.getIngredients();
		Logger.info(primer.input);
		
		return new FacTechRecipe(new ResourceLocation(NameList.MODID, "standard"), recipe.getRecipeOutput(), primer);
	}
	
	public static class FacTechRecipe extends ShapedOreRecipe
	{
		public FacTechRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer)
		{
			super(group, result, primer);
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			// TODO Auto-generated method stub
			return super.matches(inv, worldIn);
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			// TODO Auto-generated method stub
			return super.getCraftingResult(inv);
		}

		@Override
		public boolean canFit(int width, int height) {
			// TODO Auto-generated method stub
			return super.canFit(width, height);
		}

		@Override
		public ItemStack getRecipeOutput() {
			// TODO Auto-generated method stub
			return super.getRecipeOutput();
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
			// TODO Auto-generated method stub
			return super.getRemainingItems(inv);
		}

		@Override
		public NonNullList<Ingredient> getIngredients() {
			// TODO Auto-generated method stub
			return super.getIngredients();
		}

		@Override
		public boolean isHidden() {
			// TODO Auto-generated method stub
			return super.isHidden();
		}

		@Override
		public String getGroup() {
			// TODO Auto-generated method stub
			return super.getGroup();
		}
		
	}
}