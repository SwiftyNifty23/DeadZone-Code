package com.deadrising.mod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = "deadrising")
@ObjectHolder("deadrising")
public class ModSounds 
{
	public static SoundEvent LOADING_SOUND;
	
	 @SubscribeEvent
	  public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		 
		    registerSound(event.getRegistry(), "loading_sound");
		    LOADING_SOUND = (SoundEvent)SoundEvent.REGISTRY.getObject(new ResourceLocation("deadrising", "loading_sound"));
		 
		 
		 
	 }
	 
	  public static void registerSound(IForgeRegistry<SoundEvent> r, String name) {
		    ResourceLocation loc = new ResourceLocation("deadrising", name);
		    r.register((new SoundEvent(loc)).setRegistryName(loc));
		  }
	
}
