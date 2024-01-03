package net.kozibrodka.sdk.events;

import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.kozibrodka.sdk.atv.SdkItemAtv;
import net.kozibrodka.sdk.item.*;
import net.kozibrodka.sdk.itemGun.*;
import net.kozibrodka.sdk.itemNade.*;
import net.kozibrodka.sdk_api.events.utils.SdkMap;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class ItemListener {

    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        //AMMO
        itemBulletLight = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletLight")).setTranslationKey(MOD_ID, "itemBulletLight").setMaxStackSize(32);
        itemBulletMedium = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletMedium")).setTranslationKey(MOD_ID, "itemBulletMedium").setMaxStackSize(8);
        itemBulletHeavy = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletHeavy")).setTranslationKey(MOD_ID, "itemBulletHeavy").setMaxStackSize(4);
        itemBulletRocket = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletRocket")).setTranslationKey(MOD_ID, "itemBulletRocket").setMaxStackSize(4);
        itemBulletRocketLaser = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletRocketLaser")).setTranslationKey(MOD_ID, "itemBulletRocketLaser").setMaxStackSize(4);
        itemBulletShell = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBulletShell")).setTranslationKey(MOD_ID, "itemBulletShell").setMaxStackSize(8);
        itemOil = (TemplateItem) new SdkItemOil(Identifier.of(MOD_ID, "itemOil")).setTranslationKey(MOD_ID, "itemOil");

        //GUNS
        itemGunAk47 = (TemplateItem) new SdkItemGunAk47(Identifier.of(MOD_ID, "itemGunAk47")).setTranslationKey(MOD_ID, "itemGunAk47");
        itemGunMp5 = (TemplateItem) new SdkItemGunMp5(Identifier.of(MOD_ID, "itemGunMp5")).setTranslationKey(MOD_ID, "itemGunMp5");
        itemGunShotgun = (TemplateItem) new SdkItemGunShotgun(Identifier.of(MOD_ID, "itemGunShotgun")).setTranslationKey(MOD_ID, "itemGunShotgun");
        itemGunDeagle = (TemplateItem) new SdkItemGunDeagle(Identifier.of(MOD_ID, "itemGunDeagle")).setTranslationKey(MOD_ID, "itemGunDeagle");
        itemGunSniper = (TemplateItem) new SdkItemGunSniper(Identifier.of(MOD_ID, "itemGunSniper")).setTranslationKey(MOD_ID, "itemGunSniper");
        itemGunM4 = (TemplateItem) new SdkItemGunM4(Identifier.of(MOD_ID, "itemGunM4")).setTranslationKey(MOD_ID, "itemGunM4");
        itemGunSg552 = (TemplateItem) new SdkItemGunSg552(Identifier.of(MOD_ID, "itemGunSg552")).setTranslationKey(MOD_ID, "itemGunSg552");
        itemGunMinigun = (TemplateItem) new SdkItemGunMinigun(Identifier.of(MOD_ID, "itemGunMinigun")).setTranslationKey(MOD_ID, "itemGunMinigun");
        itemGunLaser = (TemplateItem) new SdkItemGunLaser(Identifier.of(MOD_ID, "itemGunLaser")).setTranslationKey(MOD_ID, "itemGunLaser");
        itemGunFlamethrower = (TemplateItem) new SdkItemGunFlamethrower(Identifier.of(MOD_ID, "itemGunFlamethrower")).setTranslationKey(MOD_ID, "itemGunFlamethrower");
        itemGunRocketLauncherLaser = (TemplateItem) new SdkItemGunRocketLauncherLaser(Identifier.of(MOD_ID, "itemGunRocketLauncherLaser")).setTranslationKey(MOD_ID, "itemGunRocketLauncherLaser");
        itemGunRocketLauncher = (TemplateItem) new SdkItemGunRocketLauncher(Identifier.of(MOD_ID, "itemGunRocketLauncher")).setTranslationKey(MOD_ID, "itemGunRocketLauncher");

        //NADES
        itemGrenade = (TemplateItem) new SdkItemGrenade(Identifier.of(MOD_ID, "itemGrenade")).setTranslationKey(MOD_ID, "itemGrenade").setMaxStackSize(4);
        itemGrenadeHe = (TemplateItem) new SdkItemGrenadeHE(Identifier.of(MOD_ID, "itemGrenadeHe")).setTranslationKey(MOD_ID, "itemGrenadeHe").setMaxStackSize(4);
        itemGrenadeStun = (TemplateItem) new SdkItemGrenadeStun(Identifier.of(MOD_ID, "itemGrenadeStun")).setTranslationKey(MOD_ID, "itemGrenadeStun").setMaxStackSize(4);
        itemGrenadeSmoke = (TemplateItem) new SdkItemGrenadeSmoke(Identifier.of(MOD_ID, "itemGrenadeSmoke")).setTranslationKey(MOD_ID, "itemGrenadeSmoke").setMaxStackSize(4);
        itemGrenadeSticky = (TemplateItem) new SdkItemGrenadeSticky(Identifier.of(MOD_ID, "itemGrenadeSticky")).setTranslationKey(MOD_ID, "itemGrenadeSticky").setMaxStackSize(4);
        itemGrenadeIncendiary = (TemplateItem) new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiary")).setTranslationKey(MOD_ID, "itemGrenadeIncendiary").setMaxStackSize(4);
        itemGrenadeIncendiaryLit = (TemplateItem) new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiaryLit")).setTranslationKey(MOD_ID, "itemGrenadeIncendiaryLit").setMaxStackSize(4);

        //UTIL
        itemGoldCoin = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemGoldCoin")).setTranslationKey(MOD_ID, "itemGoldCoin");
        itemLightometer = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemLightometer")).setTranslationKey(MOD_ID, "itemLightometer");
        itemNightvisionGoggles = (TemplateArmorItem) new SdkItemNightvision(Identifier.of(MOD_ID, "itemNightvisionGoggles"), 1).setTranslationKey(MOD_ID, "itemNightvisionGoggles");
        itemScubaTank = (TemplateArmorItem) new SdkItemScuba(Identifier.of(MOD_ID, "itemScubaTank"), 1).setTranslationKey(MOD_ID, "itemScubaTank");
        itemParachute = (TemplateArmorItem) new SdkItemParachute(Identifier.of(MOD_ID, "itemParachute"), 1).setTranslationKey(MOD_ID, "itemParachute");
        itemTelescope = (TemplateItem) new SdkItemTelescope(Identifier.of(MOD_ID, "itemTelescope")).setTranslationKey(MOD_ID, "itemTelescope");

        itemAtv = (TemplateItem) new SdkItemAtv(Identifier.of(MOD_ID, "itemAtv")).setTranslationKey(MOD_ID, "itemAtv");
        itemAtvBody = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemAtvBody")).setTranslationKey(MOD_ID, "itemAtvBody");
        itemAtvWheel = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemAtvWheel")).setTranslationKey(MOD_ID, "itemAtvWheel");

        itemWrench = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemWrench")).setTranslationKey(MOD_ID, "itemWrench").setMaxStackSize(1).setDurability(15);
        itemSentry = (TemplateItem) new SdkItemSentry(Identifier.of(MOD_ID, "itemSentry")).setTranslationKey(MOD_ID, "itemSentry").setMaxStackSize(1).setDurability(0);
        itemOilDrop = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemOilDrop")).setTranslationKey(MOD_ID, "itemOilDrop");
        itemJetPack = (TemplateArmorItem) new SdkItemJetPack(Identifier.of(MOD_ID, "itemJetPack"), 1).setTranslationKey(MOD_ID, "itemJetPack");

        itemRope = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemRope")).setTranslationKey(MOD_ID, "itemRope");
        itemGrapplingHook = (TemplateItem) new SdkItemGrapplingHook(Identifier.of(MOD_ID, "itemGrapplingHook")).setTranslationKey(MOD_ID, "itemGrapplingHook");

        //PARTS
        itemMetalParts = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemMetalParts")).setTranslationKey(MOD_ID, "itemMetalParts");
        itemBarrelLong = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBarrelLong")).setTranslationKey(MOD_ID, "itemBarrelLong");
        itemBarrelShort = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBarrelShort")).setTranslationKey(MOD_ID, "itemBarrelShort");
        itemBarrelFat = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBarrelFat")).setTranslationKey(MOD_ID, "itemBarrelFat");
        itemBarrelShotgun = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBarrelShotgun")).setTranslationKey(MOD_ID, "itemBarrelShotgun");
        itemBarrelMinigun = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemBarrelMinigun")).setTranslationKey(MOD_ID, "itemBarrelMinigun");
        itemGripWood = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemGripWood")).setTranslationKey(MOD_ID, "itemGripWood");
        itemGripMetal = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemGripMetal")).setTranslationKey(MOD_ID, "itemGripMetal");
        itemHandleMinigun = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemHandleMinigun")).setTranslationKey(MOD_ID, "itemHandleMinigun");
        itemStockWood = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemStockWood")).setTranslationKey(MOD_ID, "itemStockWood");
        itemStockMetal = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemStockMetal")).setTranslationKey(MOD_ID, "itemStockMetal");
        itemReceiverMetal = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemReceiverMetal")).setTranslationKey(MOD_ID, "itemReceiverMetal");
        itemReceiverDiamond = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemReceiverDiamond")).setTranslationKey(MOD_ID, "itemReceiverDiamond");
        itemMagazine = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemMagazine")).setTranslationKey(MOD_ID, "itemMagazine");
        itemScope = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "itemScope")).setTranslationKey(MOD_ID, "itemScope");

        //MAPS
        SdkMap.nightvisionList.add(itemNightvisionGoggles.id);
        SdkMap.parachuteList.add(itemParachute.id);
        SdkMap.scubaTankList.add(itemScubaTank.id);
        SdkMap.telescopeList.add(itemTelescope.id);
        SdkMap.oilList.add(itemOil.id);
        SdkMap.jetpackList.add(itemJetPack.id);

        SdkMap.noReloadList.add(itemGunMinigun);
        SdkMap.noReloadList.add(itemGunLaser);
        SdkMap.noReloadList.add(itemGunFlamethrower);

        SdkMap.redAmmoList.add(itemGunLaser);

        SdkMap.oilAmmoList.add(itemGunFlamethrower);

        SdkMap.sniperList.add(itemGunSniper);
        SdkMap.scopedList.add(itemGunSniper);
        SdkMap.scopedList.add(itemGunSg552);

        //TODO: ADD TO MAPS
    }

    //BRONIE
    public static TemplateItem itemGunAk47;
    public static TemplateItem itemGunMp5;
    public static TemplateItem itemGunShotgun;
    public static TemplateItem itemGunDeagle;
    public static TemplateItem itemGunSniper;
    public static TemplateItem itemGunM4;
    public static TemplateItem itemGunSg552;
    public static TemplateItem itemGunMinigun;
    public static TemplateItem itemGunLaser;
    public static TemplateItem itemGunFlamethrower;
    public static TemplateItem itemGunRocketLauncherLaser;
    public static TemplateItem itemGunRocketLauncher;
