package com.deadrising.mod.client.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author jtripled
 */
public class EntitySeat extends Entity
{
    public int blockPosX;
    public int blockPosY;
    public int blockPosZ;

    public EntitySeat(World world)
    {
        super(world);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }

    public EntitySeat(World world, double x, double y, double z, double y0ffset)
    {
        this(world);
        this.blockPosX = (int) x;
        this.blockPosY = (int) y;
        this.blockPosZ = (int) z;
        setPosition(x + 0.5D, y + y0ffset, z + 0.5D);
    }

    public EntitySeat(World world, double x, double y, double z, double y0ffset, int rotation, double rotationOffset)
    {
        this(world);
        this.blockPosX = (int) x;
        this.blockPosY = (int) y;
        this.blockPosZ = (int) z;
        setPostionConsideringRotation(x + 0.5D, y + y0ffset, z + 0.5D, rotation, rotationOffset);
    }

    public void setPostionConsideringRotation(double x, double y, double z, int rotation, double rotationOffset)
    {
        switch(rotation)
        {
            case 2:
                z += rotationOffset;
                break;
            case 0:
                z -= rotationOffset;
                break;
            case 3:
                x -= rotationOffset;
                break;
            case 1:
                x += rotationOffset;
                break;
        }
        setPosition(x, y, z);
    }

    @Override
    public double getMountedYOffset()
    {
        return this.height * 0.0D;
    }

    @Override
    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }

    @Override
    public void onEntityUpdate()
    {
        if(!this.world.isRemote)
        {
            if(!this.isBeingRidden() || this.world.isAirBlock(new BlockPos(blockPosX, blockPosY, blockPosZ)))
            {
                this.setDead();
            }
        }
    }

    @Override
    protected void entityInit()
    {
        
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        
    }
    
    public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double par6)
    {
        if(!checkForExistingEntity(world, x, y, z, player))
        {
            EntitySeat seat = new EntitySeat(world, x, y, z, par6);
            world.spawnEntity(seat);
            player.startRiding(seat);
        }
        return true;
    }

    public static boolean sitOnBlockWithRotationOffset(World world, double x, double y, double z, EntityPlayer player, double par6, int metadata, double offset)
    {
        if(!checkForExistingEntity(world, x, y, z, player) && !world.isRemote)
        {
            EntitySeat nemb = new EntitySeat(world, x, y, z, par6, metadata, offset);
            world.spawnEntity(nemb);
            player.startRiding(nemb);
        }
        return true;
    }

    public static boolean checkForExistingEntity(World world, double x, double y, double z, EntityPlayer player)
    {
        List<EntitySeat> listEMB = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
        for(EntitySeat mount : listEMB)
        {
            if(mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
            {
                if(!mount.isBeingRidden())
                {
                    player.startRiding(mount);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isSomeoneSitting(World world, double x, double y, double z)
    {
        List<EntitySeat> listEMB = world.getEntitiesWithinAABB(EntitySeat.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
        for(EntitySeat mount : listEMB)
        {
            if(mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
            {
                return mount.isBeingRidden();
            }
        }
        return false;
    }
}
