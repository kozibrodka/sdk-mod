package net.kozibrodka.sdk.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.sdk.entitySentry.SdkEntitySentry;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.ClientWorld;
import net.minecraft.world.ServerWorld;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AskPacket extends Packet implements ManagedPacket<AskPacket> {

    public static final PacketType<AskPacket> TYPE = PacketType.builder(true, true, AskPacket::new).build();

    private int itemId;
    private int itemMeta;
    private int x;
    private int y;
    private int z;

    public AskPacket() {
    }

    public AskPacket(int id, int meta, int a, int b, int c) {
        this.itemId = id;
        this.itemMeta = meta;
        this.x = a;
        this.y = b;
        this.z = c;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.itemId = stream.readInt();
            this.itemMeta = stream.readInt();
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.itemId);
            stream.writeInt(this.itemMeta);
            stream.writeInt(this.x);
            stream.writeInt(this.y);
            stream.writeInt(this.z);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apply(NetworkHandler arg) {
        switch (FabricLoader.INSTANCE.getEnvironmentType()) {
            case CLIENT -> handleClient(arg);
            case SERVER -> handleServer(arg);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {
        ClientPlayerEntity player = (ClientPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }
        SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)player.world.getBlockEntity(x,y,z);
        if(sdktileentityplaque != null){
            if(itemId == 0){
                sdktileentityplaque.itemStack = null;
            }else{
                sdktileentityplaque.itemStack = new ItemStack(itemId,1,itemMeta);
            }
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)player.world.getBlockEntity(x,y,z);
        if(sdktileentityplaque != null){
            if(sdktileentityplaque.itemStack == null){
                PacketHelper.sendTo(player, new AskPacket(0, 0, x,y,z));
            }else{
                PacketHelper.sendTo(player, new AskPacket(sdktileentityplaque.itemStack.itemId, sdktileentityplaque.itemStack.getDamage(), x,y,z));
            }
        }

    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public @NotNull PacketType<AskPacket> getType() {
        return TYPE;
    }
}
