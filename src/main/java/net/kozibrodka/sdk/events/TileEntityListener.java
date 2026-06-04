package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityRope;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class TileEntityListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public static void registerTileEntities(BlockEntityRegisterEvent event) {
        event.register(Identifier.of(MOD_ID, "TileGrinder"), SdkTileEntityGrinder.class);
        event.register(Identifier.of(MOD_ID, "TileRope"), SdkTileEntityRope.class);
        event.register(Identifier.of(MOD_ID, "TilePlaque"), SdkTileEntityPlaque.class);
    }
}
