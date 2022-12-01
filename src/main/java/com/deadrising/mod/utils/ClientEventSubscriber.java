package com.deadrising.mod.utils;
import com.deadrising.mod.Reference;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = CLIENT)
public final class ClientEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent
	public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {

		registerTileEntitySpecialRenderers();
		LOGGER.debug("Registered tile entity special renderers");

		registerEntityRenderers();
		LOGGER.debug("Registered entity renderers");

		for (final Item item : ForgeRegistries.ITEMS.getValues()) {
			if (item.getRegistryName().getResourceDomain().equals(Reference.MOD_ID)) {
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
			}
		}

		LOGGER.debug("Registered models");

	}

	private static void registerTileEntitySpecialRenderers() {

//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExampleTileEntity.class, new RenderExampleTileEntity());

	}

	/**
	 * Helper method to register all Entity Renderers in
	 */
	private static void registerEntityRenderers() {

//		RenderingRegistry.registerEntityRenderingHandler(Entity___.class, Entity___Renderer::new);

	}

}