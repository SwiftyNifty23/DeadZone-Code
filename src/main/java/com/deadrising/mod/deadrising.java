package com.deadrising.mod;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.EntitySpawnHandler;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.deadrising.mod.client.tabs.TabFood;
import com.deadrising.mod.client.tabs.TabItems;
import com.deadrising.mod.client.tabs.TabProps;
import com.deadrising.mod.client.gui.GuiHealthbar;
import com.deadrising.mod.client.tabs.TabBlocks;
import com.deadrising.mod.client.tabs.TabMedical;
import com.deadrising.mod.client.tabs.TabMelee;
import com.deadrising.mod.common.CommonEvents;
import com.deadrising.mod.entity.EntityCrawler;
import com.deadrising.mod.entity.EntityDRRunner;
import com.deadrising.mod.entity.EntityDRZombie;
import com.deadrising.mod.entity.EntityLootableBody;
import com.deadrising.mod.events.EntityFallEvent;
import com.deadrising.mod.events.PlayerDeathEventHandler;
import com.deadrising.mod.graphics.GUIHandler;
import com.deadrising.mod.init.ModBlocks;
import com.deadrising.mod.init.ModItems;
import com.deadrising.mod.init.ModSounds;
import com.deadrising.mod.network.PacketHandler;
import com.deadrising.mod.potions.PotionEffectRegistry;
import com.deadrising.mod.potions.ZombieVirus;
import com.deadrising.mod.proxy.CommonProxy;
import com.deadrising.mod.proxy.ServerProxy;
import com.deadrising.mod.tileentity.TileEntityCrystal;
import com.deadrising.mod.utils.events.MainEvents;
import com.deadrising.mod.utils.handlers.RegistryHandler;
import com.deadrising.mod.utils.handlers.RenderHandler;
import com.deadrising.mod.utils.handlers.SoundHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;




@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = Reference.MOD_COMPATIBILITY)
public class deadrising {
	
	
	public static final Potion Infection = new ZombieVirus();
	 
	public static boolean displayNameTag = true;
    public static boolean addBonesToCorpse = true;
    public static boolean useLocalSkin = false;
	public static long ticksPerItemDecay = 10 * 60 * 20; // -1 to disable
	public static boolean hurtByEnvironment = false;
	public static boolean hurtByAttacks = true;
	public static boolean hurtByMisc = false;
	public static boolean completelyInvulnerable = false;
	public static float corpseHP = 2;
	
    public static boolean isLegsBroken = false;
    
    public static boolean allowCorpseDecay = true;
    public static boolean decayOnlyWhenEmpty = false;
    public static long corpseDecayTime = 3600*20; // in game ticks
	
	public static boolean corpseBuoyancy = false;
	public static boolean voidPlatform= false;
	public static boolean dimensionSpecificPlatform = false;
	
	public static final CreativeTabs TabMelee = new TabMelee("tabmelee");
	public static final CreativeTabs TabMedical = new TabMedical("tabmedical");
	public static final CreativeTabs TabFood = new TabFood("tabfood");
	public static final CreativeTabs TabBlocks = new TabBlocks("tabblocks");
	public static final CreativeTabs TabProps = new TabProps("tabprops");
	public static final CreativeTabs TabItems = new TabItems("tabitems");
	
    public static final String MOD_ID = "deadzone";

	@Mod.Instance
	public static deadrising instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static Logger logger;

	@Mod.EventHandler
	  public void preInit(FMLPreInitializationEvent event) {
	    logger = event.getModLog();
	    proxy.preInit(event);
	    instance = this;
	    MinecraftForge.EVENT_BUS.register(new CommonEvents());
	    MinecraftForge.EVENT_BUS.register(new SoundHandler());
		MinecraftForge.EVENT_BUS.register(new RenderHandler());
	    PacketHandler.initialize();
	    RegistryHandler.preInit();
	    ForgeRegistries.POTIONS.register(Infection);
	    PotionEffectRegistry.registerPotionTypes();
	    MinecraftForge.EVENT_BUS.register(this);
	  }

    


	
	@SideOnly(Side.CLIENT)
	@EventHandler
	public static void preinitOne(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistriesOne();
	}
	


	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		
		RegistryHandler.init();
		MinecraftForge.EVENT_BUS.register(new MainEvents());
		// register entities
		registerItemRenders();
		
