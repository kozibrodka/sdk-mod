package net.kozibrodka.sdk.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkEntityLandVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
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
    private String entityPass = "";
    private float entityYaw;
    private float entityPitch;
    private boolean entityGround;
    private int entityHealth;
    private int gunAID;
    private int gunBID;

    public CarLoadPacket() {
    }

    public CarLoadPacket(int id) {
        this.entityId = id;
    }

    public CarLoadPacket(int id, String pass) {
        this.entityId = id;
        this.entityPass = pass;
    }

    public CarLoadPacket(int id, float ya, float pi, boolean gr, int ht, int a, int b, String pass) {
        this.entityId = id;
        this.entityYaw = ya;
        this.entityPitch = pi;
        this.entityGround = gr;
        this.entityHealth = ht;
        this.gunAID = a;
        this.gunBID = b;
        this.entityPass = pass;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.entityPass = stream.readUTF();
            this.entityYaw = stream.readFloat();
            this.entityPitch = stream.readFloat();
            this.entityGround = stream.readBoolean();
            this.entityHealth = stream.readInt();
            this.gunAID = stream.readInt();
            this.gunBID = stream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            stream.writeUTF(this.entityPass);
            stream.writeFloat(this.entityYaw);
            stream.writeFloat(this.entityPitch);
            stream.writeBoolean(this.entityGround);
            stream.writeInt(this.entityHealth);
            stream.writeInt(this.gunAID);
            stream.writeInt(this.gunBID);
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
            landVeh.setOnGround(this.entityGround);
            landVeh.getDataTracker().set(29, (byte) this.entityHealth);
            landVeh.setClientYaw(this.entityYaw);
            landVeh.getDataTracker().set(18, this.gunAID);
            landVeh.getDataTracker().set(19, this.gunBID);

            PlayerEntity jokey1 = player.world.getPlayer(this.entityPass);
                if(jokey1 != null){
                    jokey1.setVehicle(landVeh);
                }
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        Entity vehicleEntity = ((ServerWorld)player.world).getEntity(this.entityId);

        if(vehicleEntity instanceof SdkEntityAtv landVeh){
            String sPass = "";
            if(vehicleEntity.passenger instanceof PlayerEntity plPass){
                sPass = plPass.name;
            }

            PacketHelper.sendTo(player, new CarLoadPacket(vehicleEntity.id, vehicleEntity.yaw, vehicleEntity.pitch, vehicleEntity.onGround, landVeh.health, landVeh.getDataTracker().getInt(18), landVeh.getDataTracker().getInt(19), sPass));
//            PacketHelper.sendTo(player, new CarLoadPacket(vehicleEntity.id, vehicleEntity.yaw, vehicleEntity.pitch, vehicleEntity.onGround, landVeh.health, landVeh.gunA.itemId, landVeh.gunB.itemId, sPass));

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
