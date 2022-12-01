package com.deadrising.mod.client.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.api.GuiButtonDead;
import com.deadrising.mod.client.gui.api.GuiContainer;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GuiDead extends GuiScreen {

    protected ArrayList<GuiContainer> guiContainers = new ArrayList();

    public String uiTitle = "Unknown";

    public static final ResourceLocation menuBackground = new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/background" + (int) (Math.random() * ((4 - 1) + 1)) + ".png");

    public final int BUTTON_LINK_DISCORD = 200;
    public final int BUTTON_LINK_WEBSITE = 201;

    public final int BUTTON_SERVERLIST = 202;
    public final int BUTTON_JOINOFFSERVER = 202;
    public final int BUTTON_PLAY = 204;

    public final int BUTTON_NEWS = 205;

    public final int BUTTON_SETTINGS = 206;

    public final int BUTTON_SINGLEPLAYER = 207;
    public final int BUTTON_MULTIPLAYER = 208;

    public final int BUTTON_QUIT = 209;

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        for(GuiButton button : this.buttonList){
            if(button instanceof GuiButtonDead){
                ((GuiButtonDead)button).updateButton();
            }
        }
    }

    /**
     * Draw Screen - Draw the GUI Screen
     * @param mouseX - Given Mouse X
     * @param mouseY - Given Mouse Y
     * @param partialTicks - Given Partial Ticks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){

        GL11.glPushMatrix();

        DeadRenderHelper.renderImageCentered(width / 2,0,menuBackground,width,height);

        DeadRenderHelper.renderRectWithOutline(0,0,width,40,0x55000000,0x44000000,1);
        DeadRenderHelper.renderRectWithOutline(0,height - 40,width,40,0x55000000,0x44000000,1);
        DeadRenderHelper.renderImage(4,3,new ResourceLocation(Reference.MOD_ID,"textures/gui/logo.png"),110,29);

        DeadRenderHelper.renderCenteredTextScaled(TextFormatting.RED + "DEADZONE v" + Reference.MOD_VERSION,58,34,0xFFFFFF,0.5);

        GL11.glPopMatrix();

        super.drawScreen(mouseX,mouseY,partialTicks);
    }

    /**
     * Set UI Title - Set the Title of the GUI
     * @param givenTitle - Given Title Name (String)
     */
    public void setUiTitle(String givenTitle){
        this.uiTitle = givenTitle;
    }

    /**
     * Get UI Title - Get the Title of the UI
     * @return - Returns the given Title Name of the UI (String)
     */
    public String getUiTitle(){
        return this.uiTitle;
    }

    /**
     * Add Container - Add a new Container to the GUI Interface
     * @param container - Given Container (GuiContainer)
     */
    public void addContainer(GuiContainer container) {
        container.initGui();
        guiContainers.add(container);
    }

    /**
     * Update Containers - Update the Containers on the GUI
     */
    public void updateContainers() {
        for (GuiContainer gui : guiContainers) {
            gui.updateScreen();
        }
    }

    /**
     * Get Container - Get a Container with a specific ID
     * @param containerID - Given Container ID
     * @return - Returns the found Container with that ID (GuiContainer)
     */
    public GuiContainer getContainer(int containerID) {
        for (GuiContainer cont : guiContainers) {
            if (cont.containerID == containerID) {
                return cont;
            }
        }
        return null;
    }

    private static final String pad(String s) {
        return (s.length() == 1) ? "0" + s : s;
    }

    public int toHex(Color color) {
        String alpha = pad(Integer.toHexString(color.getAlpha()));
        String red = pad(Integer.toHexString(color.getRed()));
        String green = pad(Integer.toHexString(color.getGreen()));
        String blue = pad(Integer.toHexString(color.getBlue()));
        String hex = "0x" + alpha + red + green + blue;
        return Integer.parseInt(hex, 16);
    }

    /**
     * Open URL - Opens a given URL in Default System Web Browser
     * @param givenURL - Given URL to Open
     */
    public void openURL(String givenURL){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(givenURL));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Join Server - Join a specific Server from given IP
     * @param givenIP - Given Server IP
     * @param isLan - Is Server Lan? Yes/No is lan/isn't lan
     */
    public void joinServer(String givenIP, boolean isLan){

        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiConnecting)) {
            FMLClientHandler.instance().setupServerList();
            FMLClientHandler.instance().connectToServer(Minecraft.getMinecraft().currentScreen, new ServerData("Server", givenIP, isLan));
        }

    }
}
