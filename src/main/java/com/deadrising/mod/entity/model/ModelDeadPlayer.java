package com.deadrising.mod.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import com.deadrising.mod.entity.EntityDeadPlayer;

import net.minecraft.client.*;

public class ModelDeadPlayer extends ModelPlayer
{
    public ModelDeadPlayer(final float modelSize, final boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
    }
}
