package com.deadrising.mod.client.gui;

import java.awt.Image;

import com.deadrising.mod.Reference;
import com.deadrising.mod.deadrising;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;
import com.deadrising.mod.utils.ImageUtil;
import com.deadrising.mod.utils.handlers.ConfigHandler;
import com.deadrising.mod.utils.handlers.SoundHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOverlayHandler extends Gui {

    private final ResourceLocation bar = new ResourceLocation(Reference.MOD_ID, "textures/gui/healthbar.png");
    private final ResourceLocation infectedbar = new ResourceLocation(Reference.MOD_ID, "textures/gui/infectedbar.png");
    private final ResourceLocation hungerbar = new ResourceLocation(Reference.MOD_ID, "textures/gui/hungerbar.png");
    private final ResourceLocation brokenleg = new ResourceLocation(Reference.MOD_ID, "textures/gui/shit.png");
    private final ResourceLocation beta = new ResourceLocation(Reference.MOD_ID, "textures/gui/beta.png");
    private final ResourceLocation infection = new ResourceLocation(Reference.MOD_ID, "textures/gui/1.png");
    private final int tex_width = 102, tex_height = 8, bar_width = 100, bar_height = 6;

    
    private final int text_width = 40, text_height = 40;
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
    	
    	Minecraft mc = Minecraft.getMinecraft();
    	if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) event.setCanceled(true);
    	//if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) event.setCanceled(true);
    	//if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) event.setCanceled(true);
    	if(event.getType() == RenderGameOverlayEvent.ElementType.ARMOR) event.setCanceled(true);
    	

        if (event.getType() == ElementType.ALL) {
        	
        	
            if(mc.player.getActivePotionEffect(deadrising.Infection) != null){
            	
            	
        		drawInfeciton();
            }
             
        }
 
            
    	//drawPlayerStats();
        if(mc.player.getActivePotionEffect(MobEffects.SLOWNESS) != null){
        	
        	//drawBrokenLeg();
             
        }
        
        
        
        
        
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            
        	if (mc.playerController.gameIsSurvivalOrAdventure()) {
            	//drawHealthBar();
                //drawHungerBar();
        	}

      
                
            
        }
    }
    
    

    
    
    
    public void drawPrimary()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":textures/gui/equippeditem.png"));
		this.drawModalRectWithCustomSizedTexture(200, 230, 0, 0, 64, 64, 64, 64);
		mc.getRenderItem().renderItemAndEffectIntoGUI(mc.player.getHeldItemMainhand(), 220, 235);
    }
    
    public void drawInfeciton()
    {
    	
    	Minecraft mc = Minecraft.getMinecraft();
    	ScaledResolution res = new ScaledResolution(mc);
    	ImageUtil.drawFullScreenImage(mc, res, new ResourceLocation(Reference.MOD_ID, "textures/gui/infection.png"), true);
    }
    
    
    public void drawPlayerStats() {
    	Minecraft mc = Minecraft.getMinecraft();
    	
        DeadRenderHelper.renderTextScaled("Player Kills - " + "" + ConfigHandler.ClientSide.PlayerKills, 300, 2, 16777215, 1);
        DeadRenderHelper.renderTextScaled("Zombie Kills - " + "" + ConfigHandler.ClientSide.ZombieKills,  300, 12, 16777215, 1);
        DeadRenderHelper.renderTextScaled("Deaths - " + "" + ConfigHandler.ClientSide.Deaths,  300, 22, 16777215, 1);
    }
    
    
    public void drawHungerBar()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	
    	mc.renderEngine.bindTexture(hungerbar);
        float oneUnit = (float)bar_width / 20;
        int currentWidth = (int)(oneUnit * mc.player.getFoodStats().getFoodLevel());
        drawTexturedModalRect(0, 5, 0, 0, tex_width, tex_height);
        drawTexturedModalRect(0, 5, 0, tex_height, currentWidth, tex_height);
        
    }
    
    public void drawBrokenLeg()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	
    	ImageUtil.drawCustomSizedImage(mc, brokenleg, 0, 0, 40, 40, true);
        
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
        drawTexturedModalRect(0, 16, 0, 0, tex_width, tex_height);
        drawTexturedModalRect(0, 16, 0, tex_height, currentWidth, tex_height);
    }

}
