package com.deadrising.mod.utils.handlers;

import com.deadrising.mod.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {
	
	public static SoundEvent ZOMBIEIDLE1, ZOMBIEIDLE2, ZOMBIEIDLE3, ZOMBIEIDLE4, ZOMBIEHURT1, ZOMBIEHURT2, ZOMBIEHURT3;
	
	public static void registerSounds()
	{
		
		ZOMBIEIDLE1 = registerSound("entity.zombie.idle1");
		ZOMBIEIDLE2 = registerSound("entity.zombie.idle2");
		ZOMBIEIDLE3 = registerSound("entity.zombie.idle3");
		ZOMBIEIDLE4 = registerSound("entity.zombie.idle4");
		
		ZOMBIEHURT1 = registerSound("entity.zombie.hurt1");
		ZOMBIEHURT2 = registerSound("entity.zombie.hurt2");
		ZOMBIEHURT3 = registerSound("entity.zombie.hurt3");
	}
	
	public static SoundEvent registerSound(String name) {
			
			ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
			SoundEvent event = new SoundEvent(location);
			event.setRegistryName(name);
			ForgeRegistries.SOUND_EVENTS.register(event);
			return event;
		}
}
