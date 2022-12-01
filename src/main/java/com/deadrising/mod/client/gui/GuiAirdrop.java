package com.deadrising.mod.client.gui;

import com.deadrising.mod.Reference;
import com.deadrising.mod.tileentity.container.ContainerAirdrop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiAirdrop extends GuiContainer
{
	private static final ResourceLocation AIRDROP_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/airdrop.png");

    private final int inventoryRows;
	
    public GuiAirdrop(IInventory playerInv,IInventory chestInv,int inventoryRows) {
        super(new ContainerAirdrop(playerInv ,chestInv, Minecraft.getMinecraft().player));
    	this.allowUserInput = false;
    	this.inventoryRows = inventoryRows;

	}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AIRDROP_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

    
    
    
}
