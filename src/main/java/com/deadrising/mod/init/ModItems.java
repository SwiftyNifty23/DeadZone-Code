 package com.deadrising.mod.init;

import java.util.ArrayList;
import java.util.List;

import com.deadrising.mod.deadrising;

import com.deadrising.mod.item.FoodBase;
import com.deadrising.mod.item.ItemBase;
import com.deadrising.mod.item.ItemCure;
import com.deadrising.mod.item.ItemExplodeable;
import com.deadrising.mod.item.ItemGears;
import com.deadrising.mod.item.ItemHeal;
import com.deadrising.mod.item.ItemHealCure;
import com.deadrising.mod.item.ItemMedical;
import com.deadrising.mod.item.ItemMelee;
import com.deadrising.mod.item.ItemMorphine;

import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "deadrising")
public class ModItems
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();

	//Melee Material
	  public static final Item.ToolMaterial MATERIAL_Sickle = EnumHelper.addToolMaterial("MATERIAL_BaseballBatNailed", 0, 0, 0.0F, 1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_Fireaxe = EnumHelper.addToolMaterial("MATERIAL_Fireaxe", 0, 0, 0.0F, 2.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_Sledge = EnumHelper.addToolMaterial("MATERIAL_Sledge", 0, 0, 0.0F, -2.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_Machete = EnumHelper.addToolMaterial("MATERIAL_Machete", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_baseballbat = EnumHelper.addToolMaterial("MATERIAL_baseballbat", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_baseballbat_nailed = EnumHelper.addToolMaterial("MATERIAL_baseballbat_nailed", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_Shovel = EnumHelper.addToolMaterial("MATERIAL_Shovel", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_vikings_axe = EnumHelper.addToolMaterial("MATERIAL_vikings_axe", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_rench = EnumHelper.addToolMaterial("MATERIAL_rench", 0, 0, 0.0F, -1.0F, 0);
	  
	  public static final Item.ToolMaterial MATERIAL_lead_pipe = EnumHelper.addToolMaterial("MATERIAL_lead_pipe", 0, 0, 0.0F, -1.0F, 0);

	  
	  //Medical
	  public static final Item milifak = (Item)new ItemHealCure("military_first_aid_kit", 8, 10.0F, 1, deadrising.TabMedical);
	  
	  public static final Item fak = (Item)new ItemHeal("first_aid_kit", 8, 10.0F, 1, deadrising.TabMedical);
	  
	  public static final Item milibandage = (Item)new ItemHeal("militarybandage", 4, 4.0F, 6, deadrising.TabMedical);
	  
	  public static final Item bandage = (Item)new ItemHeal("bandage", 4, 4.0F, 6, deadrising.TabMedical);
	  
	  public static final Item bloodbag = (Item)new ItemHeal("bloodbag", 2, 2.0F, 6, deadrising.TabMedical);
	  
	  public static final Item painkillers_bottle = (Item)new ItemHeal("painkillers_bottle", 3, 3.0F, 6, deadrising.TabMedical);
	  
	  public static final Item painkillers_box = (Item)new ItemHeal("painkillers_box", 3, 3.0F, 6, deadrising.TabMedical);
	  
	  public static final Item cure_syringe = (Item)new ItemCure("cure_syringe", 7, 6);
	  
	  public static final Item morphine = (Item)new ItemMorphine("morphine", 7, 6);
	  
	  public static final Item empty_syringe = (Item)new ItemMedical("empty_syringe");
	  
	  public static final Item dirty_rag = (Item)new ItemMedical("dirty_rag");
	  
	  public static final Item antibiotics = (Item)new ItemHealCure("antibiotics", 3, 1.0F, 6, deadrising.TabMedical);
	  
	  public static final Item aspirine = (Item)new ItemHealCure("aspirine", 3, 1.0F, 6, deadrising.TabMedical);
	  
	  public static final Item clean_rag = (Item)new ItemHeal("clean_rag", 4, 4.0F, 6, deadrising.TabMedical);
	  
	  
	  //Melee
	  public static final Item FIREAXE = (Item)new ItemMelee(MATERIAL_Fireaxe, "item_fire_axe", 1, deadrising.TabMelee);
	  
	  public static final Item SICKLE = (Item)new ItemMelee(MATERIAL_Sickle, "item_sickle", 1, deadrising.TabMelee);
	  
	  public static final Item SLEDGE = (Item)new ItemMelee(MATERIAL_Sledge, "item_sledge_hammer", 1, deadrising.TabMelee);
	  
	  public static final Item MACHETE = (Item)new ItemMelee(MATERIAL_Machete, "item_machete", 1, deadrising.TabMelee);
	  
	  public static final Item BASEBALLBAT = (Item)new ItemMelee(MATERIAL_baseballbat, "item_bat", 1, deadrising.TabMelee);
	  
	  public static final Item BASEBALLBAT_NAILED = (Item)new ItemMelee(MATERIAL_baseballbat_nailed, "item_bat_nailed", 1, deadrising.TabMelee);
	  
	  public static final Item SHOVEL = (Item)new ItemMelee(MATERIAL_Shovel, "item_shovel", 1, deadrising.TabMelee);
	  
	  public static final Item LEAD_PIPE = (Item)new ItemMelee(MATERIAL_lead_pipe, "item_lead_pipe", 1, deadrising.TabMelee);
	  
	  public static final Item RENCH = (Item)new ItemMelee(MATERIAL_rench, "item_rench", 1, deadrising.TabMelee);
	  
	  public static final Item VIKINGS_AXE = (Item)new ItemMelee(MATERIAL_vikings_axe, "item_vikings_axe", 1, deadrising.TabMelee);
	  
	  
	 // public static final ItemExplodeable GRENADE = new ItemExplodeable("grenade", 80, ItemExplodeable.Helper::onFragRemoved);
	  
	 // public static final ItemExplodeable SMOKE = (new ItemExplodeable("smoke", 80, ItemExplodeable.Helper::onSmokeRemoved)).addAditionalDescription(new String[] { "Effect duration: 20s", TextFormatting.RED + "Water will cancel the effect!" });
	  
	  //public static final ItemExplodeable MOLOTOV = (new ItemExplodeable("molotov", -1, ItemExplodeable.Helper::onMolotovRemoved)).addAditionalDescription(new String[] { "Effect duration: 10s", TextFormatting.RED + "Water will cancel the effect!" });
	  
	 
	  //Items
	  public static final Item MONEY = (Item)new ItemGears("money");
	  
	  public static final Item TAPE = (Item)new ItemBase("tape");
	  
	  public static final Item OAK_WOOD = (Item)new ItemBase("oak_wood");
	  
	  public static final Item BIRCH_WOOD = (Item)new ItemBase("birch_wood");
	  
	  public static final Item FOIL = (Item)new ItemBase("foil");
	  
	  //Items
	  public static final Item rice = (Item)(new FoodBase("rice", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item applegreen = (Item)(new FoodBase("applegreen", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item banana = (Item)(new FoodBase("banana", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item beans = (Item)(new FoodBase("beans", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item purpleberrys = (Item)(new FoodBase("purpleberrys", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item orangeberrys = (Item)(new FoodBase("orangeberrys", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item burger = (Item)(new FoodBase("burger", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item cakepiece = (Item)(new FoodBase("cakepiece", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item cereals = (Item)(new FoodBase("cereals", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item cherrys = (Item)(new FoodBase("cherrys", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item chips = (Item)(new FoodBase("chips", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item chocolate = (Item)(new FoodBase("chocolate", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item coffebag = (Item)(new FoodBase("coffebag", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item cupcake = (Item)(new FoodBase("cupcake", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item dogfood = (Item)(new FoodBase("dogfood", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item fish_in_souse = (Item)(new FoodBase("fish_in_souse", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item jerky = (Item)(new FoodBase("jerky", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item mandarine = (Item)(new FoodBase("mandarine", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item mauo = (Item)(new FoodBase("mauo", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item military_mre = (Item)(new FoodBase("military_mre", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item mre = (Item)(new FoodBase("mre", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item orange = (Item)(new FoodBase("orange", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item peanut = (Item)(new FoodBase("peanut", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item pear = (Item)(new FoodBase("pear", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item pepper = (Item)(new FoodBase("pepper", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item pomjuice = (Item)(new FoodBase("pomjuice", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item pumkinpie = (Item)(new FoodBase("pumkinpie", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item rice_pack = (Item)(new FoodBase("rice_pack", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item sandwich = (Item)(new FoodBase("sandwich", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item sausage = (Item)(new FoodBase("sausage", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item suger_pack = (Item)(new FoodBase("suger_pack", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
	  public static final Item teabag = (Item)(new FoodBase("teabag", 5, 2.4F, false, 1, deadrising.TabFood)).addAditionalDescription(new String[] { TextFormatting.GRAY + "Feeds: 5" });
	  
}