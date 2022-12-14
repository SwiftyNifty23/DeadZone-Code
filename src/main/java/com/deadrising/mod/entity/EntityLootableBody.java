package com.deadrising.mod.entity;

import com.deadrising.mod.deadrising;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class EntityLootableBody extends EntityLiving implements IInventory{


	public static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET, EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};

	final static byte VACUUM_TIMELIMIT = 20;
	final static int VACUUM_RADIUS = 3;
	
	private final NonNullList<ItemStack> mainInventory = NonNullList.withSize(6*7, ItemStack.EMPTY);
	private final Deque<ItemStack> auxInventory = new LinkedList<>();
	private byte vacuumTime = 0;
	private long deathTimestamp;
	private long decayTimestamp;

	private final AtomicReference<GameProfile> gpSwap = new AtomicReference<>(null) ; // for lazy-evaluation of player skins
	private int terminate = -1;
	private static final int DEATH_COUNTDOWN = 10;


	public EntityLootableBody(World w) {
		super(w);
		deathTimestamp = w.getTotalWorldTime();
		decayTimestamp = deathTimestamp;
		this.setAlwaysRenderNameTag(deadrising.displayNameTag);
		this.setSize(0.85F, 0.75F);

		this.isImmuneToFire = deadrising.completelyInvulnerable || (!deadrising.hurtByEnvironment);

	}

	public EntityLootableBody(EntityPlayer player){
		this(player.getEntityWorld());
		this.setPosition(player.posX, player.posY+0.5,  player.posZ);

		this.motionX = player.motionX;
		this.motionY = player.motionY+0.0784000015258789;
		this.motionZ = player.motionZ;

		//this.newPosX = posX; // no newPosX?
		//this.newPosY = posY; // no newPosY?
		//this.newPosZ = posZ; // no newPosZ?
		this.markDirty();
	}
	
	
	private void log(String format, Object... o){
		FMLLog.info("%s: %s",getClass().getSimpleName(), String.format(format, o));
	}

	private void log(Object o){
		FMLLog.info("%s: %s",getClass().getSimpleName(),String.valueOf(o));
	}

	private String oldName = null;
	@Override
	public void onEntityUpdate(){
		//log("position=%s; terminate=%s; time=%s; age=%s; deathTimestamp=%s; decayTimestamp=%s; Health=%s; isDead=%s" ,getPosition(),terminate,getEntityWorld().getTotalWorldTime(),getEntityWorld().getTotalWorldTime() - deathTimestamp, deathTimestamp, decayTimestamp, getHealth(), isDead);
		super.onEntityUpdate();
		final boolean isClient = getEntityWorld().isRemote;
		final boolean isServer = !isClient;

		if(isServer && terminate >= 0){
			// in death mode
			if(terminate == DEATH_COUNTDOWN) dropAllItems();
			// handle self-removal from entity list in world
			if(terminate > 0) terminate--;
			if(terminate == 0){
				// needs to be manually deleted for some reason
				getEntityWorld().removeEntity(this);
			}
			return;
		}
		if(isServer && terminate < 0 && (this.getHealth() <= 0 || this.isDead)){
			terminate = DEATH_COUNTDOWN;
		}

		// handle corpse name (and therefore appearance)
		String nameUpdate = this.getCustomNameTag();
		if(ObjectUtils.notEqual(oldName,nameUpdate)){
			oldName = nameUpdate;
			if(nameUpdate != null && nameUpdate.trim().length() > 0) {
				GameProfile gp = new GameProfile(null, nameUpdate);
				if((!deadrising.useLocalSkin) && isClient) gp = TileEntitySkull.updateGameprofile(gp);
				setGameProfile(gp);
				this.setCustomNameTag(nameUpdate);
			} else {
				setGameProfile(null);
				this.setCustomNameTag("");
			}
		}

		// handle decay time
		final long currentTime = getEntityWorld().getTotalWorldTime();
		if(isServer && deadrising.allowCorpseDecay){
			if(deadrising.decayOnlyWhenEmpty && isEmpty() == false){
				deathTimestamp = currentTime;
			}
			long age = currentTime - deathTimestamp;
			if(age > deadrising.corpseDecayTime){
				this.onKillCommand();
			}
		}

		// handle item decay
		if(isServer && deadrising.ticksPerItemDecay > 0){
			long age = currentTime - decayTimestamp;
			if(age > deadrising.ticksPerItemDecay){
				int decayAmount = (int)(age / deadrising.ticksPerItemDecay);
				decayTimestamp += decayAmount * deadrising.ticksPerItemDecay;
				decayItems(decayAmount);
			}
		}


		// move items from buffer to main inventory
		if(isServer && !auxInventory.isEmpty()) {
			for (int slot = this.getSizeInventory() - 1; slot >= EQUIPMENT_SLOTS.length; slot--) {
				if(!this.getStackInSlot(slot).isEmpty()){
					this.setInventorySlotContents(slot,auxInventory.pollFirst());
					this.markDirty();
					break;
				}
			}
		}

		// handle item vacuuming
		if(isServer && vacuumTime < VACUUM_TIMELIMIT){
			vacuumTime++;
			List<EntityItem> items = getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
					posX - VACUUM_RADIUS, posY - VACUUM_RADIUS, posZ - VACUUM_RADIUS,
					posX + VACUUM_RADIUS, posY + VACUUM_RADIUS, posZ + VACUUM_RADIUS));
			for(EntityItem item : items){
				ItemStack stack = item.getItem();
				this.addItem(stack);
			}
			for(EntityItem item : items){
				getEntityWorld().removeEntity(item);
			}
		}
		
		if(isServer && deadrising.corpseBuoyancy && vacuumTime >= VACUUM_TIMELIMIT && (this.inWater || this.isInLava())) {
			this.motionY += 0.025D;
			this.motionY *= 0.9;
		}
		
		if(isServer && deadrising.voidPlatform && this.posY < 0) {
			generateVoidPlatform();
			this.setPositionAndUpdate((int)this.posX, 1.5, (int)this.posZ);
		}

	}
	
	@Override
	public boolean handleWaterMovement() {
		if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, this)) 		{
			this.fallDistance = 0.0F;
			this.inWater = true;
			this.extinguish();
		} else {
			this.inWater = false;
		}
		
		return this.inWater;
	}
	
	private void generateVoidPlatform() {
		World world = this.world;
		BlockPos testPos = new BlockPos((int)this.posX, 0, (int)this.posZ);
		
		IBlockState platformBlockstate = getPlatformBlock(this.dimension);
		
		for(int j = -1; j <= 1; j++) {
			for(int k = -1; k <= 1; k++)	{
				if(world.isAirBlock(testPos.add(j,0,k)))
					world.setBlockState(testPos.add(j,0,k), platformBlockstate, 3);
			}
		}
	}
	
	private IBlockState getPlatformBlock(int i) {
		switch(deadrising.dimensionSpecificPlatform ? i : 0) {
			case -1:
				return Blocks.NETHERRACK.getDefaultState();
			case 1:
				return Blocks.END_STONE.getDefaultState();
			default:
				return Blocks.COBBLESTONE.getDefaultState();
		}
	}
	
	private void decayItems(int amount) {
		for(int slot = 0; slot < this.getSizeInventory(); slot++){
			this.setInventorySlotContents(slot,this.decayItem(this.getStackInSlot(slot), amount));
		}
		for(ItemStack item : auxInventory){
			decayItem(item, amount);
		}
	}

	private ItemStack decayItem(ItemStack item, int amount) {
		if(!item.isEmpty()
				&& item.getCount() == 1
				&& item.isItemStackDamageable()
				&& item.getItem().isDamageable()){
			int durabilityRemaining = item.getMaxDamage() - item.getItemDamage() - 1;
			if(durabilityRemaining > 0){
				item.damageItem(Math.min(durabilityRemaining,amount),this);
			}
		}
		return item;
	}

	public void addItem(ItemStack stack) {
		for(int slot = EQUIPMENT_SLOTS.length; slot < this.getSizeInventory(); slot++){
			stack = mergeItem(slot,stack);
			if(stack.isEmpty()) return;
		}
		auxInventory.addLast(stack);
	}


	public void initializeItems(NonNullList<ItemStack> inv) {
	
		for(int i = 0; i < inv.size(); i++) {
			if(i < mainInventory.size())
				mainInventory.set(i,inv.get(i));
			else
				auxInventory.addLast(inv.get(i));
		}

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
	protected void damageEntity(DamageSource src, float amount){
		String type = String.valueOf(src == null ? null : src.getDamageType()); // null protection
		if(src instanceof EntityDamageSource && src.getTrueSource() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)src.getTrueSource();
			ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
			if(!item.isEmpty()) {
				if (item.getItem() instanceof ItemSpade || item.getItem().getToolClasses(item).contains("shovel")) {
					this.onKillCommand();
				}
			}
		}
		if(type.equals(DamageSource.OUT_OF_WORLD.getDamageType())) {
			super.damageEntity(src,amount); // kill command and falling out of the world
			return;
		}

		if(type.equals(DamageSource.IN_WALL.getDamageType())){
			this.jumpOutOfWall();
		}

		// damage handling
		if(deadrising.completelyInvulnerable) return;

		if(src instanceof EntityDamageSource ){
			if(deadrising.hurtByAttacks) super.damageEntity(src,amount);
			return;
		}

		if(matchesAny(type, DamageSource.ANVIL, DamageSource.CACTUS, DamageSource.FALL, DamageSource.FALLING_BLOCK, DamageSource.FLY_INTO_WALL, DamageSource.IN_FIRE, DamageSource.LAVA, DamageSource.MAGIC, DamageSource.LIGHTNING_BOLT, DamageSource.ON_FIRE)){
			if(deadrising.hurtByEnvironment)super.damageEntity(src,amount);
			return;
		}

		if(deadrising.hurtByMisc){
			super.damageEntity(src,amount);
			return;
		}
	}



	public GameProfile getGameProfile(){
		return gpSwap.get();
	}

	public void setGameProfile(GameProfile gp){
		gpSwap.set(gp);
	}
	public void setUserName(String name){
		this.setCustomNameTag(name);
	}

	private long getDeathTimestamp() {
		return deathTimestamp;
	}
	private void setDeathTimestamp(long timestamp) {
		deathTimestamp = timestamp;
	}


	@Override
	protected void applyEntityAttributes() {
		// called during constructor of EntityLivingBase
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(deadrising.corpseHP);
	}

	public float getRotation(){
		return this.rotationYaw;
	}

	public void setRotation(float newRot){
		this.rotationYaw = newRot;
	}


	@Override
	protected boolean canDespawn() {
		return false;
	}

	public void jumpOutOfWall(){
		log("Escaping from wall at position %s",this.getPosition());
		BlockPos currentCoord = this.getPosition();
		// first try going out to the nearest adjacent block
		double[] vector = new double[3];
		vector[0] = currentCoord.getX()+0.5 - this.posX;
		vector[1] = 0;
		vector[2] = currentCoord.getZ()+0.5 - this.posZ;
		double normalizer = 1.0/Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
		vector[0] *= normalizer;
		vector[1] *= normalizer;
		vector[2] *= normalizer;
		IBlockState bs = world.getBlockState(new BlockPos(this.posX+vector[0], this.posY+vector[1], this.posZ+vector[2]));
		if(!(bs.getMaterial().blocksMovement())){
			this.setPosition(this.posX+vector[0], this.posY+vector[1], this.posZ+vector[2]);
			return;
		}

		// then try finding an open space in all adjacent blocks
		BlockPos n;
		n = currentCoord.up();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		n = currentCoord.north();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		n = currentCoord.east();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		n = currentCoord.south();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		n = currentCoord.west();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		n = currentCoord.down();
		if(!(world.getBlockState(n).getMaterial().blocksMovement())){
			this.setPosition(n.getX()+0.5,n.getY()+0.015625, n.getZ()+0.5);
			return;
		}
		// then if the above fails, move 2 blocks in a random direction
		vector[0] = world.rand.nextDouble() * 2;
		vector[1] = world.rand.nextDouble() * 2;
		vector[2] = world.rand.nextDouble() * 2;
		this.setPosition(this.posX+vector[0], this.posY+vector[1], this.posZ+vector[2]);
		log("jumped to %s",this.getPosition());
	}
	
	@Override
	public void onKillCommand() {
		if(terminate < 0) terminate = DEATH_COUNTDOWN;
		this.attackEntityFrom(DamageSource.OUT_OF_WORLD, this.getMaxHealth());
		this.markDirty();
	}


	@Override
	public int getTalkInterval() {
		return 1200;
	}
	@Override
	public void playLivingSound() {
		// do nothing
	}



	@Override
	protected int getExperiencePoints(final EntityPlayer p_getExperiencePoints_1_) {
		return 0;
	}


	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	@Override
	public boolean canBeSteered() {
		return false;
	}


	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return false; // leashing not allowed
	}


	@Override
	public boolean canPickUpLoot() {
		return false; // picking up items done in special way
	}

	@Override
	public void setCanPickUpLoot(final boolean p_setCanPickUpLoot_1_) {
		// do nothing
	}


	@Override public boolean canBreatheUnderwater() {
		return true;
	}

	////////// IInventory //////////
	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return EQUIPMENT_SLOTS.length + mainInventory.size();
	}

	/**
	 * Returns the stack in the given slot.
	 *
	 * @param index
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		if(index < EQUIPMENT_SLOTS.length){
			return super.getItemStackFromSlot(EQUIPMENT_SLOTS[index]);
		} else {
			return mainInventory.get(index - EQUIPMENT_SLOTS.length);
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 *
	 * @param index
	 * @param stack
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index < EQUIPMENT_SLOTS.length){
			super.setItemStackToSlot(EQUIPMENT_SLOTS[index], stack);
		} else {
			mainInventory.set(index - EQUIPMENT_SLOTS.length, stack);
		}
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 *
	 * @param index
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack i = this.getStackInSlot(index);
		this.setInventorySlotContents(index,ItemStack.EMPTY);
		return i;
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 *
	 * @param index
	 * @param count
	 */
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

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {
		// do nothing
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 *
	 * @param player
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand){
		if(player.getPosition().distanceSq(this.getPosition()) < 25) {
			FMLNetworkHandler.openGui(player, deadrising.getInstance(), 0, getEntityWorld(), getEntityId(), 0, 0);
			return true;
		}
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 *
	 * @param index
	 * @param stack
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index < 4){
			return stack.getItem().isValidArmor (stack, EQUIPMENT_SLOTS[index], this);
		} else {
			return true;
		}
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// do nothing
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for(int i = 0; i < EQUIPMENT_SLOTS.length; i++){
			this.setInventorySlotContents(i,ItemStack.EMPTY);
		}
	
		mainInventory.clear();
		auxInventory.clear();
	}

	////////// End of IInventory //////////

	public boolean isEmpty(){
		if(!auxInventory.isEmpty()) return false;
		for(int slot = 0; slot < this.getSizeInventory(); slot++){
			if(!this.getStackInSlot(slot).isEmpty()) return false;
		}
		return true;
	}

	private void dropAllItems(){
		for(EntityEquipmentSlot e : EQUIPMENT_SLOTS){
			ItemStack i = this.getItemStackFromSlot(e);
			if(!i.isEmpty())this.getEntityWorld().spawnEntity(new EntityItem(this.getEntityWorld(),posX,posY,posZ,
					i));
		}for(ItemStack i : mainInventory){
			if(!i.isEmpty())this.getEntityWorld().spawnEntity(new EntityItem(this.getEntityWorld(),posX,posY,posZ,i));
		}for(ItemStack i : auxInventory){
			if(!i.isEmpty())this.getEntityWorld().spawnEntity(new EntityItem(this.getEntityWorld(),posX,posY,posZ,i));
		}
		this.clear();
	}

	private static boolean matchesAny(String damageType, DamageSource... list){
		for(int i = 0; i < list.length; i++){
			if(damageType.equalsIgnoreCase(list[i].damageType)) return true;
		}
		return false;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
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

		if(!this.auxInventory.isEmpty()){
			final NBTTagList nbtauxtaglist = new NBTTagList();
			Iterator<ItemStack> iter = this.auxInventory.iterator();
			while (iter.hasNext()) {
				ItemStack i = iter.next();
				if(i.isEmpty()) continue;
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				i.writeToNBT(nbttagcompound1);
				nbtauxtaglist.appendTag(nbttagcompound1);
			}
			root.setTag("Aux", nbtauxtaglist);
		}
		if(vacuumTime < VACUUM_TIMELIMIT)root.setByte("Vac", vacuumTime);
		if(getGameProfile() != null){
			root.setString("Name", getGameProfile().getName());
		}
		root.setLong("DeathTime", this.getDeathTimestamp());
		root.setLong("DecayTime",this.decayTimestamp);
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
				this.auxInventory.addLast(new ItemStack(nbttaglist.getCompoundTagAt(i)));
			}
		}
		if(root.hasKey("Vac")){
			this.vacuumTime = root.getByte("Vac");
		} else {
			this.vacuumTime = VACUUM_TIMELIMIT;
		}
		if(root.hasKey("DeathTime")){
			this.setDeathTimestamp(root.getLong("DeathTime"));
		}
		if(root.hasKey("DecayTime")){
			this.decayTimestamp = root.getLong("DecayTime");
		}
		if (root.hasKey("Name")) {
			this.setUserName(root.getString("Name"));
		}
		this.setRotation(this.rotationYaw, 0);
	}

	public boolean useThinArms() {
		GameProfile p = this.getGameProfile();
		if(p != null){
			if(p.isLegacy()) return false;
			UUID uid = p.getId();
			if(uid != null){
				return (uid.hashCode() & 1) == 1;
			}
		}
		return false;
	}
}
