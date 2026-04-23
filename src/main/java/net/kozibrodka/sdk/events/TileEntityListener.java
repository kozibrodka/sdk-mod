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
        event.register(SdkTileEntityGrinder.class, String.valueOf(Identifier.of(MOD_ID, "TileGrinder")));
        event.register(SdkTileEntityRope.class, String.valueOf(Identifier.of(MOD_ID, "TileRope")));
        event.register(SdkTileEntityPlaque.class, String.valueOf(Identifier.of(MOD_ID, "TilePlaque")));
//TODO: Custom render for tile entity SdkTileEntityRendererPlaque
    }
}
