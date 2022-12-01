package com.deadrising.mod.client.tabs;


import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabProps extends CreativeTabs 
{
	public TabProps(String label) { super(label);
	}

	@Override
	public ItemStack getTabIconItem() { return new ItemStack(ModBlocks.body_bag_black);

	}
}

