package com.deadrising.mod.client.tabs;


import com.deadrising.mod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMelee extends CreativeTabs 
{
	public TabMelee(String label) { super(label);
	}

	@Override
	public ItemStack getTabIconItem() { return new ItemStack(ModItems.BASEBALLBAT);

	}
}
