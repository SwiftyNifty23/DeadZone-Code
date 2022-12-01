package com.deadrising.mod.utils.handlers;

import com.deadrising.mod.Reference;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;

@Config(modid=Reference.MOD_ID)
public class ConfigHandler 
{
	
	public static Client ClientSide = new Client();
    public static Server ServerSide = new Server();
	
    public static class Client {
        @Config.Ignore
        public ItemStack lastMainItem = ItemStack.EMPTY;
        @Config.Ignore
        public ItemStack HELMET = ItemStack.EMPTY;
        @Config.Ignore
        public ItemStack CHESTPLATE = ItemStack.EMPTY;
        @Config.Ignore
        public ItemStack LEGGINGS = ItemStack.EMPTY;
        @Config.Ignore
        public ItemStack BOOTS = ItemStack.EMPTY;
        
        public int Deaths;
        public int ZombieKills;
        public int PlayerKills;
    }

    public static class Server {
    }
}
