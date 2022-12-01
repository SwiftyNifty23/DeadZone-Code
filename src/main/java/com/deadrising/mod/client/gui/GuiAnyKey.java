package com.deadrising.mod.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;


public class GuiAnyKey extends GuiScreen
{

	private int y = 200;
	private int flag;

    public static final ResourceLocation menuBackground = new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/background" + (int) (Math.random() * ((5 - 1) + 1)) + ".png");
	public GuiDeadMainMenu mainMenu;
	private GuiButton contBut;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        DeadRenderHelper.renderImageCentered(width / 2,0,menuBackground,width,height);
		String text1 = String.valueOf(TextFormatting.DARK_RED) + TextFormatting.BOLD + "Click Anywhere to Start";
		ScaledResolution sr = new ScaledResolution(this.mc);
		
		this.drawString(this.fontRenderer, text1, (sr.getScaledWidth() / 2) - (this.fontRenderer.getStringWidth(text1) / 2), this.y, 0xFFFFFF);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
    public static void renderCenteredTextScaled(final String text, final int posX, final int posY, final int color, final double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)posX, (double)posY, 0.0);
        GL11.glScaled(givenScale, givenScale, givenScale);
        renderCenteredText(text, 0, 0, color);
        GL11.glPopMatrix();
    }
    
    
    
    public static void renderText(final String text, final int posX, final int posY, final int color) {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.fontRenderer.drawString(text, posX, posY, color);
    }
    
    @Override
    public void updateScreen() {
    	
    	y = y + 1 * flag;
    	
    	if(y >= 200)
    		flag = -1;
    	
    	if(y <= 195)
    		flag = 1;
    	
    	super.updateScreen();
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.mc.displayGuiScreen(new GuiDeadMainMenu());
    	super.mouseReleased(mouseX, mouseY, state);
    }
    
    public static void renderCenteredText(final String text, final int posX, final int posY, final int color) {
        final Minecraft mc = Minecraft.getMinecraft();
        renderText(text, posX - mc.fontRenderer.getStringWidth(text) / 2, posY, color);
    }
    
}
