package com.deadrising.mod.client.gui.components;

import com.deadrising.mod.client.gui.GuiEditValueContainer;
import com.deadrising.mod.tileentity.IValueContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;


public class ToggleComponent extends ValueComponent
{
    private GuiButton button;
    private boolean state;

    public ToggleComponent(IValueContainer.Entry entry)
    {
        super(entry.getId(), entry.getName());
        this.button = new GuiButton(0, 0, 0, GuiEditValueContainer.WIDTH - GuiEditValueContainer.PADDING * 2, 20, "Off");
        this.setState(Boolean.valueOf(entry.getValue()));
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY)
    {
        super.render(x, y, mouseX, mouseY);
        button.x = x;
        button.y = y + 10;
        button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, 0);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(button.isMouseOver())
        {
            this.setState(!state);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {

    }

    @Override
    public String getValue()
    {
        return Boolean.toString(state);
    }

    @Override
    public IValueContainer.Entry toEntry()
    {
        return new IValueContainer.Entry(id, getValue());
    }

    public void setState(boolean state)
    {
        this.state = state;
        if(state)
        {
            button.displayString = "On";
        }
        else
        {
            button.displayString = "Off";
        }
    }
}