		registerEntity(EntityLootableBody.class);
		MinecraftForge.EVENT_BUS.register(new PlayerDeathEventHandler());
		MinecraftForge.EVENT_BUS.register(new EntityFallEvent());
		NetworkRegistry.INSTANCE.registerGuiHandler( deadrising.getInstance(), GUIHandler.getInstance());
		
		
		registerItemRenders();
		
 	
		proxy.init(event);

	}

	private int parseTimeInSeconds(String time) {
		String[] component = time.split(":");
		int hr = 0,min=0,sec=0;
		if(component.length > 0)hr = Integer.parseInt(component[0].trim());
		if(component.length > 1)min = Integer.parseInt(component[1].trim());
		if(component.length > 2)sec = Integer.parseInt(component[2].trim());
		return 3600 * hr + 60 * min + sec;
	}


	private int entityIndex = 0;
	private void registerEntity(Class<? extends Entity> entityClass){
		String idName = "Corpse";
 		EntityRegistry.registerModEntity(new ResourceLocation("lootablebodies",idName), entityClass, idName, entityIndex++/*mod-specific entity id*/, this, 32/*trackingRange*/, 1/*updateFrequency*/, true/*sendsVelocityUpdates*/);
 		
	}
    
    private void registerItemRenders() {
    	// client-side only
    	if(proxy instanceof ServerProxy) return;
    //	registerItemRender(wandGeneric,OrdinaryWand.itemName);
	}


	

	public static deadrising getInstance(){
		return instance;
	}
	
	
	private static boolean or(boolean... bools){
		for(int i = 0; i < bools.length; i++){
			if(bools[i] == true) return true;
		}
		return false;
	}
	
	private static boolean and(boolean... bools){
		for(int i = 0; i < bools.length; i++){
			if(bools[i] == false) return false;
		}
		return true;
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		RegistryHandler.postInit();
		
		//Handle Spawns
		Biome[] spawnBiomes = getAllSpawnBiomes();
		
		int prob = 1;
		int maxSpawn = 1;
		int minSpawn = 0;
		
		int Crawlerprob = 1;
		int CrawlermaxSpawn = 1;
		int CrawlerminSpawn = 0;
		
		EntityRegistry.addSpawn(EntityDRZombie.class, prob, minSpawn, maxSpawn, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.addSpawn(EntityCrawler.class, prob, CrawlerminSpawn, CrawlermaxSpawn, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.addSpawn(EntityDRRunner.class, prob, CrawlerminSpawn, CrawlermaxSpawn, EnumCreatureType.MONSTER, spawnBiomes);
		
		EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityEnderman.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityCaveSpider.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityWitch.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityZombieVillager.class, EnumCreatureType.MONSTER, spawnBiomes);
		EntityRegistry.removeSpawn(EntityHorse.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntitySheep.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntityCow.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntityDonkey.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntityPig.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntityChicken.class, EnumCreatureType.CREATURE, spawnBiomes);
		EntityRegistry.removeSpawn(EntitySlime.class, EnumCreatureType.MONSTER, spawnBiomes);
		//MinecraftForge.EVENT_BUS.register(new GuiHealthbar());
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		
	}

	public static Logger logger() {
		return logger;
	}

	private Biome[] getAllSpawnBiomes() {
        LinkedList<Biome> list = new LinkedList<>();
        Collection<Biome> biomes = ForgeRegistries.BIOMES.getValuesCollection();
        for (Biome bgb : biomes) {
            if (bgb instanceof BiomeVoid) {
                continue;
            }
            if (bgb instanceof BiomeEnd) {
                continue;
            }
            if (bgb instanceof BiomeHell) {
                continue;
            }
            if (!list.contains(bgb)) {
                list.add(bgb);
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                    info("  >>> getAllSpawnBiomes: " + bgb.getBiomeName());
                }
            }
        }
        return list.toArray(new Biome[0]);
    }
	
	public void info(String s)
    {
        logger.info(s);
    }
	
}
