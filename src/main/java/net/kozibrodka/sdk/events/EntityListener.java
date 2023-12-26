package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Null;

public class EntityListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    private static void registerEntities(EntityRegister event) {
        event.register(SdkEntityAtv.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityAtv")));
        event.register(SdkEntityGrapplingHook.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityGrapplingHook")));
        event.register(SdkEntityNukePrimed.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityNukePrimed")));

//        event.register(SdkEntityBulletCasing.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletCasing")));
//        event.register(SdkEntityBulletCasingShell.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletCasingShell")));
//        event.register(SdkEntityBulletAk47.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletAk47")));
//        event.register(SdkEntityBulletDeagle.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletDeagle")));
//        event.register(SdkEntityBulletFlame.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletFlame")));
//        event.register(SdkEntityBulletM4.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletM4")));
//        event.register(SdkEntityBulletMinigun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMinigun")));
//        event.register(SdkEntityBulletMp5.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMp5")));
//        event.register(SdkEntityBulletSg552.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletSg552")));
//        event.register(SdkEntityBulletShot.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletShot")));
//        event.register(SdkEntityBulletSniper.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletSniper")));
//        event.register(SdkEntityBulletRocket.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletRocket")));
//        event.register(SdkEntityBulletRocketLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletRocketLaser")));
//        event.register(SdkEntityBulletLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletLaser")));
    }

    @EventListener
    private static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("SdkEntityAtv"), SdkEntityAtv::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityGrapplingHook"), SdkEntityGrapplingHook::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityNukePrimed"), SdkEntityNukePrimed::new);

//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletCasing"), SdkEntityBulletCasing::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletCasingShell"), SdkEntityBulletCasingShell::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletAk47"), SdkEntityBulletAk47::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletDeagle"), SdkEntityBulletDeagle::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletFlame"), SdkEntityBulletFlame::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletM4"), SdkEntityBulletM4::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMinigun"), SdkEntityBulletMinigun::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMp5"), SdkEntityBulletMp5::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletSg552"), SdkEntityBulletSg552::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletShot"), SdkEntityBulletShot::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletSniper"), SdkEntityBulletSniper::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletRocket"), SdkEntityBulletRocket::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletRocketLaser"), SdkEntityBulletRocketLaser::new);
//        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletLaser"), SdkEntityBulletLaser::new);
    }
}
