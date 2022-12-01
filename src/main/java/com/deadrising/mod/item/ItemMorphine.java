package com.deadrising.mod.item;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.utils.IHasModel;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMorphine extends Item implements IHasModel{

	private int useTime;

	public ItemMorphine(String id, int useTime, int StackSize) {
		this.useTime = useTime;
		this.setUnlocalizedName(id);
		this.setRegistryName(id);
		this.setCreativeTab(deadrising.TabMedical);
	
    ModItems.ITEMS.add(this);
	}




	public void registerModels() 
	{
		deadrising.proxy.registerItemRender(this, 0, "inventory");
	
	}


	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			this.onUse(stack, world, player);
			player.removePotionEffect(MobEffects.SLOWNESS);
			player.addStat(StatList.getObjectUseStats(this));
			if (player instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
			
		}
			
		
	}

		stack.shrink(1);
		return stack;
	}

	protected void onUse(ItemStack stack, World world, EntityPlayer player) {
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return this.useTime;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (player.getActivePotionEffect(MobEffects.SLOWNESS) != null) {
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		} else {
			player.sendMessage((ITextComponent) new TextComponentString(TextFormatting.RED + "Your leg is not broken"));
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
		}
	}
}
