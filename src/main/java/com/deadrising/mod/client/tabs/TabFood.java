package com.deadrising.mod.client.tabs;

import com.deadrising.mod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabFood extends CreativeTabs 
{
	public TabFood(String label) { super(label);
	}

	@Override
	public ItemStack getTabIconItem() { return new ItemStack(ModItems.cereals);

	}
}
