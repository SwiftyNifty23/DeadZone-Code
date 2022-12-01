package com.deadrising.mod.proxy;


import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;
import com.deadrising.mod.client.GuiEvents;
import com.deadrising.mod.client.Manager_Environment;
import com.deadrising.mod.client.gui.GuiAirdrop;
import com.deadrising.mod.client.particle.ParticleRenderer;
import com.deadrising.mod.client.render.throwable.RenderThrowable;
import com.deadrising.mod.common.EnumParticles;
import com.deadrising.mod.entity.EntityLootableBody;
import com.deadrising.mod.entity.throwables.EntityFragGrenade;
import com.deadrising.mod.entity.throwables.EntityMolotov;
import com.deadrising.mod.entity.throwables.EntitySmokeGrenade;
import com.deadrising.mod.graphics.CorpseRenderer;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.handlers.RenderHandler;
import com.deadrising.mod.utils.handlers.SoundHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		/* Register Gui Events */
		registerEntityRenderers();
		MinecraftForge.EVENT_BUS.register(new GuiEvents());
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	    Manager_Environment.setIcon();
	    
		RenderingRegistry.registerEntityRenderingHandler(EntityLootableBody.class, new IRenderFactory<EntityLootableBody>() {
			@Override
			public Render<? super EntityLootableBody> createRenderFor(RenderManager rm) {
				return (new CorpseRenderer(rm));
			}
		});

	}
	
	  private static void registerEntityRenderers() {
		    //RenderingRegistry.registerEntityRenderingHandler(EntityFragGrenade.class, manager -> new RenderThrowable(manager, ModItems.GRENADE));
		    //RenderingRegistry.registerEntityRenderingHandler(EntitySmokeGrenade.class, manager -> new RenderThrowable(manager, ModItems.SMOKE));
		    //RenderingRegistry.registerEntityRenderingHandler(EntityMolotov.class, manager -> new RenderThrowable(manager, ModItems.MOLOTOV));
		   
		  }



	public void registerItemRender(Item item, int meta, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
    public void registerVariantRenderer(Item item, int meta, String filename, String id) 
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, filename), id));
    }

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);


		// Particles, etc...
		MinecraftForge.EVENT_BUS.register(ParticleRenderer.getInstance());
       
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public void OpenAirdropGUI(EntityPlayer player) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiAirdrop(null, null, 9));
	}
	
	

	

}