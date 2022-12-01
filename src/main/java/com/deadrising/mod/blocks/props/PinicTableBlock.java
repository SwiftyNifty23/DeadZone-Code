package com.deadrising.mod.blocks.props;

import java.util.List;
import java.util.Random;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.CollisionHelper;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PinicTableBlock extends Block implements IHasModel 
{

	
	  protected static final AxisAlignedBB LAMP_AAB = new AxisAlignedBB(-.5, 0, -1.5, 1.5, 1, 1.5);


		public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
		{
		    this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); 
		}

		
	public PinicTableBlock(String name, Material material, CreativeTabs tab)
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
	
	
	  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	    {
	        return LAMP_AAB;
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
