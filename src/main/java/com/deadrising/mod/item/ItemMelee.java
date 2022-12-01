	package com.deadrising.mod.item;

	import java.util.ArrayList;
	import java.util.List;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
	import net.minecraft.creativetab.CreativeTabs;
	import net.minecraft.item.ItemStack;
	import net.minecraft.item.ItemSword;
	import net.minecraft.world.World; 
public class ItemMelee extends ItemSword implements IHasModel
{
    

    public ItemMelee(ToolMaterial material, String name, int StackSize, CreativeTabs tab) 
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);
        setMaxStackSize(StackSize);
        
        
        
        ModItems.ITEMS.add(this);
    }


	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
		
	}



    
}

