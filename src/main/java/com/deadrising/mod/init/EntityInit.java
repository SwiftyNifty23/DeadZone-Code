package com.deadrising.mod.init;

import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;
import com.deadrising.mod.api.colors.Colors;
import com.deadrising.mod.api.colors.EggColor;
import com.deadrising.mod.entity.EntityCrawler;
import com.deadrising.mod.entity.EntityDRRunner;
import com.deadrising.mod.entity.EntityDRZombie;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit 
{
	public static void registerEntities()
	{
		registerEntity("zombie", EntityDRZombie.class, Reference.ZombieNormalID, 50, Colors.getEggColors(94, 0, 0, 51, 0, 0));
		registerEntity("crawler", EntityCrawler.class, Reference.CrawlerID, 51, Colors.getEggColors(94, 0, 0, 51, 0, 0));
		registerEntity("runner", EntityDRRunner.class, Reference.ZombieRunner, 52, Colors.getEggColors(94, 0, 0, 51, 0, 0));
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, EggColor color)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, deadrising.instance, range, 1, true, color.value1, color.value2);
	}
}
