package net.kozibrodka.sdk.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.minecraft.entity.LivingEntity;
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
import java.util.Objects;

public class GrapplingPacket extends Packet implements ManagedPacket<GrapplingPacket> {

    public static final PacketType<GrapplingPacket> TYPE = PacketType.builder(true, true, GrapplingPacket::new).build();

    private int entityId;
    private String entityRoper;

    public GrapplingPacket() {
    }

    public GrapplingPacket(int id, String roper) {
        this.entityId = id;
        this.entityRoper = roper;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.entityRoper = stream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            stream.writeUTF(this.entityRoper);
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
        System.out.println("DOSZŁO");
        SdkEntityGrapplingHook hook = (SdkEntityGrapplingHook) ((ClientWorld)player.world).getEntity(this.entityId);
        if(hook != null){
            System.out.println("DOSZŁO2 + " + hook.world.getPlayer(this.entityRoper));
            hook.owner = hook.world.getPlayer(this.entityRoper);
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
    public @NotNull PacketType<GrapplingPacket> getType() {
        return TYPE;
    }
}
