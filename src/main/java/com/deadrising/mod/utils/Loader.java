package com.deadrising.mod.utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.deadrising.mod.deadrising;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;


public class Loader {

	private static List<Integer> textures = new ArrayList<Integer>();
	private static Map<String, ByteBuffer> buffers = new HashMap<String, ByteBuffer>();


	public static void cleanUp() {
		for (Integer texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}


	public static ByteBuffer loadToByteBuffer(BufferedImage image) throws NullPointerException {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = new int[width * height];
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = pixels[x + y * width];
				buffer.put((byte) ((color >> 16) & 0xff));
				buffer.put((byte) ((color >> 8) & 0xff));
				buffer.put((byte) (color & 0xff));
				buffer.put((byte) ((color >> 24) & 0xff));
			}
		}
		buffer.flip();
		return buffer;
	}


	public static int loadTexture(ResourceLocation location) {
		try {
			int textureID = GlStateManager.generateTexture();
			textures.add(textureID);
			return Loader.loadTexture(textureID, ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream()), location.toString());
		} catch (Exception e) {
			deadrising.logger().warn("Could not find image at \'" + location + "\'");
			return 0;
		}
	}


	public static int loadTexture(int textureID, BufferedImage image, String id) throws NullPointerException {
		int width = image.getWidth();
		int height = image.getHeight();

		ByteBuffer pixels = buffers.get(id);
		if (pixels == null) {
			pixels = loadToByteBuffer(image);
			buffers.put(id, pixels);
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

		return textureID;
	}


	public static void clearTextureBuffers() {
		buffers.clear();
	}
}