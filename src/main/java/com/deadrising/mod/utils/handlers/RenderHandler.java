package com.deadrising.mod.utils.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.deadrising.mod.entity.EntityCrawler;
import com.deadrising.mod.entity.EntityDRRunner;
import com.deadrising.mod.entity.EntityDRZombie;
import com.deadrising.mod.entity.render.RenderCrawler;
import com.deadrising.mod.entity.render.RenderDRRunner;
import com.deadrising.mod.entity.render.RenderDRZombie;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler 
{
	

	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityDRZombie.class, new IRenderFactory<EntityDRZombie>()
		{
			@Override
			public Render<? super EntityDRZombie> createRenderFor(RenderManager manager) 
			{
				return new RenderDRZombie(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCrawler.class, new IRenderFactory<EntityCrawler>()
		{
			@Override
			public Render<? super EntityCrawler> createRenderFor(RenderManager manager) 
			{
				return new RenderCrawler(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDRRunner.class, new IRenderFactory<EntityDRRunner>()
		{
			@Override
			public Render<? super EntityDRRunner> createRenderFor(RenderManager manager) 
			{
				return new RenderDRRunner(manager);
			}
		});
		
	}
	
	
		
}
    

