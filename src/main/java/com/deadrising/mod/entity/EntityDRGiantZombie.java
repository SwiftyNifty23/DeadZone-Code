package com.deadrising.mod.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.utils.handlers.LootTableHandler;
import com.deadrising.mod.utils.handlers.SoundHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityDRGiantZombie extends EntityZombie
{

	private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityDRGiantZombie.class, DataSerializers.VARINT);
	
	public SoundEvent[] hurtSounds = { SoundHandler.ZOMBIEHURT1, SoundHandler.ZOMBIEHURT2, SoundHandler.ZOMBIEHURT3};
	public SoundEvent deathSounds = SoundHandler.ZOMBIEHURT1;
	public SoundEvent[] ambientSounds = { SoundHandler.ZOMBIEIDLE1, SoundHandler.ZOMBIEIDLE2, SoundHandler.ZOMBIEIDLE3, SoundHandler.ZOMBIEIDLE4};
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(9.0D);
        
    }
	
	
	
	public EntityDRGiantZombie(World worldIn) {
		super(worldIn);
		
		this.setSize(3, 3);
	}
	
    private void getMaxSpawnedInChunk(float f) {
		
    	this.getMaxSpawnedInChunk(1f);
    	
		
	}

    @Override
	protected ResourceLocation getLootTable() 
	{
		return LootTableHandler.TEST;
	}
	
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		Random rand = new Random();
		int infectChance = rand.nextInt(100);
		
		if(infectChance > 40)
		{
			
			if(entityIn instanceof EntityLivingBase)
			{
				Minecraft mc = Minecraft.getMinecraft();
				if(mc.player.isCreative()) {
					
					((EntityLivingBase)entityIn).sendMessage((ITextComponent) new TextComponentString(TextFormatting.RED + "You went into creative and your infection was lose"));
					((EntityLivingBase)entityIn).removePotionEffect(deadrising.Infection);
				
				
				}else {
					((EntityLivingBase)entityIn).sendMessage((ITextComponent) new TextComponentString(TextFormatting.RED + "You have been infected"));
					((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(deadrising.Infection, Integer.MAX_VALUE, 0, true, false));					
				}

				
			}
		}
		return super.attackEntityAsMob(entityIn);
	}
	
	@Override
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
		return true;
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
		return ambientSounds[rand.nextInt(ambientSounds.length - 1)];
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
		return deathSounds;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		Random rand = new Random();
		return hurtSounds[rand.nextInt(hurtSounds.length - 1)];
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setVariant(this.rand.nextInt(4));
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	public int getVariant()
    {
        return MathHelper.clamp(((Integer)this.dataManager.get(VARIANT)).intValue(), 0, 4);
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
