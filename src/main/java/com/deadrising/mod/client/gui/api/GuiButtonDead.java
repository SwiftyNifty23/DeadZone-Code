package com.deadrising.mod.client.gui.api;

import com.deadrising.mod.client.gui.utilities.DeadRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiButtonDead extends GuiButton  {

    private int fade = 0;

    private int color = 0x55000000;

    private ResourceLocation buttonImage = null;

    private boolean isFirstHover = true;

    private double scale = 1;

    private int yOffset = 0;

    private boolean isAlwaysHighlighted = false;
    private boolean isDisabled = false;

    public GuiButtonDead(int buttonId, int x, int y, int givenWidth, int givenHeight, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.width = givenWidth;
        this.height = givenHeight;
    }

    public GuiButtonDead setColor(int givenColor){
        this.color = givenColor;
        return this;
    }

    public GuiButtonDead setImage(ResourceLocation givenResource){
        this.buttonImage = givenResource;
        return this;
    }

    public GuiButtonDead setScale(double givenScale){
        this.scale = givenScale;
        return this;
    }

    public GuiButtonDead setYOffset(int givenYOffset){
        this.yOffset = givenYOffset;
        return this;
    }

    public GuiButtonDead setAlwaysHighlighted(boolean givenHighlighted){
        this.isAlwaysHighlighted = givenHighlighted;
        return this;
    }

    public GuiButtonDead setDisabled(boolean isDisabled){
        this.isDisabled = isDisabled;
        return this;
    }

    public void updateButton()
    {
        if (isMouseOver()) {
            if (fade <= 0) {
                fade = 0;
            } else {
                fade -= 15;
            }
        } else {
            fade = 90;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {

        this.hovered = (mouseX > this.x && mouseX < this.x + width && mouseY > this.y && mouseY < this.y + height);

        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;

            DeadRenderHelper.renderRectWithOutline(this.x,this.y,this.width,this.height,color,color,1);

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }
            else if (this.isAlwaysHighlighted)
            {
                j = 16777120;
            }
            if (this.isDisabled)
            {
                this.enabled = false;
            }

            if(this.buttonImage != null){
            	DeadRenderHelper.renderImage(x,y,this.buttonImage,this.width,this.height);
            }

            if(isMouseOver() || isAlwaysHighlighted) {

                if(this.isFirstHover){
                    this.isFirstHover = false;
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 2.0F));
                }

                DeadRenderHelper.renderRect(this.x, this.y, this.width, this.height, 0x33FFFFFF);

            } else {
                this.isFirstHover = true;
            }

            DeadRenderHelper.renderCenteredTextScaledWithShadow(this.displayString, this.x + this.width / 2, (this.y + (this.height - 8) / 2) + this.yOffset, j, this.scale);
        }
    }

}