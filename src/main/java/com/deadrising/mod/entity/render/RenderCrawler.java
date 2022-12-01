package com.deadrising.mod.entity.render;

import com.deadrising.mod.Reference;
import com.deadrising.mod.entity.EntityCrawler;
import com.deadrising.mod.entity.EntityDRZombie;
import com.deadrising.mod.entity.model.ModelCrawler;
import com.deadrising.mod.entity.model.ModelDRZombie;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrawler extends RenderLiving<EntityCrawler>
{
	
	public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/crawler1.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/crawler2.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/crawler3.png")};

	public RenderCrawler(RenderManager manager) {
		super(manager, new ModelCrawler(), 0.3f);
	}
	
	protected void applyRotations(EntityCrawler entityLiving, float p_77043_2_, float rotationYaw, float PartialTicks ) {
		
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, PartialTicks);
		
	}	
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCrawler entity)
    {
        return TEXTURES[entity.getVariant()];
    }
}
