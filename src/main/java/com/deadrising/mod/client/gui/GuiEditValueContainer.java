package com.deadrising.mod.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import com.deadrising.mod.client.gui.components.TextFieldComponent;
import com.deadrising.mod.client.gui.components.ToggleComponent;
import com.deadrising.mod.client.gui.components.ValueComponent;
import com.deadrising.mod.network.PacketHandler;
import com.deadrising.mod.network.message.MessageUpdateValueContainer;
import com.deadrising.mod.tileentity.IValueContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GuiEditValueContainer extends GuiScreen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("cubga:textures/gui/value_container.png");

    public static final int WIDTH = 176;
    public static final int PADDING = 10;
    public static final int VALUE_HEIGHT = 35;

    private List<ValueComponent> values = new ArrayList<>();
    private IValueContainer valueContainer;
    private int containerHeight;

    public GuiEditValueContainer(IValueContainer valueContainer)
    {
        this.valueContainer = valueContainer;
        valueContainer.getEntries().forEach(entry ->
        {
            if(entry.getType() != null)
            {
                switch(entry.getType())
                {
                    case TEXT_FIELD:
                        values.add(new TextFieldComponent(entry));
                        break;
                    case TOGGLE:
                        values.add(new ToggleComponent(entry));
                        break;
                }
            }
        });
        this.containerHeight = values.size() * VALUE_HEIGHT + 10 * 2;
    }

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int startX = (this.width - WIDTH) / 2;
        int startY = (this.height - this.containerHeight) / 2;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);

        //Top
        drawScaledCustomSizeModalRect(startX, startY, 0, 0, WIDTH, 10, WIDTH, 10, 256, 256);

        //Middle
        drawScaledCustomSizeModalRect(startX, startY + 10, 0, 10, WIDTH, 1, WIDTH, values.size() * VALUE_HEIGHT, 256, 256);

        //Bottom
        drawScaledCustomSizeModalRect(startX, startY + values.size() * VALUE_HEIGHT + 10, 0, 10, WIDTH, 10, WIDTH, 10, 256, 256);

        for(int i = 0; i < values.size(); i++)
        {
            values.get(i).render(startX + PADDING, startY + i * VALUE_HEIGHT + 10, mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        for(ValueComponent value : values)
        {
            value.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        for(ValueComponent value : values)
        {
            value.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        PacketHandler.INSTANCE.sendToServer(new MessageUpdateValueContainer(values, valueContainer));
    }

}
