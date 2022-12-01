package com.deadrising.mod.events;

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
	public void onFall(LivingFallEvent e) {
		if(e.getEntity() instanceof EntityPlayer) {
			if(!deadrising.isLegsBroken) {
				float d = e.getDistance();
				final EntityPlayer p = (EntityPlayer) e.getEntityLiving();
				Random r = new Random();
				
				if(d > 5.5) {
					int rg = r.nextInt(6) + 1;
					if(rg == 3) {
						deadrising.isLegsBroken = true;
						Timer t = new Timer();
						t.scheduleAtFixedRate(new TimerTask() {
							public void run() {
								if(deadrising.isLegsBroken) {
									p.addPotionEffect(new PotionEffect(deadrising.Infection, Integer.MAX_VALUE));
								} else {
									this.cancel();
								}
							}
						}, 1, 2000);
					}
				}
			}
		}	
	}
}