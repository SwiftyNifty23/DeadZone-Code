package com.deadrising.mod.client.gui;

import java.io.IOException;
import java.net.UnknownHostException;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.api.GuiButtonDead;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;
import com.deadrising.mod.client.gui.utilities.ScissorState;
import com.deadrising.mod.utils.handlers.ConfigHandler;
import com.deadrising.mod.utils.handlers.ConfigHandler.Server;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.ExtendedServerListData;
import net.minecraftforge.fml.client.GuiAccessDenied;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEUServerBrowser extends GuiDead {

    public static String designatedServerIP = Reference.SERVERIP_UNDEADISLAND;
    public static String designatedServerIP2 = Reference.SERVERIP_UNDEADISLAND;
    private ServerData data;
    private ServerData data2;
    
    public boolean isOnline;
    

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX,mouseY,partialTicks);

        DeadRenderHelper.renderRectWithOutline(width / 2 - 75,3,150,34,0x22FFFFFF,0x22FFFFFF,1);
        
        DeadRenderHelper.renderRectWithOutline(0,0,width,40,0x55000000,0x44000000,1);
        DeadRenderHelper.renderRectWithOutline(0,height - 40,width,40,0x55000000,0x44000000,1);
        
        DeadRenderHelper.renderCenteredTextWithShadow(I18n.format("gui.server.eu"),width / 2,5,0xFFFFFF);
        //Server Name
        DeadRenderHelper.renderCenteredTextWithShadow(data.serverName ,width / 2 - 108, height - 195,0xFFFFFF);
        //Server Ping
        if(!isOnline)
        {
            DeadRenderHelper.renderTextWithShadow(TextFormatting.RED + "OFFLINE" ,width / 2 - 130, height - 185,0xFFFFFF);
        }
        else
        {
            DeadRenderHelper.renderTextWithShadow(TextFormatting.GREEN + "ONLINE" ,width / 2 - 130, height - 185,0xFFFFFF);
            //Player Count
        }
        DeadRenderHelper.renderText(data.populationInfo ,width / 2 - 130, height - 175,0xFFFFFF);
    }
    
    @Override
    public void updateScreen() {
    	super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        switch(button.id){
            case BUTTON_QUIT:
                mc.displayGuiScreen(new GuiDeadMainMenu());
                break;
            case BUTTON_JOINOFFSERVER:
                designatedServerIP = Reference.SERVERIP_UNDEADISLAND;
                mc.displayGuiScreen(new GuiDeadMainMenu());
            case BUTTON_PLAY:
                this.joinServer(designatedServerIP,false);
                break;
            case 2:
            	mc.displayGuiScreen(new GuiEUServerBrowser());
            	break;
            case BUTTON_SINGLEPLAYER:
            	mc.displayGuiScreen(new GuiServerBrowser());
                break;
            	
        }

    }
    /**
     * Initialize GUI - Initialize the GUI
     */
    public void initGui() {
        this.buttonList.add(new GuiButtonDead(BUTTON_QUIT, 55, height - 60, 80, 15, "Back"));
        this.buttonList.add(new GuiButtonDead(2, 150, height - 60, 80, 15, "Refresh"));
        this.buttonList.add(new GuiButtonDead(BUTTON_SINGLEPLAYER, 335, height - 60, 80, 15, "Us Servers"));
        this.buttonList.add(new GuiButtonDead(BUTTON_PLAY, 243, height - 60, 80, 15, "Join Server"));
        ServerData data = new ServerData("Adagio Isle", designatedServerIP, false);
        this.data = data;
        isOnline = true;
        ServerPinger ping = new ServerPinger();
        try {
			ping.ping(data);
		} catch (Exception e) {
			isOnline = false;
			//e.printStackTrace();
		}
       
        
        this.buttonList.add(new GuiButtonDead(BUTTON_JOINOFFSERVER, width / 2 - 140, height - 198, 250, 40, I18n.format(""))
                .setAlwaysHighlighted(designatedServerIP.equalsIgnoreCase(Reference.SERVERIP_UNDEADISLAND)));
    }
}