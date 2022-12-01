package com.deadrising.mod.potions;

import java.util.UUID;

import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;
import com.deadrising.mod.api.Base;
import com.deadrising.mod.entity.EntityDRZombie;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class BrokenLeg extends Potion
{
	
	
	public BrokenLeg() {
		super(true, 14155775);
		setPotionName("effect.brokenleg");
		setIconIndex(7, 0);
		setRegistryName(new ResourceLocation(Reference.MOD_ID + ":" + "brokenleg"));
	}


	@Override
	public boolean isReady(int duration, int amplifier) {
		int i = Base.getTicksFromTime(5);

        if (i > 0)
        {
            return duration % i == 0;
        }
        else
        {
            return true;
        }
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

        entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, Integer.MAX_VALUE, 2, true, false));
        entityLivingBaseIn.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50, 2, true, false));
	}



	
	
	
	
	
}
