package com.deadrising.mod.blocks.props;

import java.util.Random;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.client.entity.EntitySeat;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CampfireBlock extends Block implements IHasModel 
{

	
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
	    this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); 
	}


	public CampfireBlock(String name, Material material, CreativeTabs tab)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		this.setHardness(1); 
		this.setLightLevel(0.8F);
		
		
		
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	
	public void registerModels() 
	{
		deadrising.proxy.registerBlockRender(this, 0, "inventory");
	
	}
	
	

    public boolean isBurning()
    {
        return true;
    }
    
 
    
    
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return super.getFlammability(world, pos, face);
    }
    
    
    
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if(rand.nextInt(24) == 0)
        {
            worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        for(int i = 0; i < 2; ++i)
        {
            double posX = (double) pos.getX() + rand.nextDouble() * 0.8 + 0.2;
            double posY = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double posZ = (double) pos.getZ() + rand.nextDouble() * 0.8 + 0.2;
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public boolean isFullcube(IBlockState state) {
		return false;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
    {
		EnumFacing facing = EnumFacing.getFront(meta);

		if(facing.getAxis()==EnumFacing.Axis.Y) 
		{
			facing=EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING, facing);
    }
	
	//Facing
	@Override
	public int getMetaFromState(IBlockState state) 
    {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
    }
	    
	//Facing
    @Override
	protected BlockStateContainer createBlockState() 
    {
    	return new BlockStateContainer(this, new IProperty[]{FACING});
    }
    
    //Facing
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos,EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) 
    {
	  return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	private boolean canBlockStay(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) 
	{
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}
	
}
