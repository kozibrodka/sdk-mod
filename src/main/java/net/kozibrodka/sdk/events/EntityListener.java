package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityLaserWolf;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.entity.SdkEntityParachute;
import net.kozibrodka.sdk.entityBullet.*;
import net.kozibrodka.sdk.entityNade.*;
import net.kozibrodka.sdk.entitySentry.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.registry.Registry;

public class EntityListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public static void registerEntities(EntityRegister event) {
        event.register(SdkEntityAtv.class, String.valueOf(Identifier.of(MOD_ID, "Atv")));
        event.register(SdkEntityGrapplingHook.class, String.valueOf(Identifier.of(MOD_ID, "GrapplingHook")));
        event.register(SdkEntityNukePrimed.class, String.valueOf(Identifier.of(MOD_ID, "NukePrimed")));
        event.register(SdkEntityParachute.class, String.valueOf(Identifier.of(MOD_ID, "Parachute")));

        event.register(EntityBulletCasing.class, String.valueOf(Identifier.of(MOD_ID, "BulletCasing")));
        event.register(EntityShellCasing.class, String.valueOf(Identifier.of(MOD_ID, "ShellCasing")));

        event.register(SdkEntityBulletAk47.class, String.valueOf(Identifier.of(MOD_ID, "BulletAk47")));
        event.register(SdkEntityBulletDeagle.class, String.valueOf(Identifier.of(MOD_ID, "BulletDeagle")));
        event.register(SdkEntityBulletFlame.class, String.valueOf(Identifier.of(MOD_ID, "BulletFlame")));
        event.register(SdkEntityBulletM4.class, String.valueOf(Identifier.of(MOD_ID, "BulletM4")));
        event.register(SdkEntityBulletMinigun.class, String.valueOf(Identifier.of(MOD_ID, "BulletMinigun")));
        event.register(SdkEntityBulletMp5.class, String.valueOf(Identifier.of(MOD_ID, "BulletMp5")));
        event.register(SdkEntityBulletSg552.class, String.valueOf(Identifier.of(MOD_ID, "BulletSg552")));
        event.register(SdkEntityBulletShot.class, String.valueOf(Identifier.of(MOD_ID, "BulletShot")));
        event.register(SdkEntityBulletSniper.class, String.valueOf(Identifier.of(MOD_ID, "BulletSniper")));
        event.register(SdkEntityBulletRocket.class, String.valueOf(Identifier.of(MOD_ID, "BulletRocket")));
        event.register(SdkEntityBulletRocketLaser.class, String.valueOf(Identifier.of(MOD_ID, "BulletRocketLaser")));
        event.register(SdkEntityBulletLaser.class, String.valueOf(Identifier.of(MOD_ID, "BulletLaser")));

        event.register(SdkEntityGrenadeAP.class, String.valueOf(Identifier.of(MOD_ID, "NadeAp")));
        event.register(SdkEntityGrenadeHE.class, String.valueOf(Identifier.of(MOD_ID, "NadeHe")));
        event.register(SdkEntityGrenadeIncendiary.class, String.valueOf(Identifier.of(MOD_ID, "NadeFire")));
        event.register(SdkEntityGrenadeSmoke.class, String.valueOf(Identifier.of(MOD_ID, "NadeSmoke")));
        event.register(SdkEntityGrenadeSticky.class, String.valueOf(Identifier.of(MOD_ID, "NadeSticky")));
        event.register(SdkEntityGrenadeStun.class, String.valueOf(Identifier.of(MOD_ID, "NadeStun")));

