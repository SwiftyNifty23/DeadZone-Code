package com.deadrising.mod.item;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.item.Item;

public class ItemMedical extends Item implements IHasModel
{
	public ItemMedical(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(deadrising.TabMedical);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
		
	}
	
}
