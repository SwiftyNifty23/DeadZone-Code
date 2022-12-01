package com.deadrising.mod.proxy;

import com.deadrising.mod.Reference;
import com.deadrising.mod.blocks.BlockBase;
import com.deadrising.mod.blocks.PropBase;
import com.deadrising.mod.blocks.props.CabnetBlock;
import com.deadrising.mod.blocks.props.CampfireBlock;
import com.deadrising.mod.blocks.props.ConcreteBarrier;
import com.deadrising.mod.blocks.props.CrateModel;
import com.deadrising.mod.blocks.props.FloorProp;
import com.deadrising.mod.blocks.props.LightProp;
import com.deadrising.mod.blocks.props.MilitartLootCrate;
import com.deadrising.mod.blocks.props.PinicTableBlock;
import com.deadrising.mod.blocks.props.SteelBeem;
import com.deadrising.mod.blocks.props.TableModel;
import com.deadrising.mod.blocks.props.TraficBarrier;
import com.deadrising.mod.common.EnumParticles;
import com.deadrising.mod.entity.EntityScent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

	private int entityId = 0;
	
    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event)
    {
    
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    
    public void spawnParticle(EnumParticles particle, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {

    }
	public void registerItemRender(Item item, int meta, String id) {}
	
	public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
	public void OpenAirdropGUI(EntityPlayer player) {}

	public void registerBlockRender(LightProp lightProp, int i, String string) {
		// TODO Auto-generated method stub
		
	}
	public void registerBlockRender(SteelBeem SteelBeem, int i, String string) {
		// TODO Auto-generated method stub
		
	}
	public void registerBlockRender(BlockBase blockprop, int i, String string) {
		// TODO Auto-generated method stub
		
	}
	public void registerBlockRender(PropBase blockBase, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(FloorProp floorProp, int i, String string) {
		// TODO Auto-generated method stub
		
	}


	public void registerBlockRender(CampfireBlock campfireBlock, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(PinicTableBlock pinicTableBlock, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(CabnetBlock cabnetBlock, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(TraficBarrier traficBarrier, int i, String string) {
		
		
		
	}

	public void registerBlockRender(TableModel tableModel, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(ConcreteBarrier concreteBarrier, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(MilitartLootCrate militartLootCrate, int i, String string) {
		// TODO Auto-generated method stub
		
	}

	public void registerBlockRender(CrateModel crateModel, int i, String string) {
		// TODO Auto-generated method stub
		
	}





	
	
}
