package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.entityBullet.*;
import net.kozibrodka.sdk.entitySentry.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
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

        event.register(SdkEntityBulletAk47.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletAk47")));
        event.register(SdkEntityBulletDeagle.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletDeagle")));
        event.register(SdkEntityBulletFlame.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletFlame")));
        event.register(SdkEntityBulletM4.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletM4")));
        event.register(SdkEntityBulletMinigun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMinigun")));
        event.register(SdkEntityBulletMp5.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMp5")));
        event.register(SdkEntityBulletSg552.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletSg552")));
        event.register(SdkEntityBulletShot.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletShot")));
        event.register(SdkEntityBulletSniper.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletSniper")));
        event.register(SdkEntityBulletRocket.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletRocket")));
        event.register(SdkEntityBulletRocketLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletRocketLaser")));
        event.register(SdkEntityBulletLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletLaser")));

        event.register(SdkEntityLaserWolf.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityLaserWolf")));
        event.register(SdkEntitySentryAk47.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryAk47")));
        event.register(SdkEntitySentryDeagle.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryDeagle")));
        event.register(SdkEntitySentryMp5.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryMp5")));
        event.register(SdkEntitySentryRocketLauncher.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryRocketLauncher")));
        event.register(SdkEntitySentryRocketLauncherLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryRocketLauncherLaser")));
        event.register(SdkEntitySentryShotgun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryShotgun")));
        event.register(SdkEntitySentrySniper.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentrySniper")));
        event.register(SdkEntitySentryFlamethrower.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryFlamethrower")));
        event.register(SdkEntitySentrySg552.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentrySg552")));
        event.register(SdkEntitySentryMinigun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryMinigun")));
        event.register(SdkEntitySentryLaser.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryLaser")));
        event.register(SdkEntitySentryM4.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntitySentryM4")));

    }

    @EventListener
    private static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("SdkEntityAtv"), SdkEntityAtv::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityGrapplingHook"), SdkEntityGrapplingHook::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityNukePrimed"), SdkEntityNukePrimed::new);

        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletAk47"), SdkEntityBulletAk47::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletDeagle"), SdkEntityBulletDeagle::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletFlame"), SdkEntityBulletFlame::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletM4"), SdkEntityBulletM4::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMinigun"), SdkEntityBulletMinigun::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMp5"), SdkEntityBulletMp5::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletSg552"), SdkEntityBulletSg552::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletShot"), SdkEntityBulletShot::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletSniper"), SdkEntityBulletSniper::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletRocket"), SdkEntityBulletRocket::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletRocketLaser"), SdkEntityBulletRocketLaser::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletLaser"), SdkEntityBulletLaser::new);

        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryAk47"), SdkEntitySentryAk47::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryDeagle"), SdkEntitySentryDeagle::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryMp5"), SdkEntitySentryMp5::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryRocketLauncher"), SdkEntitySentryRocketLauncher::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryRocketLauncherLaser"), SdkEntitySentryRocketLauncherLaser::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryShotgun"), SdkEntitySentryShotgun::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentrySniper"), SdkEntitySentrySniper::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryFlamethrower"), SdkEntitySentryFlamethrower::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentrySg552"), SdkEntitySentrySg552::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryMinigun"), SdkEntitySentryMinigun::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryLaser"), SdkEntitySentryLaser::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntitySentryM4"), SdkEntitySentryM4::new);
    }

    @EventListener
    private static void registerMobsHandlers(MobHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("SdkEntityLaserWolf"), SdkEntityLaserWolf::new);
    }

    public static Class sentryEntityClasses[];
    static {
        sentryEntityClasses = (new Class[]{
                SdkEntitySentryAk47.class, SdkEntitySentryMp5.class, SdkEntitySentryShotgun.class, SdkEntitySentryDeagle.class, SdkEntitySentryRocketLauncher.class, SdkEntitySentryRocketLauncherLaser.class, SdkEntitySentrySniper.class, SdkEntitySentryFlamethrower.class, SdkEntitySentrySg552.class, SdkEntitySentryMinigun.class,
                SdkEntitySentryLaser.class, SdkEntitySentryM4.class
        });
    }


}
