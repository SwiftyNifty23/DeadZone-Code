package com.deadrising.mod.common;

import java.util.HashMap;
import java.util.UUID;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.entity.throwables.EntityFragGrenade;
import com.deadrising.mod.entity.throwables.EntityMolotov;
import com.deadrising.mod.entity.throwables.EntitySmokeGrenade;
import com.deadrising.mod.item.ItemExplodeable;
import com.deadrising.mod.entity.throwables.EntityThrowableExplodeable;
import com.deadrising.mod.network.PacketHandler;
import com.deadrising.mod.utils.handlers.ConfigHandler;

import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CommonEvents {

	// public static boolean timerStarted = false;
	private static int ticks = 0;
	 public static int entityId = 0;
	
	 
	
	  @SubscribeEvent
	  public void onRegisterEntitiesEvent(RegistryEvent.Register<EntityEntry> event) {
	    IForgeRegistry<EntityEntry> registry = event.getRegistry();
	    EntityEntry[] entries = { registerEntity("grenade", (Class)EntityFragGrenade.class, 64, 20, true), registerEntity("smoke", (Class)EntitySmokeGrenade.class, 256, 20, true), registerEntity("molotov", (Class)EntityMolotov.class, 64, 20, true) };
	    event.getRegistry().registerAll((EntityEntry[])entries);
	    deadrising.logger.info("Registered entities");
	  }
	  
	  private static EntityEntry registerEntity(String name, Class<? extends Entity> cl, int trackRange, int frequency, boolean velocityUpdates) {
		    return createEntityBuilder(name).entity(cl).tracker(trackRange, frequency, velocityUpdates).build();
		  }
		  
		  private static EntityEntry registerEntity(String name, Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int eggPrimary, int eggSecondary) {
		    return createEntityBuilder(name).entity(entityClass).tracker(trackingRange, updateFrequency, sendVelocityUpdates).egg(eggPrimary, eggSecondary).build();
		  }
		  
		  private static <E extends Entity> EntityEntryBuilder<E> createEntityBuilder(String name) {
		    EntityEntryBuilder<E> builder = EntityEntryBuilder.create();
		    ResourceLocation regName = new ResourceLocation("deadrising", name);
		    return builder.id(regName, ID()).name(regName.toString());
		  }
		  
		  private static int ID() {
		    entityId++;
		    return entityId;
		  }
	
	private static final HashMap<UUID, String> STATUS_MAP = new HashMap<>();

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
		}
	}
	
	
	
	@SubscribeEvent
	public void onPlayerDeath(PlayerRespawnEvent e)
	{
		ConfigHandler.ClientSide.Deaths++;
	}
	
	
    @SubscribeEvent
    public void onItemDrop(ItemTossEvent e) {
        EntityPlayer player = e.getPlayer();
        EntityItem itemEntity = e.getEntityItem();
        if(itemEntity.getItem().getItem() instanceof ItemExplodeable) {
            ItemStack stack = itemEntity.getItem();
            ItemExplodeable explodeable = (ItemExplodeable) stack.getItem();
            if(explodeable.isCooking(stack)) {
                e.setCanceled(true);
                explodeable.getExplodeableItemAction().onRemoveFromInventory(stack, player.world, player, explodeable.getMaxFuse() - explodeable.getFuseTime(stack), EntityThrowableExplodeable.EnumEntityThrowState.SHORT);
            }
        }
    }
	/*@SubscribeEvent
	public void onKillZombie(LivingDeathEvent e)
	{
		if(e.getEntity() instanceof EntityZombie)
		{
			if(e.getSource().getTrueSource() == Minecraft.getMinecraft().player)
			{
				ConfigHandler.ClientSide.ZombieKills++;
			}
		}
		else
		{
			if(e.getEntity() instanceof EntityPlayer)
			{
				if(e.getSource().getTrueSource() == Minecraft.getMinecraft().player)
				{
					ConfigHandler.ClientSide.PlayerKills++;
				}
			}
		}
	}*/

    }