        event.register(SdkEntityLaserWolf.class, String.valueOf(Identifier.of(MOD_ID, "LaserWolf")));
        event.register(SdkEntitySentryAk47.class, String.valueOf(Identifier.of(MOD_ID, "SentryAk47")));
        event.register(SdkEntitySentryDeagle.class, String.valueOf(Identifier.of(MOD_ID, "SentryDeagle")));
        event.register(SdkEntitySentryMp5.class, String.valueOf(Identifier.of(MOD_ID, "SentryMp5")));
        event.register(SdkEntitySentryRocketLauncher.class, String.valueOf(Identifier.of(MOD_ID, "SentryRocketLauncher")));
        event.register(SdkEntitySentryRocketLauncherLaser.class, String.valueOf(Identifier.of(MOD_ID, "SentryRocketLauncherLaser")));
        event.register(SdkEntitySentryShotgun.class, String.valueOf(Identifier.of(MOD_ID, "SentryShotgun")));
        event.register(SdkEntitySentrySniper.class, String.valueOf(Identifier.of(MOD_ID, "SentrySniper")));
        event.register(SdkEntitySentryFlamethrower.class, String.valueOf(Identifier.of(MOD_ID, "SentryFlamethrower")));
        event.register(SdkEntitySentrySg552.class, String.valueOf(Identifier.of(MOD_ID, "SentrySg552")));
        event.register(SdkEntitySentryMinigun.class, String.valueOf(Identifier.of(MOD_ID, "SentryMinigun")));
        event.register(SdkEntitySentryLaser.class, String.valueOf(Identifier.of(MOD_ID, "SentryLaser")));
        event.register(SdkEntitySentryM4.class, String.valueOf(Identifier.of(MOD_ID, "SentryM4")));

    }

    @EventListener
    public static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("Atv"), SdkEntityAtv::new);
        Registry.register(event.registry, MOD_ID.id("GrapplingHook"), SdkEntityGrapplingHook::new);
        Registry.register(event.registry, MOD_ID.id("NukePrimed"), SdkEntityNukePrimed::new);
        Registry.register(event.registry, MOD_ID.id("Parachute"), SdkEntityParachute::new);

        Registry.register(event.registry, MOD_ID.id("BulletCasing"), EntityBulletCasing::new);
        Registry.register(event.registry, MOD_ID.id("ShellCasing"), EntityShellCasing::new);

        Registry.register(event.registry, MOD_ID.id("BulletAk47"), SdkEntityBulletAk47::new);
        Registry.register(event.registry, MOD_ID.id("BulletDeagle"), SdkEntityBulletDeagle::new);
        Registry.register(event.registry, MOD_ID.id("BulletFlame"), SdkEntityBulletFlame::new);
        Registry.register(event.registry, MOD_ID.id("BulletM4"), SdkEntityBulletM4::new);
        Registry.register(event.registry, MOD_ID.id("BulletMinigun"), SdkEntityBulletMinigun::new);
        Registry.register(event.registry, MOD_ID.id("BulletMp5"), SdkEntityBulletMp5::new);
        Registry.register(event.registry, MOD_ID.id("BulletSg552"), SdkEntityBulletSg552::new);
        Registry.register(event.registry, MOD_ID.id("BulletShot"), SdkEntityBulletShot::new);
        Registry.register(event.registry, MOD_ID.id("BulletSniper"), SdkEntityBulletSniper::new);
        Registry.register(event.registry, MOD_ID.id("BulletRocket"), SdkEntityBulletRocket::new);
        Registry.register(event.registry, MOD_ID.id("BulletRocketLaser"), SdkEntityBulletRocketLaser::new);
        Registry.register(event.registry, MOD_ID.id("BulletLaser"), SdkEntityBulletLaser::new);

        Registry.register(event.registry, MOD_ID.id("NadeAp"), SdkEntityGrenadeAP::new);
        Registry.register(event.registry, MOD_ID.id("NadeHe"), SdkEntityGrenadeHE::new);
        Registry.register(event.registry, MOD_ID.id("NadeFire"), SdkEntityGrenadeIncendiary::new);
        Registry.register(event.registry, MOD_ID.id("NadeSmoke"), SdkEntityGrenadeSmoke::new);
        Registry.register(event.registry, MOD_ID.id("NadeSticky"), SdkEntityGrenadeSticky::new);
        Registry.register(event.registry, MOD_ID.id("NadeStun"), SdkEntityGrenadeStun::new);

