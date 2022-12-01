package com.deadrising.mod.utils;

import static net.minecraft.client.Minecraft.getMinecraft;

import javax.annotation.Nullable;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;

public class Access
{

	public static IntegratedServer getIntegratedServer()
	{
		return getMinecraft().getIntegratedServer();
	}


	@Nullable
	public static PlayerProfileCache getPlayerProfileCache()
	{
		return getIntegratedServer() == null ? null : getIntegratedServer().getPlayerProfileCache();
	}


	@Nullable
	public static MinecraftSessionService getMinecraftSessionService()
	{
		return getIntegratedServer() == null ? null : getIntegratedServer().getMinecraftSessionService();
	}
}
