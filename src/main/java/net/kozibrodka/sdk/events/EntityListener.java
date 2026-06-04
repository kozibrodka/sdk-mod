package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityLaserWolf;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.entity.SdkEntityParachute;
import net.kozibrodka.sdk.entityBullet.*;
import net.kozibrodka.sdk.entityNade.*;
import net.kozibrodka.sdk.entitySentry.*;
import net.kozibrodka.sdk_api.TEST.EntityRocketTest;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
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
    public static void registerEntities(EntityRegisterEvent event) {
        event.register(Identifier.of(MOD_ID, "Atv"), SdkEntityAtv.class);
        event.register(Identifier.of(MOD_ID, "GrapplingHook"), SdkEntityGrapplingHook.class);
        event.register(Identifier.of(MOD_ID, "NukePrimed"), SdkEntityNukePrimed.class);
        event.register(Identifier.of(MOD_ID, "Parachute"), SdkEntityParachute.class);

        event.register(Identifier.of(MOD_ID, "BulletCasing"), EntityBulletCasing.class);
        event.register(Identifier.of(MOD_ID, "ShellCasing"), EntityShellCasing.class);

        event.register(Identifier.of(MOD_ID, "BulletAk47"), SdkEntityBulletAk47.class);
        event.register(Identifier.of(MOD_ID, "BulletDeagle"), SdkEntityBulletDeagle.class);
        event.register(Identifier.of(MOD_ID, "BulletFlame"), SdkEntityBulletFlame.class);
        event.register(Identifier.of(MOD_ID, "BulletM4"), SdkEntityBulletM4.class);
        event.register(Identifier.of(MOD_ID, "BulletMinigun"), SdkEntityBulletMinigun.class);
        event.register(Identifier.of(MOD_ID, "BulletMp5"), SdkEntityBulletMp5.class);
        event.register(Identifier.of(MOD_ID, "BulletSg552"), SdkEntityBulletSg552.class);
        event.register(Identifier.of(MOD_ID, "BulletShot"), SdkEntityBulletShot.class);
        event.register(Identifier.of(MOD_ID, "BulletSniper"), SdkEntityBulletSniper.class);
        event.register(Identifier.of(MOD_ID, "BulletRocket"), SdkEntityBulletRocket.class);
        event.register(Identifier.of(MOD_ID, "BulletRocketLaser"), SdkEntityBulletRocketLaser.class);
        event.register(Identifier.of(MOD_ID, "BulletLaser"), SdkEntityBulletLaser.class);

        event.register(Identifier.of(MOD_ID, "NadeAp"), SdkEntityGrenadeAP.class);
        event.register(Identifier.of(MOD_ID, "NadeHe"), SdkEntityGrenadeHE.class);
        event.register(Identifier.of(MOD_ID, "NadeFire"), SdkEntityGrenadeIncendiary.class);
        event.register(Identifier.of(MOD_ID, "NadeSmoke"), SdkEntityGrenadeSmoke.class);
        event.register(Identifier.of(MOD_ID, "NadeSticky"), SdkEntityGrenadeSticky.class);
        event.register(Identifier.of(MOD_ID, "NadeStun"), SdkEntityGrenadeStun.class);

        event.register(Identifier.of(MOD_ID, "LaserWolf"), SdkEntityLaserWolf.class);
        event.register(Identifier.of(MOD_ID, "SentryAk47"), SdkEntitySentryAk47.class);
        event.register(Identifier.of(MOD_ID, "SentryDeagle"), SdkEntitySentryDeagle.class);
        event.register(Identifier.of(MOD_ID, "SentryMp5"), SdkEntitySentryMp5.class);
        event.register(Identifier.of(MOD_ID, "SentryRocketLauncher"), SdkEntitySentryRocketLauncher.class);
        event.register(Identifier.of(MOD_ID, "SentryRocketLauncherLaser"), SdkEntitySentryRocketLauncherLaser.class);
        event.register(Identifier.of(MOD_ID, "SentryShotgun"), SdkEntitySentryShotgun.class);
        event.register(Identifier.of(MOD_ID, "SentrySniper"), SdkEntitySentrySniper.class);
        event.register(Identifier.of(MOD_ID, "SentryFlamethrower"), SdkEntitySentryFlamethrower.class);
        event.register(Identifier.of(MOD_ID, "SentrySg552"), SdkEntitySentrySg552.class);
        event.register(Identifier.of(MOD_ID, "SentryMinigun"), SdkEntitySentryMinigun.class);
        event.register(Identifier.of(MOD_ID, "SentryLaser"), SdkEntitySentryLaser.class);
        event.register(Identifier.of(MOD_ID, "SentryM4"), SdkEntitySentryM4.class);
    }

    @EventListener
    public static void registerEntityHandlers(EntityHandlerRegistryEvent event) {
        event.register(MOD_ID.id("Atv"), SdkEntityAtv::new);
        event.register(MOD_ID.id("GrapplingHook"), SdkEntityGrapplingHook::new);
        event.register(MOD_ID.id("NukePrimed"), SdkEntityNukePrimed::new);
        event.register(MOD_ID.id("Parachute"), SdkEntityParachute::new);

        event.register(MOD_ID.id("BulletCasing"), EntityBulletCasing::new);
        event.register(MOD_ID.id("Method…"), EntityShellCasing::new);

        event.register(MOD_ID.id("BulletAk47"), SdkEntityBulletAk47::new);
        event.register(MOD_ID.id("BulletDeagle"), SdkEntityBulletDeagle::new);
        event.register(MOD_ID.id("BulletFlame"), SdkEntityBulletFlame::new);
        event.register(MOD_ID.id("BulletM4"), SdkEntityBulletM4::new);
        event.register(MOD_ID.id("BulletMinigun"), SdkEntityBulletMinigun::new);
        event.register(MOD_ID.id("BulletMp5"), SdkEntityBulletMp5::new);
        event.register(MOD_ID.id("BulletSg552"), SdkEntityBulletSg552::new);
        event.register(MOD_ID.id("BulletShot"), SdkEntityBulletShot::new);
        event.register(MOD_ID.id("BulletSniper"), SdkEntityBulletSniper::new);
        event.register(MOD_ID.id("BulletRocket"), SdkEntityBulletRocket::new);
        event.register(MOD_ID.id("BulletRocketLaser"), SdkEntityBulletRocketLaser::new);
        event.register(MOD_ID.id("BulletLaser"), SdkEntityBulletLaser::new);

        event.register(MOD_ID.id("NadeAp"), SdkEntityGrenadeAP::new);
        event.register(MOD_ID.id("NadeHe"), SdkEntityGrenadeHE::new);
        event.register(MOD_ID.id("NadeFire"), SdkEntityGrenadeIncendiary::new);
        event.register(MOD_ID.id("NadeSmoke"), SdkEntityGrenadeSmoke::new);
        event.register(MOD_ID.id("NadeSticky"), SdkEntityGrenadeSticky::new);
        event.register(MOD_ID.id("NadeStun"), SdkEntityGrenadeStun::new);
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
        if(event.biome == Biome.FOREST || event.biome == Biome.TAIGA){
            if(SdkConfig.spawnLaserWolves) {
                event.biome.addPassiveEntity(SdkEntityLaserWolf.class, 1);
            }
        }
    }

    public static Class sentryEntityClasses[];
    static {
        sentryEntityClasses = (new Class[]{
                SdkEntitySentryAk47.class, SdkEntitySentryMp5.class, SdkEntitySentryShotgun.class, SdkEntitySentryDeagle.class, SdkEntitySentryRocketLauncher.class, SdkEntitySentryRocketLauncherLaser.class, SdkEntitySentrySniper.class, SdkEntitySentryFlamethrower.class, SdkEntitySentrySg552.class, SdkEntitySentryMinigun.class,
                SdkEntitySentryLaser.class, SdkEntitySentryM4.class
        });
    }


}
