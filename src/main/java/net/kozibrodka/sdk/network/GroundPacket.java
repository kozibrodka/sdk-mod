package net.kozibrodka.sdk.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk_api.utils.SdkVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.ClientWorld;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GroundPacket extends Packet implements ManagedPacket<GroundPacket> {

    public static final PacketType<GroundPacket> TYPE = PacketType.builder(true, true, GroundPacket::new).build();

    private boolean ground;

    public GroundPacket() {
    }

    public GroundPacket(boolean id) {
        this.ground = id;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.ground = stream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeBoolean(this.ground);
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
        if(player.vehicle != null){
//            player.vehicle.onGround = this.ground;
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<GroundPacket> getType() {
        return TYPE;
    }
}
