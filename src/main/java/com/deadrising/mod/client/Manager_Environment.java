package com.deadrising.mod.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.text.TextFormatting;

import org.lwjgl.opengl.Display;

public class Manager_Environment {
  public static void setIcon() {
    try {
      loadIcon("/assets/deadzone/textures/gui/icon_32x32.png");
      loadIcon("/assets/deadzone/textures/gui/icon_16x16.png");
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private static void loadIcon(String path) throws IOException {
    BufferedImage image = TextureUtil.readBufferedImage(Manager_Environment.class.getResourceAsStream(path));
    ByteBuffer[] buffers = null;
    String OS = System.getProperty("os.name").toUpperCase();
    if (OS.contains("MAC")) {
      buffers = new ByteBuffer[] { loadInstance(image, 128) };
    } else if (OS.contains("WIN")) {
      buffers = new ByteBuffer[] { loadInstance(image, 16), loadInstance(image, 32) };
    } else {
      buffers = new ByteBuffer[] { loadInstance(image, 32) };
    } 
    Display.setTitle("Minecraft - DeadZone v 1.0.0");
    Display.setIcon(buffers);
  }
  
  private static ByteBuffer loadInstance(BufferedImage image, int dimension) {
    BufferedImage scaled = new BufferedImage(dimension, dimension, 3);
    Graphics2D g = scaled.createGraphics();
    double width = image.getWidth();
    double height = image.getHeight();
    g.drawImage(image, (int)((scaled.getWidth() - width) / 2.0D), (int)((scaled.getHeight() - height) / 2.0D), (int)width, (int)height, null);
    g.dispose();
    byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
    int counter = 0;
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int colorSpace = image.getRGB(j, i);
        buffer[counter + 0] = (byte)(colorSpace << 8 >> 24);
        buffer[counter + 1] = (byte)(colorSpace << 16 >> 24);
        buffer[counter + 2] = (byte)(colorSpace << 24 >> 24);
        buffer[counter + 3] = (byte)(colorSpace >> 24);
        counter += 4;
      } 
    } 
    return ByteBuffer.wrap(buffer);
  }
}

