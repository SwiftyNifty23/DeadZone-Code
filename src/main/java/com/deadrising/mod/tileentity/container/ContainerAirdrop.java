package com.deadrising.mod.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAirdrop extends Container
{

	private final IInventory chestInv;
	private final int rowsNum;
	
	
	public ContainerAirdrop(IInventory playerInv,IInventory chestInv, EntityPlayer player) {
		this.rowsNum = 9;
		this.chestInv = chestInv;
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(chestInv, i, 6 + i * 18, 70));
		}
		
		int i = (9 - 4) * 18;

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlotToContainer(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(playerInv, i1, 8 + i1 * 18, 161 + i));
        }
		
		chestInv.openInventory(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.rowsNum) {
                if (!this.mergeItemStack(itemstack1, this.rowsNum, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.rowsNum, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
    
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        this.chestInv.closeInventory(playerIn);
    }
    
    public IInventory getInventoryAirdrop() {
        return this.chestInv;
    }

}
