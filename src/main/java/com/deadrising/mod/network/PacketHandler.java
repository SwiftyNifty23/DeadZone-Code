package com.deadrising.mod.network;


import com.deadrising.mod.network.message.PacketSyncEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("deadrising");
  
  private static int ID = -1;
  
  public static void initialize() {
    registerClientPacket((Class)PacketSyncEntity.Handler.class, PacketSyncEntity.class);
  }
  
  public static void sendToClient(IMessage packet, EntityPlayerMP player) {
    INSTANCE.sendTo(packet, player);
  }
  
  public static void sendToAllClients(IMessage packet) {
    INSTANCE.sendToAll(packet);
  }
  
  public static void sendToClientsAround(IMessage packet, NetworkRegistry.TargetPoint target) {
    INSTANCE.sendToAllAround(packet, target);
  }
  
  public static void sendToAllTracking(IMessage packet, Entity entity) {
    INSTANCE.sendToAllTracking(packet, entity);
  }
  
  public static void sendToClientsAround(IMessage packet, int dimension, BlockPos pos, double range) {
      INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, pos.getX(), pos.getY(), pos.getZ(), range));
  }

  public static void sendToClientsAround(IMessage packet, int dimension, double x, double y, double z, double range) {
      INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
  }

  public static void sendToClientsAround(IMessage packet, EntityPlayerMP player, double range) {
      INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, range));
  }
  
  public static void sendToDimension(IMessage packet, int dimension) {
    INSTANCE.sendToDimension(packet, dimension);
  }
  
  public static void sendToDimension(IMessage packet, EntityPlayerMP player) {
    INSTANCE.sendToDimension(packet, player.dimension);
  }
  
  public static void sendToServer(IMessage packet) {
    INSTANCE.sendToServer(packet);
  }
  
  private static <REQ extends IMessage, REPLY extends IMessage> void registerClientPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> packet) {
    INSTANCE.registerMessage(handler, packet, nextID(), Side.CLIENT);
  }
  
  private static <REQ extends IMessage, REPLY extends IMessage> void registerServerPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> packet) {
    INSTANCE.registerMessage(handler, packet, nextID(), Side.SERVER);
  }
  
  private static int nextID() {
    return ID++;
  }
}
