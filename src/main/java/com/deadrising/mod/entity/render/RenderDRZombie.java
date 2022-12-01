package com.deadrising.mod.entity.render;

import com.deadrising.mod.Reference;
import com.deadrising.mod.entity.EntityDRZombie;
import com.deadrising.mod.entity.model.ModelDRZombie;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDRZombie extends RenderBiped<EntityDRZombie>
{
	
	public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie1.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie2.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie3.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie4.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie5.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie6.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie7.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie8.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie9.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie10.png"), new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie11.png"),
			new ResourceLocation(Reference.MOD_ID + ":textures/entity/zombie/zombie12.png")};

	public RenderDRZombie(RenderManager manager) {
		super(manager, new ModelDRZombie(), 0.3f);
	}
	
	protected void applyRotations(EntityDRZombie entityLiving, float p_77043_2_, float rotationYaw, float PartialTicks ) {
		
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, PartialTicks);
		
	}	
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDRZombie entity)
    {
        return TEXTURES[entity.getVariant()];
    }
}
