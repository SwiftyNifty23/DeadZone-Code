package com.deadrising.mod.common.events;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.potions.PotionEffectRegistry;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFallEvent {
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
              if (event.getDistance() >= 10.0F) {
                  event.getEntityLiving().addPotionEffect(new PotionEffect(deadrising.Infection, 9999, 0, true, false));
              }
              event.setDamageMultiplier(event.getDistance() - 2.0F);
        }
	}
	
}