//        Registry.register(event.registry, MOD_ID.id("SentryAk47"), SdkEntitySentryAk47::new);
//        Registry.register(event.registry, MOD_ID.id("SentryDeagle"), SdkEntitySentryDeagle::new);
//        Registry.register(event.registry, MOD_ID.id("SentryMp5"), SdkEntitySentryMp5::new);
//        Registry.register(event.registry, MOD_ID.id("SentryRocketLauncher"), SdkEntitySentryRocketLauncher::new);
//        Registry.register(event.registry, MOD_ID.id("SentryRocketLauncherLaser"), SdkEntitySentryRocketLauncherLaser::new);
//        Registry.register(event.registry, MOD_ID.id("SentryShotgun"), SdkEntitySentryShotgun::new);
//        Registry.register(event.registry, MOD_ID.id("SentrySniper"), SdkEntitySentrySniper::new);
//        Registry.register(event.registry, MOD_ID.id("SentryFlamethrower"), SdkEntitySentryFlamethrower::new);
//        Registry.register(event.registry, MOD_ID.id("SentrySg552"), SdkEntitySentrySg552::new);
//        Registry.register(event.registry, MOD_ID.id("SentryMinigun"), SdkEntitySentryMinigun::new);
//        Registry.register(event.registry, MOD_ID.id("SentryLaser"), SdkEntitySentryLaser::new);
//        Registry.register(event.registry, MOD_ID.id("SentryM4"), SdkEntitySentryM4::new);
    }

    @EventListener
    public static void registerMobsHandlers(MobHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("LaserWolf"), SdkEntityLaserWolf::new);
        Registry.register(event.registry, MOD_ID.id("SentryAk47"), SdkEntitySentryAk47::new);
        Registry.register(event.registry, MOD_ID.id("SentryDeagle"), SdkEntitySentryDeagle::new);
        Registry.register(event.registry, MOD_ID.id("SentryMp5"), SdkEntitySentryMp5::new);
        Registry.register(event.registry, MOD_ID.id("SentryRocketLauncher"), SdkEntitySentryRocketLauncher::new);
        Registry.register(event.registry, MOD_ID.id("SentryRocketLauncherLaser"), SdkEntitySentryRocketLauncherLaser::new);
        Registry.register(event.registry, MOD_ID.id("SentryShotgun"), SdkEntitySentryShotgun::new);
        Registry.register(event.registry, MOD_ID.id("SentrySniper"), SdkEntitySentrySniper::new);
        Registry.register(event.registry, MOD_ID.id("SentryFlamethrower"), SdkEntitySentryFlamethrower::new);
        Registry.register(event.registry, MOD_ID.id("SentrySg552"), SdkEntitySentrySg552::new);
        Registry.register(event.registry, MOD_ID.id("SentryMinigun"), SdkEntitySentryMinigun::new);
        Registry.register(event.registry, MOD_ID.id("SentryLaser"), SdkEntitySentryLaser::new);
        Registry.register(event.registry, MOD_ID.id("SentryM4"), SdkEntitySentryM4::new);
    }

    @EventListener
    public void registerEntitySpawn(BiomeModificationEvent event) {
        //TODO wolferinio + opcja
    }

    public static Class sentryEntityClasses[];
    static {
        sentryEntityClasses = (new Class[]{
                SdkEntitySentryAk47.class, SdkEntitySentryMp5.class, SdkEntitySentryShotgun.class, SdkEntitySentryDeagle.class, SdkEntitySentryRocketLauncher.class, SdkEntitySentryRocketLauncherLaser.class, SdkEntitySentrySniper.class, SdkEntitySentryFlamethrower.class, SdkEntitySentrySg552.class, SdkEntitySentryMinigun.class,
                SdkEntitySentryLaser.class, SdkEntitySentryM4.class
        });
    }


}
