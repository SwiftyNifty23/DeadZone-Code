package com.deadrising.mod.client.tabs;

import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabBlocks extends CreativeTabs{

	public TabBlocks(String label) { super(label);
	
	}
	
	
	
	@Override
	public ItemStack getTabIconItem() { return new ItemStack(ModBlocks.Barbedwire);
	
	}

	
}
