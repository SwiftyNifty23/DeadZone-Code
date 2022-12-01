package com.deadrising.mod.client.tabs;

import com.deadrising.mod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabItems extends CreativeTabs 
{
	public TabItems(String label) { super(label);
	}

	@Override
	public ItemStack getTabIconItem() { return new ItemStack(ModItems.MONEY);

	}
}

