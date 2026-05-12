package net.kozibrodka.sdk.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.sdk.atv.SdkEntityLandVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

public class CarLoadPacket extends Packet implements ManagedPacket<CarLoadPacket> {

    public static final PacketType<CarLoadPacket> TYPE = PacketType.builder(true, true, CarLoadPacket::new).build();

    private int entityId;
    private String entityPass;
    private float entityYaw;
    private float entityPitch;

    public CarLoadPacket() {
    }

    public CarLoadPacket(int id) {
        this.entityId = id;
    }

    public CarLoadPacket(int id, String pass) {
        this.entityId = id;
        this.entityPass = pass;
    }

    public CarLoadPacket(int id, float ya, float pi) {
        this.entityId = id;
        this.entityYaw = ya;
        this.entityPitch = pi;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
//            this.entityPass = stream.readUTF();
            this.entityYaw = stream.readFloat();
            this.entityPitch = stream.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
//            stream.writeUTF(this.entityPass);
            stream.writeFloat(this.entityYaw);
            stream.writeFloat(this.entityPitch);
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
        Entity vehicleEntity = ((ClientWorld)player.world).getEntity(this.entityId);
        if(vehicleEntity instanceof SdkEntityLandVehicle landVeh){
            vehicleEntity.yaw = this.entityYaw;
            vehicleEntity.pitch = this.entityPitch;
//            vehicleEntity.pitch = 0;
//            landVeh.clientVelocityY = 10;
//            vehicleEntity.onGround = true;
//            System.out.println(vehicleEntity.x + "  " + vehicleEntity.z);
//            System.out.println(vehicleEntity.clie + "  " + landVeh.clientVelocityY);

        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        Entity vehicleEntity = ((ServerWorld)player.world).getEntity(this.entityId);

        if(vehicleEntity != null){
            PacketHelper.sendTo(player, new CarLoadPacket(vehicleEntity.id, vehicleEntity.yaw, vehicleEntity.pitch));
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<CarLoadPacket> getType() {
        return TYPE;
    }
}
