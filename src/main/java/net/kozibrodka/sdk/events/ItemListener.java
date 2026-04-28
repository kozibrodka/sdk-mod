package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkItemAtv;
import net.kozibrodka.sdk.item.*;
import net.kozibrodka.sdk.itemGun.*;
import net.kozibrodka.sdk.itemNade.*;
import net.kozibrodka.sdk_api.utils.SdkMap;
import net.kozibrodka.sdk_api.utils.SdkTelescopeItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;


public class ItemListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        //AMMO
        itemBulletCasing = new TemplateItem(Identifier.of(MOD_ID, "BulletCasing")).setTranslationKey(MOD_ID, "BulletCasing").setMaxCount(64);
        itemShellCasing = new TemplateItem(Identifier.of(MOD_ID, "ShellCasing")).setTranslationKey(MOD_ID, "ShellCasing").setMaxCount(64);
        itemBulletLight = new TemplateItem(Identifier.of(MOD_ID, "BulletLight")).setTranslationKey(MOD_ID, "BulletLight").setMaxCount(32);
        itemBulletMedium = new TemplateItem(Identifier.of(MOD_ID, "BulletMedium")).setTranslationKey(MOD_ID, "BulletMedium").setMaxCount(8);
        itemBulletHeavy = new TemplateItem(Identifier.of(MOD_ID, "BulletHeavy")).setTranslationKey(MOD_ID, "BulletHeavy").setMaxCount(4);
        itemBulletRocket = new TemplateItem(Identifier.of(MOD_ID, "BulletRocket")).setTranslationKey(MOD_ID, "BulletRocket").setMaxCount(4);
        itemBulletRocketLaser = new TemplateItem(Identifier.of(MOD_ID, "BulletRocketLaser")).setTranslationKey(MOD_ID, "BulletRocketLaser").setMaxCount(4);
        itemBulletShell = new TemplateItem(Identifier.of(MOD_ID, "BulletShell")).setTranslationKey(MOD_ID, "BulletShell").setMaxCount(8);
        itemOil = new SdkItemOil(Identifier.of(MOD_ID, "Oil")).setTranslationKey(MOD_ID, "Oil");

        //GUNS
        itemGunAk47 = new SdkItemGunAk47(Identifier.of(MOD_ID, "GunAk47")).setTranslationKey(MOD_ID, "GunAk47");
        itemGunMp5 = new SdkItemGunMp5(Identifier.of(MOD_ID, "GunMp5")).setTranslationKey(MOD_ID, "GunMp5");
        itemGunShotgun = new SdkItemGunShotgun(Identifier.of(MOD_ID, "GunShotgun")).setTranslationKey(MOD_ID, "GunShotgun");
        itemGunDeagle = new SdkItemGunDeagle(Identifier.of(MOD_ID, "GunDeagle")).setTranslationKey(MOD_ID, "GunDeagle");
        itemGunSniper = new SdkItemGunSniper(Identifier.of(MOD_ID, "GunSniper")).setTranslationKey(MOD_ID, "GunSniper");
        itemGunM4 = new SdkItemGunM4(Identifier.of(MOD_ID, "GunM4")).setTranslationKey(MOD_ID, "GunM4");
        itemGunSg552 = new SdkItemGunSg552(Identifier.of(MOD_ID, "GunSg552")).setTranslationKey(MOD_ID, "GunSg552");
        itemGunMinigun = new SdkItemGunMinigun(Identifier.of(MOD_ID, "GunMinigun")).setTranslationKey(MOD_ID, "GunMinigun");
        itemGunLaser = new SdkItemGunLaser(Identifier.of(MOD_ID, "GunLaser")).setTranslationKey(MOD_ID, "GunLaser");
        itemGunFlamethrower = new SdkItemGunFlamethrower(Identifier.of(MOD_ID, "GunFlamethrower")).setTranslationKey(MOD_ID, "GunFlamethrower");
        itemGunRocketLauncherLaser = new SdkItemGunRocketLauncherLaser(Identifier.of(MOD_ID, "GunRocketLauncherLaser")).setTranslationKey(MOD_ID, "GunRocketLauncherLaser");
        itemGunRocketLauncher = new SdkItemGunRocketLauncher(Identifier.of(MOD_ID, "GunRocketLauncher")).setTranslationKey(MOD_ID, "GunRocketLauncher");

        //NADES
        itemGrenade = new SdkItemGrenadeAP(Identifier.of(MOD_ID, "Grenade")).setTranslationKey(MOD_ID, "Grenade").setMaxCount(4);
        itemGrenadeHe = new SdkItemGrenadeHE(Identifier.of(MOD_ID, "GrenadeHe")).setTranslationKey(MOD_ID, "GrenadeHe").setMaxCount(4);
        itemGrenadeStun = new SdkItemGrenadeStun(Identifier.of(MOD_ID, "GrenadeStun")).setTranslationKey(MOD_ID, "GrenadeStun").setMaxCount(4);
        itemGrenadeSmoke = new SdkItemGrenadeSmoke(Identifier.of(MOD_ID, "GrenadeSmoke")).setTranslationKey(MOD_ID, "GrenadeSmoke").setMaxCount(4);
        itemGrenadeSticky = new SdkItemGrenadeSticky(Identifier.of(MOD_ID, "GrenadeSticky")).setTranslationKey(MOD_ID, "GrenadeSticky").setMaxCount(4);
        itemGrenadeIncendiary = new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "GrenadeIncendiary")).setTranslationKey(MOD_ID, "GrenadeIncendiary").setMaxCount(4);
        itemGrenadeIncendiaryLit = new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "GrenadeIncendiaryLit")).setTranslationKey(MOD_ID, "GrenadeIncendiaryLit").setMaxCount(4);

        //UTIL
        itemLightometer = new TemplateItem(Identifier.of(MOD_ID, "Lightometer")).setTranslationKey(MOD_ID, "Lightometer"); //TODO
        itemNightvisionGoggles = new ItemNightvision(Identifier.of(MOD_ID, "NightvisionGoggles")).setTranslationKey(MOD_ID, "NightvisionGoggles");
        itemScubaTank = new ItemScuba(Identifier.of(MOD_ID, "ScubaTank")).setTranslationKey(MOD_ID, "ScubaTank");
        itemParachute = new ItemParachute(Identifier.of(MOD_ID, "Parachute")).setTranslationKey(MOD_ID, "Parachute");
        itemTelescope = new SdkTelescopeItem(Identifier.of(MOD_ID, "Telescope"), new float[]{1.0F, 0.5F, 0.25F, 0.125F, 0.0625F}, 0.0625F, "/assets/sdk/stationapi/textures/gui/miscTelescope.png").setTranslationKey(MOD_ID, "Telescope");
        itemJetPack = new ItemJetPack(Identifier.of(MOD_ID, "JetPack")).setTranslationKey(MOD_ID, "JetPack");
        itemGrapplingHook = new SdkItemGrapplingHook(Identifier.of(MOD_ID, "GrapplingHook")).setTranslationKey(MOD_ID, "GrapplingHook");
        itemRope = new TemplateItem(Identifier.of(MOD_ID, "Rope")).setTranslationKey(MOD_ID, "Rope");
        itemGoldCoin = new TemplateItem(Identifier.of(MOD_ID, "GoldCoin")).setTranslationKey(MOD_ID, "GoldCoin");
        itemOilDrop = new TemplateItem(Identifier.of(MOD_ID, "OilDrop")).setTranslationKey(MOD_ID, "OilDrop");

        itemAtv = new SdkItemAtv(Identifier.of(MOD_ID, "Atv")).setTranslationKey(MOD_ID, "Atv");
        itemAtvBody = new TemplateItem(Identifier.of(MOD_ID, "AtvBody")).setTranslationKey(MOD_ID, "AtvBody");
        itemAtvWheel = new TemplateItem(Identifier.of(MOD_ID, "AtvWheel")).setTranslationKey(MOD_ID, "AtvWheel");
        itemWrench = new TemplateItem(Identifier.of(MOD_ID, "Wrench")).setTranslationKey(MOD_ID, "Wrench").setMaxCount(1).setMaxDamage(15);
        itemSentry = new SdkItemSentry(Identifier.of(MOD_ID, "Sentry")).setTranslationKey(MOD_ID, "Sentry").setMaxCount(1).setMaxDamage(0);

        //PARTS
        itemMetalParts = new TemplateItem(Identifier.of(MOD_ID, "MetalParts")).setTranslationKey(MOD_ID, "MetalParts");
        itemBarrelLong = new TemplateItem(Identifier.of(MOD_ID, "BarrelLong")).setTranslationKey(MOD_ID, "BarrelLong");
        itemBarrelShort = new TemplateItem(Identifier.of(MOD_ID, "BarrelShort")).setTranslationKey(MOD_ID, "BarrelShort");
        itemBarrelFat = new TemplateItem(Identifier.of(MOD_ID, "BarrelFat")).setTranslationKey(MOD_ID, "BarrelFat");
        itemBarrelShotgun = new TemplateItem(Identifier.of(MOD_ID, "BarrelShotgun")).setTranslationKey(MOD_ID, "BarrelShotgun");
        itemBarrelMinigun = new TemplateItem(Identifier.of(MOD_ID, "BarrelMinigun")).setTranslationKey(MOD_ID, "BarrelMinigun");
        itemGripWood = new TemplateItem(Identifier.of(MOD_ID, "GripWood")).setTranslationKey(MOD_ID, "GripWood");
        itemGripMetal = new TemplateItem(Identifier.of(MOD_ID, "GripMetal")).setTranslationKey(MOD_ID, "GripMetal");
        itemHandleMinigun = new TemplateItem(Identifier.of(MOD_ID, "HandleMinigun")).setTranslationKey(MOD_ID, "HandleMinigun");
        itemStockWood = new TemplateItem(Identifier.of(MOD_ID, "StockWood")).setTranslationKey(MOD_ID, "StockWood");
        itemStockMetal = new TemplateItem(Identifier.of(MOD_ID, "StockMetal")).setTranslationKey(MOD_ID, "StockMetal");
        itemReceiverMetal = new TemplateItem(Identifier.of(MOD_ID, "ReceiverMetal")).setTranslationKey(MOD_ID, "ReceiverMetal");
        itemReceiverDiamond = new TemplateItem(Identifier.of(MOD_ID, "ReceiverDiamond")).setTranslationKey(MOD_ID, "ReceiverDiamond");
        itemMagazine = new TemplateItem(Identifier.of(MOD_ID, "Magazine")).setTranslationKey(MOD_ID, "Magazine");
        itemScope = new TemplateItem(Identifier.of(MOD_ID, "Scope")).setTranslationKey(MOD_ID, "Scope");

        //MAPS

        SdkMap.NORELOAD_LIST.add(itemGunMinigun);
        SdkMap.NORELOAD_LIST.add(itemGunLaser);
        SdkMap.NORELOAD_LIST.add(itemGunFlamethrower);

        SdkMap.HEAVYMG_LIST.add(itemGunMinigun);

        SdkMap.SCOPED_LIST.add(itemGunSniper);
        SdkMap.SCOPED_LIST.add(itemGunSg552);

    }

    //BRONIE
    public static Item itemGunAk47;
    public static Item itemGunMp5;
    public static Item itemGunShotgun;
    public static Item itemGunDeagle;
    public static Item itemGunSniper;
    public static Item itemGunM4;
    public static Item itemGunSg552;
    public static Item itemGunMinigun;
    public static Item itemGunLaser;
    public static Item itemGunFlamethrower;
    public static Item itemGunRocketLauncherLaser;
    public static Item itemGunRocketLauncher;

    //AMMO
    public static Item itemBulletCasing;
    public static Item itemShellCasing;
    public static Item itemBulletLight;
    public static Item itemBulletMedium;
    public static Item itemBulletHeavy;
    public static Item itemOil;
    public static Item itemBulletRocket;
    public static Item itemBulletRocketLaser;
    public static Item itemBulletShell;


    //granaty
    public static Item itemGrenade;
    public static Item itemGrenadeHe;
    public static Item itemGrenadeStun;
    public static Item itemGrenadeSmoke;
    public static Item itemGrenadeSticky;
    public static Item itemGrenadeIncendiary;
    public static Item itemGrenadeIncendiaryLit;

    //util
    public static Item itemGoldCoin;
    public static Item itemLightometer;
    public static Item itemNightvisionGoggles;
    public static Item itemScubaTank;
    public static Item itemParachute;
    public static Item itemTelescope;

    public static Item itemJetPack;
    public static Item itemWrench;
    public static Item itemAtv;
    public static Item itemOilDrop;
    public static Item itemSentry;

    public static Item itemRope;
    public static Item itemGrapplingHook;

    //parts
    public static Item itemMetalParts;
    public static Item itemBarrelLong;
    public static Item itemBarrelShort;
    public static Item itemBarrelFat;
    public static Item itemBarrelShotgun;
    public static Item itemBarrelMinigun;
    public static Item itemGripWood;
    public static Item itemGripMetal;
    public static Item itemHandleMinigun;
    public static Item itemStockWood;
    public static Item itemStockMetal;
    public static Item itemReceiverMetal;
    public static Item itemReceiverDiamond;
    public static Item itemMagazine;
    public static Item itemScope;
    public static Item itemAtvBody;
    public static Item itemAtvWheel;

}
