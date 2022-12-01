package com.deadrising.mod.client.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.api.GuiButtonDead;
import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDeadDisconnected extends GuiScreen
{
    private final String reason;
    private final ITextComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int textHeight;
    private String currentTip;
    public static final ResourceLocation menuBackground = new ResourceLocation(Reference.MOD_ID,"textures/gui/menu/background" + (int) (Math.random() * ((5 - 1) + 1)) + ".png");

    public GuiDeadDisconnected(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp, String tip)
    {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey);
        this.message = chatComp;
        this.currentTip = tip;
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
        this.multilineMessage = this.fontRenderer.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.textHeight = this.multilineMessage.size() * this.fontRenderer.FONT_HEIGHT;
        this.buttonList.add(new GuiButtonDead(0, width / 2 - 150, this.height / 2 - 120, 300, 20, "To Main Menu"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glPushMatrix();

        DeadRenderHelper.renderImageCentered(width / 2,0,menuBackground,width,height);
    	
        DeadRenderHelper.renderRectWithOutline(width / 2 - 75,3,150,34,0x22FFFFFF,0x22FFFFFF,1);
        DeadRenderHelper.renderCenteredTextWithShadow(TextFormatting.YELLOW + this.currentTip,width / 2, this.height / 2 - 50,0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, this.reason, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRenderer.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.textHeight / 2;

        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRenderer, s, this.width / 2, i, 16777215);
                i += this.fontRenderer.FONT_HEIGHT;
            }
        }
        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
