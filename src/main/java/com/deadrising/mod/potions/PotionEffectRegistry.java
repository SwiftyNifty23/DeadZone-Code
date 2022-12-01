package com.deadrising.mod.potions;

import com.deadrising.mod.deadrising;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionEffectRegistry extends PotionType
{
	public static final PotionType D = new PotionType("zombievirus", new PotionEffect[] {new PotionEffect(deadrising.Infection, 2400)}).setRegistryName("Infection");
	
	public static void registerPotionTypes() {
		ForgeRegistries.POTION_TYPES.register(D);
	}
}
