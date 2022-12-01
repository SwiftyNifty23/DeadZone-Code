package com.deadrising.mod.client.render;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.model.ModelAirdrop;
import com.deadrising.mod.tileentity.TileEntityAirdrop;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityAirdropRender extends TileEntitySpecialRenderer<TileEntityAirdrop> 
{
	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(Reference.MOD_ID + ":textures/entity/tileentity/airdrop");
    private final ModelAirdrop airdrop = new ModelAirdrop();

    public TileEntityAirdropRender() {
    }

    @Override
    public void render(TileEntityAirdrop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
    {
    	this.bindTexture(TEXTURE_NORMAL);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0f, 4.8f, 0f);
            GlStateManager.scale(0.2f, 0.2f, 0.2f);
            GlStateManager.rotate(180f, 1f, 0f, 0f);
            airdrop.renderDrop(1);
        }
        GlStateManager.popMatrix();
    }
}
