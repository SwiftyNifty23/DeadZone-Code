package com.deadrising.mod.api;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class Base 
{
	public static int getTicksFromTime(int seconds)
	{
		return seconds * 20;
	}
	
	public static int getTicksFromMinutes(int minutes)
	{
		return minutes * 60 * 20;
	}
	
}
