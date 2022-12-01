package com.deadrising.mod.tileentity;

import com.deadrising.mod.Reference;
import com.deadrising.mod.tileentity.container.ContainerAirdrop;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;

public class TileEntityAirdrop extends TileEntityLockableLoot implements ITickable 
{

	private NonNullList<ItemStack> lootContents = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack i : lootContents)
		{
			if(!i.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "tile.dr.airdrop.name";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		this.fillWithLoot(playerIn);
        return new ContainerAirdrop(playerInventory, this, playerIn);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.lootContents);
        }

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.lootContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.lootContents);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
	}
	
	@Override
	public String getGuiID() {
		return "" + Reference.AirdropGUIID;
	}
	
	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.lootContents;
	}

}
