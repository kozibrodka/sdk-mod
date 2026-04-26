package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkItemAtv;
import net.kozibrodka.sdk.item.*;
import net.kozibrodka.sdk.itemGun.*;
import net.kozibrodka.sdk.itemNade.*;
import net.kozibrodka.sdk_api.utils.SdkMap;
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
        itemBulletLight = new TemplateItem(Identifier.of(MOD_ID, "itemBulletLight")).setTranslationKey(MOD_ID, "itemBulletLight").setMaxCount(32);
        itemBulletMedium = new TemplateItem(Identifier.of(MOD_ID, "itemBulletMedium")).setTranslationKey(MOD_ID, "itemBulletMedium").setMaxCount(8);
        itemBulletHeavy = new TemplateItem(Identifier.of(MOD_ID, "itemBulletHeavy")).setTranslationKey(MOD_ID, "itemBulletHeavy").setMaxCount(4);
        itemBulletRocket = new TemplateItem(Identifier.of(MOD_ID, "itemBulletRocket")).setTranslationKey(MOD_ID, "itemBulletRocket").setMaxCount(4);
        itemBulletRocketLaser = new TemplateItem(Identifier.of(MOD_ID, "itemBulletRocketLaser")).setTranslationKey(MOD_ID, "itemBulletRocketLaser").setMaxCount(4);
        itemBulletShell = new TemplateItem(Identifier.of(MOD_ID, "itemBulletShell")).setTranslationKey(MOD_ID, "itemBulletShell").setMaxCount(8);
        itemOil = new SdkItemOil(Identifier.of(MOD_ID, "itemOil")).setTranslationKey(MOD_ID, "itemOil");

        //GUNS
        itemGunAk47 = new SdkItemGunAk47(Identifier.of(MOD_ID, "itemGunAk47")).setTranslationKey(MOD_ID, "itemGunAk47");
        itemGunMp5 = new SdkItemGunMp5(Identifier.of(MOD_ID, "itemGunMp5")).setTranslationKey(MOD_ID, "itemGunMp5");
        itemGunShotgun = new SdkItemGunShotgun(Identifier.of(MOD_ID, "itemGunShotgun")).setTranslationKey(MOD_ID, "itemGunShotgun");
        itemGunDeagle = new SdkItemGunDeagle(Identifier.of(MOD_ID, "itemGunDeagle")).setTranslationKey(MOD_ID, "itemGunDeagle");
        itemGunSniper = new SdkItemGunSniper(Identifier.of(MOD_ID, "itemGunSniper")).setTranslationKey(MOD_ID, "itemGunSniper");
        itemGunM4 = new SdkItemGunM4(Identifier.of(MOD_ID, "itemGunM4")).setTranslationKey(MOD_ID, "itemGunM4");
        itemGunSg552 = new SdkItemGunSg552(Identifier.of(MOD_ID, "itemGunSg552")).setTranslationKey(MOD_ID, "itemGunSg552");
        itemGunMinigun = new SdkItemGunMinigun(Identifier.of(MOD_ID, "itemGunMinigun")).setTranslationKey(MOD_ID, "itemGunMinigun");
        itemGunLaser = new SdkItemGunLaser(Identifier.of(MOD_ID, "itemGunLaser")).setTranslationKey(MOD_ID, "itemGunLaser");
        itemGunFlamethrower = new SdkItemGunFlamethrower(Identifier.of(MOD_ID, "itemGunFlamethrower")).setTranslationKey(MOD_ID, "itemGunFlamethrower");
        itemGunRocketLauncherLaser = new SdkItemGunRocketLauncherLaser(Identifier.of(MOD_ID, "itemGunRocketLauncherLaser")).setTranslationKey(MOD_ID, "itemGunRocketLauncherLaser");
        itemGunRocketLauncher = new SdkItemGunRocketLauncher(Identifier.of(MOD_ID, "itemGunRocketLauncher")).setTranslationKey(MOD_ID, "itemGunRocketLauncher");

        //NADES
        itemGrenade = new SdkItemGrenadeAP(Identifier.of(MOD_ID, "itemGrenade")).setTranslationKey(MOD_ID, "itemGrenade").setMaxCount(4);
        itemGrenadeHe = new SdkItemGrenadeHE(Identifier.of(MOD_ID, "itemGrenadeHe")).setTranslationKey(MOD_ID, "itemGrenadeHe").setMaxCount(4);
        itemGrenadeStun = new SdkItemGrenadeStun(Identifier.of(MOD_ID, "itemGrenadeStun")).setTranslationKey(MOD_ID, "itemGrenadeStun").setMaxCount(4);
        itemGrenadeSmoke = new SdkItemGrenadeSmoke(Identifier.of(MOD_ID, "itemGrenadeSmoke")).setTranslationKey(MOD_ID, "itemGrenadeSmoke").setMaxCount(4);
        itemGrenadeSticky = new SdkItemGrenadeSticky(Identifier.of(MOD_ID, "itemGrenadeSticky")).setTranslationKey(MOD_ID, "itemGrenadeSticky").setMaxCount(4);
        itemGrenadeIncendiary = new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiary")).setTranslationKey(MOD_ID, "itemGrenadeIncendiary").setMaxCount(4);
        itemGrenadeIncendiaryLit = new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiaryLit")).setTranslationKey(MOD_ID, "itemGrenadeIncendiaryLit").setMaxCount(4);

        //UTIL
        itemGoldCoin = new TemplateItem(Identifier.of(MOD_ID, "itemGoldCoin")).setTranslationKey(MOD_ID, "itemGoldCoin");
        itemLightometer = new TemplateItem(Identifier.of(MOD_ID, "itemLightometer")).setTranslationKey(MOD_ID, "itemLightometer");
        itemNightvisionGoggles = new SdkItemNightvision(Identifier.of(MOD_ID, "itemNightvisionGoggles"), 1).setTranslationKey(MOD_ID, "itemNightvisionGoggles");
        itemScubaTank = new SdkItemScuba(Identifier.of(MOD_ID, "itemScubaTank"), 1).setTranslationKey(MOD_ID, "itemScubaTank");
        itemParachute = new SdkItemParachute(Identifier.of(MOD_ID, "itemParachute"), 1).setTranslationKey(MOD_ID, "itemParachute");
        itemTelescope = new SdkItemTelescope(Identifier.of(MOD_ID, "itemTelescope")).setTranslationKey(MOD_ID, "itemTelescope");

        itemAtv = new SdkItemAtv(Identifier.of(MOD_ID, "itemAtv")).setTranslationKey(MOD_ID, "itemAtv");
        itemAtvBody = new TemplateItem(Identifier.of(MOD_ID, "itemAtvBody")).setTranslationKey(MOD_ID, "itemAtvBody");
        itemAtvWheel = new TemplateItem(Identifier.of(MOD_ID, "itemAtvWheel")).setTranslationKey(MOD_ID, "itemAtvWheel");

        itemWrench = new TemplateItem(Identifier.of(MOD_ID, "itemWrench")).setTranslationKey(MOD_ID, "itemWrench").setMaxCount(1).setMaxDamage(15);
        itemSentry = new SdkItemSentry(Identifier.of(MOD_ID, "itemSentry")).setTranslationKey(MOD_ID, "itemSentry").setMaxCount(1).setMaxDamage(0);
        itemOilDrop = new TemplateItem(Identifier.of(MOD_ID, "itemOilDrop")).setTranslationKey(MOD_ID, "itemOilDrop");
        itemJetPack = new SdkItemJetPack(Identifier.of(MOD_ID, "itemJetPack"), 1).setTranslationKey(MOD_ID, "itemJetPack");

        itemRope = new TemplateItem(Identifier.of(MOD_ID, "itemRope")).setTranslationKey(MOD_ID, "itemRope");
        itemGrapplingHook = new SdkItemGrapplingHook(Identifier.of(MOD_ID, "itemGrapplingHook")).setTranslationKey(MOD_ID, "itemGrapplingHook");

        //PARTS
        itemMetalParts = new TemplateItem(Identifier.of(MOD_ID, "itemMetalParts")).setTranslationKey(MOD_ID, "itemMetalParts");
        itemBarrelLong = new TemplateItem(Identifier.of(MOD_ID, "itemBarrelLong")).setTranslationKey(MOD_ID, "itemBarrelLong");
        itemBarrelShort = new TemplateItem(Identifier.of(MOD_ID, "itemBarrelShort")).setTranslationKey(MOD_ID, "itemBarrelShort");
        itemBarrelFat = new TemplateItem(Identifier.of(MOD_ID, "itemBarrelFat")).setTranslationKey(MOD_ID, "itemBarrelFat");
        itemBarrelShotgun = new TemplateItem(Identifier.of(MOD_ID, "itemBarrelShotgun")).setTranslationKey(MOD_ID, "itemBarrelShotgun");
        itemBarrelMinigun = new TemplateItem(Identifier.of(MOD_ID, "itemBarrelMinigun")).setTranslationKey(MOD_ID, "itemBarrelMinigun");
        itemGripWood = new TemplateItem(Identifier.of(MOD_ID, "itemGripWood")).setTranslationKey(MOD_ID, "itemGripWood");
        itemGripMetal = new TemplateItem(Identifier.of(MOD_ID, "itemGripMetal")).setTranslationKey(MOD_ID, "itemGripMetal");
        itemHandleMinigun = new TemplateItem(Identifier.of(MOD_ID, "itemHandleMinigun")).setTranslationKey(MOD_ID, "itemHandleMinigun");
        itemStockWood = new TemplateItem(Identifier.of(MOD_ID, "itemStockWood")).setTranslationKey(MOD_ID, "itemStockWood");
        itemStockMetal = new TemplateItem(Identifier.of(MOD_ID, "itemStockMetal")).setTranslationKey(MOD_ID, "itemStockMetal");
        itemReceiverMetal = new TemplateItem(Identifier.of(MOD_ID, "itemReceiverMetal")).setTranslationKey(MOD_ID, "itemReceiverMetal");
        itemReceiverDiamond = new TemplateItem(Identifier.of(MOD_ID, "itemReceiverDiamond")).setTranslationKey(MOD_ID, "itemReceiverDiamond");
        itemMagazine = new TemplateItem(Identifier.of(MOD_ID, "itemMagazine")).setTranslationKey(MOD_ID, "itemMagazine");
        itemScope = new TemplateItem(Identifier.of(MOD_ID, "itemScope")).setTranslationKey(MOD_ID, "itemScope");

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
//    public static Item itemGunAircraft;
//    public static Item itemGunAircraftRocket;
//    public static Item itemGunAircraftRocketPanzer;

    //AMMO
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
