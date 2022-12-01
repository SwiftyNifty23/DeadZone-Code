package com.deadrising.mod.utils.events;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.entity.EntityDRZombie;
import com.deadrising.mod.network.PacketHandler;
import com.deadrising.mod.utils.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class MainEvents 
{
	@SubscribeEvent
	public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent e)
	{
        if(!e.player.getEntityWorld().isRemote)
        {
    		ConfigHandler.ClientSide.HELMET = e.player.inventory.armorInventory.get(3);
            ConfigHandler.ClientSide.CHESTPLATE = e.player.inventory.armorInventory.get(2);
            ConfigHandler.ClientSide.LEGGINGS = e.player.inventory.armorInventory.get(1);
            ConfigHandler.ClientSide.BOOTS = e.player.inventory.armorInventory.get(0);
            ConfigHandler.ClientSide.lastMainItem = e.player.getHeldItemMainhand();
        }
	}
	
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
        	final EntityPlayer p = (EntityPlayer) event.getEntityLiving();
              if (event.getDistance() >= 4.0F) {
            	  p.sendMessage((ITextComponent) new TextComponentString(TextFormatting.RED + "You have broken your leg"));
                  p.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, Integer.MAX_VALUE, 2, true, false));
                  p.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50, 2, true, false));
              

              }else if(p.isCreative()) {
            	  p.removePotionEffect(MobEffects.SLOWNESS);
              
              }
        }
        
        

	}
	public boolean attackEntityAsMob(Entity entityIn) {
		Random rand = new Random();
		int infectChance = rand.nextInt(100);
		
		if(infectChance > 90)
		{
			if(entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(deadrising.Infection, Integer.MAX_VALUE));
			}
		}
		return true;
		
	}
	
}
