package com.deadrising.mod.item;

import java.util.List;

import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FoodBase extends ItemFood implements IHasModel
{
    private String[] description;

	public FoodBase(String name, int amount, float saturation, boolean isAnimalFood, int StackSize, CreativeTabs tab)
	{
		super(amount, saturation, isAnimalFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
	
		
		ModItems.ITEMS.add(this);

	}

    public FoodBase addAditionalDescription(String... description) {
        this.description = description;
        return this;
    }

	@Override
	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
	}
	}

