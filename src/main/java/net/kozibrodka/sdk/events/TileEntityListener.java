package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityRope;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class TileEntityListener {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    private static void registerTileEntities(TileEntityRegisterEvent event) {
        event.register(SdkTileEntityGrinder.class, String.valueOf(Identifier.of(MOD_ID, "SdkTileEntityGrinder")));
        event.register(SdkTileEntityRope.class, String.valueOf(Identifier.of(MOD_ID, "SdkTileEntityRope")));
        event.register(SdkTileEntityPlaque.class, String.valueOf(Identifier.of(MOD_ID, "SdkTileEntityPlaque")));
//TODO: Custom render for tile entity SdkTileEntityRendererPlaque
    }
}
