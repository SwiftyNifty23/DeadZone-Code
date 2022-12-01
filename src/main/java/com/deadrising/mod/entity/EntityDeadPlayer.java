package com.deadrising.mod.entity;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.deadrising.mod.deadrising;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class EntityDeadPlayer extends EntityLiving implements IInventory
{
	
	public static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET, EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};

	private final NonNullList<ItemStack> playerinventory = NonNullList.withSize(6*7, ItemStack.EMPTY);
	private final Deque<ItemStack> auxInv = new LinkedList<>();
	private final AtomicReference<GameProfile> gpSwap = new AtomicReference<>(null) ;
	
	public EntityDeadPlayer(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}
	
	public void addItem(ItemStack stack)
	{
		for(int slot = EQUIPMENT_SLOTS.length; slot < this.getSizeInventory(); slot++){
			stack = mergeItem(slot,stack);
			if(stack.isEmpty()) return;
		}
		auxInv.addLast(stack);
	}
	
	public void setGameProfile(GameProfile profile)
	{
		gpSwap.set(profile);
	}
	
	public GameProfile getGameProfile()
	{
		return gpSwap.get();
	}
	
	public void setUsername(String name)
	{
		this.setCustomNameTag(name);
	}
	
	public ItemStack mergeItem(int slot, ItemStack stack) {
		if(stack.isEmpty()) return ItemStack.EMPTY;
		ItemStack current = this.getStackInSlot(slot);
		final int inventorySizeLimit = Math.min(stack.getMaxStackSize(),this.getInventoryStackLimit());
		if(current.isEmpty()){
			this.setInventorySlotContents(slot, stack);
			return ItemStack.EMPTY;
		} else if(current.getCount() < inventorySizeLimit
				&& ItemStack.areItemsEqual(stack,current)
				&& ItemStack.areItemStackTagsEqual(stack,current)){
			int delta = Math.min(stack.getCount(),inventorySizeLimit - current.getCount());
			current.grow(delta);
			stack.shrink(delta);
			this.setInventorySlotContents(slot,current);
			if(stack.getCount() <= 0) return ItemStack.EMPTY;
			return stack;
		}
		return stack;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override
	public void writeEntityToNBT(final NBTTagCompound root) {
		super.writeEntityToNBT(root);
		final NBTTagList equipmentListTag = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack item = this.getStackInSlot(i);
			if (!item.isEmpty()) {
				NBTTagCompound slotTag = new NBTTagCompound();
				slotTag.setByte("Slot", (byte)i);
				item.writeToNBT(slotTag);
				equipmentListTag.appendTag(slotTag);
			}
		}
		root.setTag("Equipment", equipmentListTag);

		if(!this.auxInv.isEmpty()){
			final NBTTagList nbtauxtaglist = new NBTTagList();
			Iterator<ItemStack> iter = this.auxInv.iterator();
			while (iter.hasNext()) {
				ItemStack i = iter.next();
				if(i.isEmpty()) continue;
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				i.writeToNBT(nbttagcompound1);
				nbtauxtaglist.appendTag(nbttagcompound1);
			}
			root.setTag("Aux", nbtauxtaglist);
		}
		if(getGameProfile() != null){
			root.setString("Name", getGameProfile().getName());
		}
	}


	@Override
	public void readEntityFromNBT(final NBTTagCompound root) {
		super.readEntityFromNBT(root);
		if (root.hasKey("Equipment", 9)) {
			final NBTTagList nbttaglist = root.getTagList("Equipment", 10);
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound slotTag = nbttaglist.getCompoundTagAt(i);
				int slot = slotTag.getByte("Slot");
				ItemStack item = new ItemStack(slotTag);
				this.setInventorySlotContents(slot,item);
			}
		}
		if(root.hasKey("Aux")){
			final NBTTagList nbttaglist = root.getTagList("Aux", 10);
			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				this.auxInv.addLast(new ItemStack(nbttaglist.getCompoundTagAt(i)));
			}
		}
		if (root.hasKey("Name")) {
			this.setUsername(root.getString("Name"));
		}
		this.setRotation(this.rotationYaw, 0);
	}
	
	@Override
	public boolean canPickUpLoot() {
		return false;
	}
	
	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return false;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSizeInventory() {
		return EQUIPMENT_SLOTS.length + playerinventory.size();
	}

	@Override
	public boolean isEmpty() {
		if(!auxInv.isEmpty()) return false;
		for(int slot = 0; slot < this.getSizeInventory(); slot++){
			if(!this.getStackInSlot(slot).isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(index < EQUIPMENT_SLOTS.length) return super.getItemStackFromSlot(EQUIPMENT_SLOTS[index]);
		else return playerinventory.get(index - EQUIPMENT_SLOTS.length);
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.getPosition().distanceSq(this.getPosition()) < 25)
		{
			FMLNetworkHandler.openGui(player, deadrising.instance, 0, world, getEntityId(), 0, 0);
		}
		return false;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack i = this.getStackInSlot(index);
		ItemStack result = i.splitStack(count);
		if (i.getCount() <= 0) {
			i = ItemStack.EMPTY;
		}
		this.setInventorySlotContents(index, i);
		return result;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack i = this.getStackInSlot(index);
		this.setInventorySlotContents(index, ItemStack.EMPTY);
		return i;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index < EQUIPMENT_SLOTS.length){
			super.setItemStackToSlot(EQUIPMENT_SLOTS[index], stack);
		} else {
			playerinventory.set(index - EQUIPMENT_SLOTS.length, stack);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index > 4) return stack.getItem().isValidArmor(stack, EQUIPMENT_SLOTS[index], this);
		else return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < EQUIPMENT_SLOTS.length; i++)
		{
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		
		auxInv.clear();
		playerinventory.clear();
	}
	
	public boolean useThinArms()
	{
		GameProfile p = this.getGameProfile();
		if(p != null)
		{
			if(p.isLegacy()) return false;
			UUID uid = p.getId();
			if(uid != null) return (uid.hashCode() & 1) == 1;
		}
		return false;
	}
	
}
