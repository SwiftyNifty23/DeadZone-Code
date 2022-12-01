package com.deadrising.mod.potions;

import com.deadrising.mod.Reference;
import com.deadrising.mod.api.Base;
import com.deadrising.mod.entity.EntityDRZombie;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ZombieVirus extends Potion
{

	public ZombieVirus() {
		super(true, 14155775);
		setPotionName("effect.zombievirus");
		setIconIndex(7, 0);
		setRegistryName(new ResourceLocation(Reference.MOD_ID + ":" + "zombievirus"));
	}
	
	@Override
	public boolean isBadEffect() {
		return true;
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
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/potion_effect.png"));
		return true;
	} 
	
	
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

        entityLivingBaseIn.attackEntityFrom(DamageSource.WITHER, 2.5F);
	}
	
}
