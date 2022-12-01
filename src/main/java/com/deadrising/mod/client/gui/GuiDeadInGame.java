package com.deadrising.mod.client.gui;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.api.GuiButtonDead;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;
import com.deadrising.mod.utils.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.GuiModList;

public class GuiDeadInGame extends GuiScreen
{
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.disableDepth();
        DeadRenderHelper.renderRectWithOutline(39, this.height - 178, 126, 150, 1426063360, 1140850688, 1);
        DeadRenderHelper.renderImage(20.0, this.height - 235, new ResourceLocation("deadzone", "textures/gui/logo.png"), 165.0, 43.5);
        DeadRenderHelper.renderCenteredTextScaledWithShadow(TextFormatting.RED + Reference.MOD_VERSION, 102, this.height - 200, 16777215, 1.0);
        EntityPlayer p = mc.player;
        
        float val = (float)(Math.sin((DeadRenderHelper.swing / 55.0F)) * 70.0D);
        DeadRenderHelper.renderPlayer(width / 2 +150,height / 2 + 155,150, val);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonDead(1, 42, this.height - 50, 120, 30, I18n.format("menu.returnToMenu", new Object[0])).setImage(new ResourceLocation("deadzone", "textures/gui/menu/play.png")));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add(new GuiButtonDead(4, 42, this.height - 187, 120, 30, I18n.format("menu.returnToGame", new Object[0])).setImage(new ResourceLocation("deadzone", "textures/gui/menu/play.png")));
        this.buttonList.add(new GuiButtonDead(0, 52, this.height - 101, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButtonDead(12, 52, this.height - 76, 98, 20, I18n.format("fml.menu.modoptions", new Object[0])));
        final GuiButton guibutton = this.addButton((GuiButton)new GuiButtonDead(7, 52, this.height - 126, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
        this.buttonList.add(new GuiButtonDead(5, 52, this.height - 151, 98, 20, I18n.format("gui.advancements", new Object[0])));
        

        
    }
    
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
                break;
            }
            case 1: {
                final boolean flag = this.mc.isIntegratedServerRunning();
                final boolean flag2 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.world.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);
                if (flag) {
                    this.mc.displayGuiScreen((GuiScreen)new GuiDeadMainMenu());
                    break;
                }
                if (flag2) {
                    final RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms((GuiScreen)new GuiDeadMainMenu());
                    break;
                }
                this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiDeadMainMenu()));
                break;
            }
            case 4: {
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                if (this.mc.player != null) {
                    this.mc.displayGuiScreen((GuiScreen)new GuiScreenAdvancements(this.mc.player.connection.getAdvancementManager()));
                    break;
                }
                break;
            }
            case 6: {
                if (this.mc.player != null) {
                    this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.player.getStatFileWriter()));
                    break;
                }
                break;
            }
            case 7: {
                this.mc.displayGuiScreen((GuiScreen)new GuiShareToLan((GuiScreen)this));
                break;
            }
            case 12: {
                this.mc.displayGuiScreen((GuiScreen)new GuiModList((GuiScreen)this));
                break;
            }
        }
    }
    
}
