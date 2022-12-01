package com.deadrising.mod.utils;

import com.deadrising.mod.Reference;
import com.deadrising.mod.client.gui.GuiAirdrop;
import com.deadrising.mod.tileentity.container.ContainerAirdrop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		 if(ID == Reference.AirdropGUIID) return new ContainerAirdrop(player.inventory, (IInventory)world.getTileEntity(new BlockPos(x,y,z)), player);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		 if(ID == Reference.AirdropGUIID) return new GuiAirdrop(player.inventory, (IInventory)world.getTileEntity(new BlockPos(x,y,z)), 9);

		return null;
	}
   

}
