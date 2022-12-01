package com.deadrising.mod.init;

import java.util.ArrayList;
import java.util.List;

import com.deadrising.mod.deadrising;
import com.deadrising.mod.blocks.BlockBase;
import com.deadrising.mod.blocks.PropBase;
import com.deadrising.mod.blocks.blocks.Barbedwire;
import com.deadrising.mod.blocks.props.CabnetBlock;
import com.deadrising.mod.blocks.props.CampfireBlock;
import com.deadrising.mod.blocks.props.ConcreteBarrier;
import com.deadrising.mod.blocks.props.CrateModel;
import com.deadrising.mod.blocks.props.FloorProp;
import com.deadrising.mod.blocks.props.LightProp;
import com.deadrising.mod.blocks.props.MilitartLootCrate;
import com.deadrising.mod.blocks.props.PinicTableBlock;
import com.deadrising.mod.blocks.props.TableModel;
import com.deadrising.mod.blocks.props.TraficBarrier;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;


@Mod.EventBusSubscriber(modid = "deadrising")
public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	//Blocks
	
	public static final Block Barbedwire = (Block)new Barbedwire("block_barbed", Material.WEB, deadrising.TabBlocks);
	
	
	
	//Props
	  
	  public static final Block bluehazard = (Block)new BlockBase("bluehazard", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block brownbrickbroken = (Block)new BlockBase("brownbrickbroken", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block brownbrickcracked = (Block)new BlockBase("brownbrickcracked", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block brownbricknormal = (Block)new BlockBase("brownbricknormal", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block darkredbrickbroken = (Block)new BlockBase("darkredbrickbroken", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block darkredbrickcracked = (Block)new BlockBase("darkredbrickcracked", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block darkredbricknormal = (Block)new BlockBase("darkredbricknormal", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block redblackhazard = (Block)new BlockBase("redblackhazard", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block redbrickbroken = (Block)new BlockBase("redbrickbroken", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block redbrickcracked = (Block)new BlockBase("redbrickcracked", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block redbricknormal = (Block)new BlockBase("redbricknormal", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block redwhitehazard = (Block)new BlockBase("redwhitehazard", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block yellowhazard = (Block)new BlockBase("yellowhazard", Material.ROCK, deadrising.TabBlocks);
	  
	  public static final Block StoreShelf = (Block)new PropBase("storeshelf", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block Box_grapes = (Block)new PropBase("box_grapes", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block Box_Oranges = (Block)new PropBase("box_oranges", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block Box_peaches = (Block)new PropBase("box_peaches", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block Box_plums = (Block)new PropBase("box_plums", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block Box_tangerines = (Block)new PropBase("box_tangerines", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block FireWood = (Block)new PropBase("firewood", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block body_bag_black = (Block)new PropBase("body_bag_black", Material.IRON, deadrising.TabProps);
	  
	  public static final Block body_bag_white = (Block)new PropBase("body_bag_white", Material.IRON, deadrising.TabProps);
	  
	  public static final Block body_bag_yellow = (Block)new PropBase("body_bag_yellow", Material.IRON, deadrising.TabProps);
	  
	  public static final Block firepit = (Block)new PropBase("firepit", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block military_crate = (Block)new PropBase("military_crate", Material.IRON, deadrising.TabProps);
	  
	  public static final Block ip_gas_cylinder = (Block)new PropBase("lp_gas_cylinder", Material.IRON, deadrising.TabProps);
	  
	  public static final Block camppicnictable = (Block)new PropBase("camppicnictable", Material.WOOD, deadrising.TabProps);
	  
	  public static final Block picnictable = (Block)new PropBase("picnictable", Material.IRON, deadrising.TabProps);
	  
	  public static final Block logs = (Block)new PropBase("logs", Material.IRON, deadrising.TabProps);
	  
	  //public static final Block pallet = (Block)new PropBase("pallet", Material.IRON, deadrising.TabProps);
	  
	  //public static final Block stackedpallet = (Block)new PropBase("stackedpallet", Material.IRON, deadrising.TabProps);
	  
	  //public static final Block steelbeem = (Block)new PropBase("steelbeem", Material.IRON, deadrising.TabProps);
	  
	  public static final Block trash = (Block)new PropBase("trash", Material.ROCK, deadrising.TabProps);
	  
	  public static final Block trash2 = (Block)new PropBase("trash2", Material.ROCK, deadrising.TabProps);
	  
	  public static final Block trash3 = (Block)new PropBase("trash3", Material.ROCK, deadrising.TabProps);
	
	
	
}