//    public static TemplateItem itemGunAircraft;
//    public static TemplateItem itemGunAircraftRocket;
//    public static TemplateItem itemGunAircraftRocketPanzer;

    //AMMO
    public static TemplateItem itemBulletLight;
    public static TemplateItem itemBulletMedium;
    public static TemplateItem itemBulletHeavy;
    public static TemplateItem itemOil;
    public static TemplateItem itemBulletRocket;
    public static TemplateItem itemBulletRocketLaser;
    public static TemplateItem itemBulletShell;


    //granaty
    public static TemplateItem itemGrenade;
    public static TemplateItem itemGrenadeHe;
    public static TemplateItem itemGrenadeStun;
    public static TemplateItem itemGrenadeSmoke;
    public static TemplateItem itemGrenadeSticky;
    public static TemplateItem itemGrenadeIncendiary;
    public static TemplateItem itemGrenadeIncendiaryLit;

    //util
    public static TemplateItem itemGoldCoin;
    public static TemplateItem itemLightometer;
    public static TemplateArmorItem itemNightvisionGoggles;
    public static TemplateArmorItem itemScubaTank;
    public static TemplateArmorItem itemParachute;
    public static TemplateItem itemTelescope;

    public static TemplateArmorItem itemJetPack;
    public static TemplateItem itemWrench;
    public static TemplateItem itemAtv;
    public static TemplateItem itemOilDrop;
    public static TemplateItem itemSentry;

    public static TemplateItem itemRope;
    public static TemplateItem itemGrapplingHook;

    //parts
    public static TemplateItem itemMetalParts;
    public static TemplateItem itemBarrelLong;
    public static TemplateItem itemBarrelShort;
    public static TemplateItem itemBarrelFat;
    public static TemplateItem itemBarrelShotgun;
    public static TemplateItem itemBarrelMinigun;
    public static TemplateItem itemGripWood;
    public static TemplateItem itemGripMetal;
    public static TemplateItem itemHandleMinigun;
    public static TemplateItem itemStockWood;
    public static TemplateItem itemStockMetal;
    public static TemplateItem itemReceiverMetal;
    public static TemplateItem itemReceiverDiamond;
    public static TemplateItem itemMagazine;
    public static TemplateItem itemScope;
    public static TemplateItem itemAtvBody;
    public static TemplateItem itemAtvWheel;

}
