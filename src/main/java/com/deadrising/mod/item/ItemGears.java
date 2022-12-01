package com.deadrising.mod.item;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemGears extends Item implements IHasModel
{
	
    private String[] description;
    
	public ItemGears(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(deadrising.TabItems);
		setMaxStackSize(16);
		
		ModItems.ITEMS.add(this);
	}
	
	
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("");
        tooltip.add("Use to buy items and trade.");
    }
	
	@Override
	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
		
	}
	
}
