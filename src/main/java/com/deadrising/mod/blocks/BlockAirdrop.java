package com.deadrising.mod.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.tileentity.TileEntityAirdrop;
import com.deadrising.mod.utils.GuiHandler;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAirdrop extends BlockContainer {
	
	public BlockAirdrop(String name) {
		super(Material.IRON);
		this.setHardness(2.5F);
		setRegistryName(name);
		setCreativeTab(null);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}
	
	/*@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.airdrop);
	}*/
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		int i = 0;
		BlockPos blockpos = pos.west();
        BlockPos blockpos1 = pos.east();
        BlockPos blockpos2 = pos.north();
        BlockPos blockpos3 = pos.south();
        
        if (worldIn.getBlockState(blockpos).getBlock() == this) {
        	 if (this.isAnotherChest(worldIn, blockpos)) {
                 return false;
             }
        	 i++;
        }
        
        if (worldIn.getBlockState(blockpos1).getBlock() == this) {
        	 if (this.isAnotherChest(worldIn, blockpos1)) {
                 return false;
             }
        	 i++;
        }
        
        if (worldIn.getBlockState(blockpos2).getBlock() == this) {
        	 if (this.isAnotherChest(worldIn, blockpos2)) {
                 return false;
             }
        	 i++;
        }
        
        if (worldIn.getBlockState(blockpos3).getBlock() == this) {
        	 if (this.isAnotherChest(worldIn, blockpos3)) {
                 return false;
             }
        	 i++;
        }
		return i <= 1;
	}
	
	private boolean isAnotherChest(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).getBlock() != this) {
            return false;
        } else {
            return true;
        }
    }
	
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		 TileEntity tileentity = worldIn.getTileEntity(pos);
        if (worldIn.isRemote) {
            return true;
        } else {
            ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);

            if (ilockablecontainer != null) {
            		deadrising.proxy.OpenAirdropGUI(playerIn);
                	playerIn.addStat(StatList.CHEST_OPENED);
               }
            }
        return true;
    }
	
	@Nullable
    public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
        return this.getContainer(worldIn, pos, false);
    }
	
	@Nullable
	public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (!(tileentity instanceof TileEntityChest)) {
	    	return null;
	    } else {
	    	ILockableContainer ilockablecontainer = (TileEntityChest)tileentity;

	    	if (!allowBlocking && this.isBlocked(worldIn, pos)) {
	            return null;
	        } else {
	            return ilockablecontainer;
	          	}
	      	}
	 }
	
	private boolean isBlocked(World worldIn, BlockPos pos) {
		return this.isBelowSolidBlock(worldIn, pos);
	}
	 
	private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN);
	}
	 
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityAirdrop tileentity = (TileEntityAirdrop)worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAirdrop();
	}
	
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
	
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return false;
    }
}
