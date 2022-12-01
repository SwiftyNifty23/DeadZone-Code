package com.deadrising.mod.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class TextureUtils {

	private static Minecraft mc = Minecraft.getMinecraft();

	private static Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
	
	public static ResourceLocation createBufferedImageTexture(BufferedImage image) {
		return mc.getTextureManager().getDynamicTextureLocation(" ", new DynamicTexture(image));
	}

	public static ResourceLocation createBufferedImageTexture(DynamicTexture texture) {
		return mc.getTextureManager().getDynamicTextureLocation(" ", texture);
	}
	
	public static void deleteTexture(ResourceLocation texture) {
		mc.getTextureManager().deleteTexture(texture);
	}


	public static void bindTexture(ResourceLocation texture) {
		mc.getTextureManager().bindTexture(texture);
	}


	public static void bindTexture(String domain, String path) {
		String locationString = domain + ":" + path;
		if (textures.containsKey(locationString)) {
			mc.getTextureManager().bindTexture(textures.get(locationString));
		} else {
			ResourceLocation location = new ResourceLocation(locationString);
			textures.put(locationString, location);
			mc.getTextureManager().bindTexture(location);
		}
	}


	public static void bindTexture(String path) {
		String locationString = "minecraft:" + path;
		if (textures.containsKey(locationString)) {
			mc.getTextureManager().bindTexture(textures.get(locationString));
		} else {
			ResourceLocation location = new ResourceLocation(locationString);
			textures.put(locationString, location);
			mc.getTextureManager().bindTexture(location);
		}
	}


	public static TextureAtlasSprite getMissingSprite() {
		return mc.getTextureMapBlocks().getMissingSprite();
	}
}