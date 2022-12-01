package com.deadrising.mod.client.gui;

import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiHealthbar extends Gui {

    private final ResourceLocation bar = new ResourceLocation(Reference.MOD_ID, "textures/gui/healthbar.png");
    private final ResourceLocation infectedbar = new ResourceLocation(Reference.MOD_ID, "textures/gui/infectedbar.png");
    private final ResourceLocation hungerbar = new ResourceLocation(Reference.MOD_ID, "textures/gui/hungerbar.png");
    private final int tex_width = 102, tex_height = 8, bar_width = 100, bar_height = 6;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
    	//if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) event.setCanceled(true);
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            drawHealthBar();
            drawHungerBar();
            //drawPrimary();
        }
    }
    
    public void drawPrimary()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":textures/gui/equippeditem.png"));
		this.drawModalRectWithCustomSizedTexture(200, 230, 0, 0, 64, 64, 64, 64);
		mc.getRenderItem().renderItemAndEffectIntoGUI(mc.player.getHeldItemMainhand(), 220, 235);
    }
    
    public void drawHungerBar()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	mc.renderEngine.bindTexture(hungerbar);
        float oneUnit = (float)bar_width / 20;
        int currentWidth = (int)(oneUnit * mc.player.getFoodStats().getFoodLevel());
        int x = 368;
        drawTexturedModalRect(x, 227, 0, 0, tex_width, tex_height);
        drawTexturedModalRect(x + 1, 227, 1, tex_height, currentWidth, tex_height);
    }
    
    public void drawHealthBar()
    {
    	Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = (EntityPlayer) mc.player;
        if(player.getActivePotionEffect(deadrising.Infection) != null)
        {
        	mc.renderEngine.bindTexture(infectedbar);
        }
        else
        {
            mc.renderEngine.bindTexture(bar);
        }
        float oneUnit = (float)bar_width / mc.player.getMaxHealth();
        int currentWidth = (int)(oneUnit * mc.player.getHealth());
        int x = 368;
        drawTexturedModalRect(x, 238, 0, 0, tex_width, tex_height);
        drawTexturedModalRect(x + 1, 238, 1, tex_height, currentWidth, tex_height);
    }

}
