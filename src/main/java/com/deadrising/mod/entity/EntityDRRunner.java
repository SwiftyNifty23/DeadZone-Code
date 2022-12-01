package com.deadrising.mod.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.utils.handlers.SoundHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityDRRunner extends EntityZombie
{
	
	private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityDRRunner.class, DataSerializers.VARINT);
	
	public SoundEvent[] hurtSounds = { SoundHandler.ZOMBIEHURT1, SoundHandler.ZOMBIEHURT2, SoundHandler.ZOMBIEHURT3};
	public SoundEvent[] ambientSounds = { SoundHandler.ZOMBIEIDLE1, SoundHandler.ZOMBIEIDLE2, SoundHandler.ZOMBIEIDLE3, SoundHandler.ZOMBIEIDLE4};
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.7D);

    }
	
    protected void preRenderScale(EntityDRRunner enDrRunner, float scale)
    {
        this.preRenderScale(enDrRunner, 4f);// makes the renderer 1.5x bigger in x-z-y.
    }
	
	public EntityDRRunner(World worldIn) {
		super(worldIn);
		
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		Random rand = new Random();
		int infectChance = rand.nextInt(100);
		
		if(infectChance > 90)
		{
			if(entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(deadrising.Infection, Integer.MAX_VALUE));
			}
		}
		return super.attackEntityAsMob(entityIn);
	}
	
	protected boolean shouldBurnInDay() {
		return false;
	}
	
	@Override
 	public AxisAlignedBB getCollisionBoundingBox() {
		// TODO Auto-generated method stub
		return super.getCollisionBoundingBox();
	}
	
	@Override
	protected boolean canEquipItem(ItemStack stack) {
		return false;
	}
	
	@Override
	protected boolean canDropLoot() {
		return false;
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override
	public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
	
	@Override
	protected SoundEvent getAmbientSound() {
		Random rand = new Random();
		return hurtSounds[rand.nextInt(ambientSounds.length - 1)];
	}
	
	@Override
	public boolean isChild() {
		return false;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
	
	@Override
	public boolean isRiding() {
		return false;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		Random rand = new Random();
		return hurtSounds[rand.nextInt(hurtSounds.length - 1)];
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setVariant(this.rand.nextInt(12));
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	public int getVariant()
    {
        return MathHelper.clamp(((Integer)this.dataManager.get(VARIANT)).intValue(), 0, 12);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }


	@Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    public void setVariant(int variant)
    {
        this.dataManager.set(VARIANT, Integer.valueOf(variant));
    }
	
    @Override
    protected void entityInit() {
        this.dataManager.register(VARIANT, Integer.valueOf(0));
    	super.entityInit();
    }
    
}
