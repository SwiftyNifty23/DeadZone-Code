package com.deadrising.mod.blocks.props;

import javax.annotation.Nullable;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TraficBarrier extends Block implements IHasModel {
	
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    
    public static final AxisAlignedBB AABBs = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 1.6, 0.625);

	public TraficBarrier(String name, Material material, CreativeTabs tab)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		this.setHardness(1); 
	
	
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}


	public void registerModels() 
	{
		deadrising.proxy.registerBlockRender(this, 0, "inventory");

	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABBs;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABBs;
    }

	//Facing
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
	
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
}