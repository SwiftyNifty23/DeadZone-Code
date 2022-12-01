package com.deadrising.mod.client.gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.api.GuiButtonDead;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDeadConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;
    private String tip;
    public static final ResourceLocation menuBackground = new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/background" + (int) (Math.random() * ((5 - 1) + 1)) + ".png");

    public GuiDeadConnecting(GuiScreen parent, Minecraft mcIn, ServerData serverDataIn)
    {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        ServerAddress serveraddress = ServerAddress.fromString(serverDataIn.serverIP);
        mcIn.loadWorld((WorldClient)null);
        mcIn.setServerData(serverDataIn);
        this.connect(serveraddress.getIP(), serveraddress.getPort());
    }

    public GuiDeadConnecting(GuiScreen parent, Minecraft mcIn, String hostName, int port)
    {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        mcIn.loadWorld((WorldClient)null);
        this.connect(hostName, port);
    }

    private void connect(final String ip, final int port)
    {
        LOGGER.info("Connecting to {}, {}", ip, Integer.valueOf(port));
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (GuiDeadConnecting.this.cancel)
                    {
                        return;
                    }

                    inetaddress = InetAddress.getByName(ip);
                    GuiDeadConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiDeadConnecting.this.mc.gameSettings.isUsingNativeTransport());
                    GuiDeadConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiDeadConnecting.this.networkManager, GuiDeadConnecting.this.mc, GuiDeadConnecting.this.previousGuiScreen));
                    GuiDeadConnecting.this.networkManager.sendPacket(new C00Handshake(ip, port, EnumConnectionState.LOGIN, true));
                    GuiDeadConnecting.this.networkManager.sendPacket(new CPacketLoginStart(GuiDeadConnecting.this.mc.getSession().getProfile()));
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (GuiDeadConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiDeadConnecting.LOGGER.error("Couldn't connect to server", (Throwable)unknownhostexception);
                    GuiDeadConnecting.this.mc.displayGuiScreen(new GuiDeadDisconnected(GuiDeadConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"}), tip));
                }
                catch (Exception exception)
                {
                    if (GuiDeadConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiDeadConnecting.LOGGER.error("Couldn't connect to server", (Throwable)exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiDeadConnecting.this.mc.displayGuiScreen(new GuiDeadDisconnected(GuiDeadConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[] {s}), tip));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.networkManager != null)
        {
            if (this.networkManager.isChannelOpen())
            {
                this.networkManager.processReceivedPackets();
            }
            else
            {
                this.networkManager.checkDisconnected();
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonDead(0, width / 2 - 150, this.height / 2 + 80, 300, 20, "Cancel"));
        
        Random rand = new Random();
        int Tip = rand.nextInt(5);
        switch(Tip)
        {
	        case 0:
	        	this.tip = "Tip: Loot and find weapons to survive";
	        	break;
	        case 1:
	        	this.tip = "Tip: Stay away from runners they are fast and deadly";
	        	break;
	        case 2:
	        	this.tip = "Tip #3";
	        	break;
	        case 3:
	        	this.tip = "Tip #4";
	        	break;
	        case 4:
	        	this.tip = "Tip #5";
	        	break;
	        case 5:
	        	this.tip = "Tip #6";
	        	break;
	        default:
	        	this.tip = "Tip #7";
	        	break;
        }
        
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.cancel = true;

            if (this.networkManager != null)
            {
                this.networkManager.closeChannel(new TextComponentString("Aborted"));
            }

            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	

        GL11.glPushMatrix();

        DeadRenderHelper.renderImageCentered(width / 2,0,menuBackground,width,height);
    	
        //DeadRenderHelper.renderRectWithOutline(width / 2 - 75,3,150,34,0x22FFFFFF,0x22FFFFFF,1);
        DeadRenderHelper.renderCenteredTextWithShadow(TextFormatting.YELLOW + this.tip,width / 2, this.height / 2 - 50,0xFFFFFF);

        if (this.networkManager == null)
        {
            this.drawCenteredString(this.fontRenderer, "Connecting", this.width / 2, this.height / 2 - 20, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRenderer, I18n.format("connect.authorizing"), this.width / 2, this.height / 2 - 20, 16777215);
        }
        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
