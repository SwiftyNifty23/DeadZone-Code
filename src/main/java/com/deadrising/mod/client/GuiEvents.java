package com.deadrising.mod.client;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.GuiAnyKey;
import com.deadrising.mod.client.gui.GuiDeadConnecting;
import com.deadrising.mod.client.gui.GuiDeadDisconnected;
import com.deadrising.mod.client.gui.GuiDeadInGame;
import com.deadrising.mod.client.gui.GuiDeadMainMenu;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiEvents {


	@SubscribeEvent
	public void onGuiLaunch(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiMainMenu) {
			event.setGui(new GuiDeadMainMenu());
		}
		
		if(event.getGui() instanceof GuiIngameMenu)
		{
			event.setGui(new GuiDeadInGame());
		}
		
		if(event.getGui() instanceof GuiConnecting)
		{
			event.setGui(new GuiDeadConnecting(new GuiDeadMainMenu(), Minecraft.getMinecraft(), new ServerData("Undead Island", Reference.SERVERIP_UNDEADISLAND, false)));
		}
		
	}
}