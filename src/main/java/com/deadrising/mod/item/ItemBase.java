package com.deadrising.mod.item;

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

public class ItemBase extends Item implements IHasModel
{
	
    private String[] description;
    
	public ItemBase(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(deadrising.TabItems);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
	
    public ItemBase addAditionalDescription(String... description) {
        this.description = description;
        return this;
    }
	
	@Override
	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
		
	}
	
}
