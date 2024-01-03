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
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import net.modificationstation.stationapi.api.template.item.armour.TemplateArmour;
import net.modificationstation.stationapi.api.util.Null;

public class ItemListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        //AMMO
        itemBulletLight = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletLight")).setTranslationKey(MOD_ID, "itemBulletLight").setMaxStackSize(32);
        itemBulletMedium = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletMedium")).setTranslationKey(MOD_ID, "itemBulletMedium").setMaxStackSize(8);
        itemBulletHeavy = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletHeavy")).setTranslationKey(MOD_ID, "itemBulletHeavy").setMaxStackSize(4);
        itemBulletRocket = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletRocket")).setTranslationKey(MOD_ID, "itemBulletRocket").setMaxStackSize(4);
        itemBulletRocketLaser = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletRocketLaser")).setTranslationKey(MOD_ID, "itemBulletRocketLaser").setMaxStackSize(4);
        itemBulletShell = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBulletShell")).setTranslationKey(MOD_ID, "itemBulletShell").setMaxStackSize(8);
        itemOil = (TemplateItemBase) new SdkItemOil(Identifier.of(MOD_ID, "itemOil")).setTranslationKey(MOD_ID, "itemOil");

        //GUNS
        itemGunAk47 = (TemplateItemBase) new SdkItemGunAk47(Identifier.of(MOD_ID, "itemGunAk47")).setTranslationKey(MOD_ID, "itemGunAk47");
        itemGunMp5 = (TemplateItemBase) new SdkItemGunMp5(Identifier.of(MOD_ID, "itemGunMp5")).setTranslationKey(MOD_ID, "itemGunMp5");
        itemGunShotgun = (TemplateItemBase) new SdkItemGunShotgun(Identifier.of(MOD_ID, "itemGunShotgun")).setTranslationKey(MOD_ID, "itemGunShotgun");
        itemGunDeagle = (TemplateItemBase) new SdkItemGunDeagle(Identifier.of(MOD_ID, "itemGunDeagle")).setTranslationKey(MOD_ID, "itemGunDeagle");
        itemGunSniper = (TemplateItemBase) new SdkItemGunSniper(Identifier.of(MOD_ID, "itemGunSniper")).setTranslationKey(MOD_ID, "itemGunSniper");
        itemGunM4 = (TemplateItemBase) new SdkItemGunM4(Identifier.of(MOD_ID, "itemGunM4")).setTranslationKey(MOD_ID, "itemGunM4");
        itemGunSg552 = (TemplateItemBase) new SdkItemGunSg552(Identifier.of(MOD_ID, "itemGunSg552")).setTranslationKey(MOD_ID, "itemGunSg552");
        itemGunMinigun = (TemplateItemBase) new SdkItemGunMinigun(Identifier.of(MOD_ID, "itemGunMinigun")).setTranslationKey(MOD_ID, "itemGunMinigun");
        itemGunLaser = (TemplateItemBase) new SdkItemGunLaser(Identifier.of(MOD_ID, "itemGunLaser")).setTranslationKey(MOD_ID, "itemGunLaser");
        itemGunFlamethrower = (TemplateItemBase) new SdkItemGunFlamethrower(Identifier.of(MOD_ID, "itemGunFlamethrower")).setTranslationKey(MOD_ID, "itemGunFlamethrower");
        itemGunRocketLauncherLaser = (TemplateItemBase) new SdkItemGunRocketLauncherLaser(Identifier.of(MOD_ID, "itemGunRocketLauncherLaser")).setTranslationKey(MOD_ID, "itemGunRocketLauncherLaser");
        itemGunRocketLauncher = (TemplateItemBase) new SdkItemGunRocketLauncher(Identifier.of(MOD_ID, "itemGunRocketLauncher")).setTranslationKey(MOD_ID, "itemGunRocketLauncher");

        //NADES
        itemGrenade = (TemplateItemBase) new SdkItemGrenade(Identifier.of(MOD_ID, "itemGrenade")).setTranslationKey(MOD_ID, "itemGrenade").setMaxStackSize(4);
        itemGrenadeHe = (TemplateItemBase) new SdkItemGrenadeHE(Identifier.of(MOD_ID, "itemGrenadeHe")).setTranslationKey(MOD_ID, "itemGrenadeHe").setMaxStackSize(4);
        itemGrenadeStun = (TemplateItemBase) new SdkItemGrenadeStun(Identifier.of(MOD_ID, "itemGrenadeStun")).setTranslationKey(MOD_ID, "itemGrenadeStun").setMaxStackSize(4);
        itemGrenadeSmoke = (TemplateItemBase) new SdkItemGrenadeSmoke(Identifier.of(MOD_ID, "itemGrenadeSmoke")).setTranslationKey(MOD_ID, "itemGrenadeSmoke").setMaxStackSize(4);
        itemGrenadeSticky = (TemplateItemBase) new SdkItemGrenadeSticky(Identifier.of(MOD_ID, "itemGrenadeSticky")).setTranslationKey(MOD_ID, "itemGrenadeSticky").setMaxStackSize(4);
        itemGrenadeIncendiary = (TemplateItemBase) new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiary")).setTranslationKey(MOD_ID, "itemGrenadeIncendiary").setMaxStackSize(4);
        itemGrenadeIncendiaryLit = (TemplateItemBase) new SdkItemGrenadeIncendiary(Identifier.of(MOD_ID, "itemGrenadeIncendiaryLit")).setTranslationKey(MOD_ID, "itemGrenadeIncendiaryLit").setMaxStackSize(4);

        //UTIL
        itemGoldCoin = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemGoldCoin")).setTranslationKey(MOD_ID, "itemGoldCoin");
        itemLightometer = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemLightometer")).setTranslationKey(MOD_ID, "itemLightometer");
        itemNightvisionGoggles = (TemplateArmour) new SdkItemNightvision(Identifier.of(MOD_ID, "itemNightvisionGoggles"), 1).setTranslationKey(MOD_ID, "itemNightvisionGoggles");
        itemScubaTank = (TemplateArmour) new SdkItemScuba(Identifier.of(MOD_ID, "itemScubaTank"), 1).setTranslationKey(MOD_ID, "itemScubaTank");
        itemParachute = (TemplateArmour) new SdkItemParachute(Identifier.of(MOD_ID, "itemParachute"), 1).setTranslationKey(MOD_ID, "itemParachute");
        itemTelescope = (TemplateItemBase) new SdkItemTelescope(Identifier.of(MOD_ID, "itemTelescope")).setTranslationKey(MOD_ID, "itemTelescope");

        itemAtv = (TemplateItemBase) new SdkItemAtv(Identifier.of(MOD_ID, "itemAtv")).setTranslationKey(MOD_ID, "itemAtv");
        itemAtvBody = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemAtvBody")).setTranslationKey(MOD_ID, "itemAtvBody");
        itemAtvWheel = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemAtvWheel")).setTranslationKey(MOD_ID, "itemAtvWheel");

        itemWrench = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemWrench")).setTranslationKey(MOD_ID, "itemWrench").setMaxStackSize(1).setDurability(15);
        itemSentry = (TemplateItemBase) new SdkItemSentry(Identifier.of(MOD_ID, "itemSentry")).setTranslationKey(MOD_ID, "itemSentry").setMaxStackSize(1).setDurability(0);
        itemOilDrop = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemOilDrop")).setTranslationKey(MOD_ID, "itemOilDrop");
        itemJetPack = (TemplateArmour) new SdkItemJetPack(Identifier.of(MOD_ID, "itemJetPack"), 1).setTranslationKey(MOD_ID, "itemJetPack");

        itemRope = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemRope")).setTranslationKey(MOD_ID, "itemRope");
        itemGrapplingHook = (TemplateItemBase) new SdkItemGrapplingHook(Identifier.of(MOD_ID, "itemGrapplingHook")).setTranslationKey(MOD_ID, "itemGrapplingHook");

        //PARTS
        itemMetalParts = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemMetalParts")).setTranslationKey(MOD_ID, "itemMetalParts");
        itemBarrelLong = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBarrelLong")).setTranslationKey(MOD_ID, "itemBarrelLong");
        itemBarrelShort = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBarrelShort")).setTranslationKey(MOD_ID, "itemBarrelShort");
        itemBarrelFat = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBarrelFat")).setTranslationKey(MOD_ID, "itemBarrelFat");
        itemBarrelShotgun = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBarrelShotgun")).setTranslationKey(MOD_ID, "itemBarrelShotgun");
        itemBarrelMinigun = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemBarrelMinigun")).setTranslationKey(MOD_ID, "itemBarrelMinigun");
        itemGripWood = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemGripWood")).setTranslationKey(MOD_ID, "itemGripWood");
        itemGripMetal = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemGripMetal")).setTranslationKey(MOD_ID, "itemGripMetal");
        itemHandleMinigun = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemHandleMinigun")).setTranslationKey(MOD_ID, "itemHandleMinigun");
        itemStockWood = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemStockWood")).setTranslationKey(MOD_ID, "itemStockWood");
        itemStockMetal = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemStockMetal")).setTranslationKey(MOD_ID, "itemStockMetal");
        itemReceiverMetal = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemReceiverMetal")).setTranslationKey(MOD_ID, "itemReceiverMetal");
        itemReceiverDiamond = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemReceiverDiamond")).setTranslationKey(MOD_ID, "itemReceiverDiamond");
        itemMagazine = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemMagazine")).setTranslationKey(MOD_ID, "itemMagazine");
        itemScope = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemScope")).setTranslationKey(MOD_ID, "itemScope");

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
    public static TemplateItemBase itemGunAk47;
    public static TemplateItemBase itemGunMp5;
    public static TemplateItemBase itemGunShotgun;
    public static TemplateItemBase itemGunDeagle;
    public static TemplateItemBase itemGunSniper;
    public static TemplateItemBase itemGunM4;
    public static TemplateItemBase itemGunSg552;
    public static TemplateItemBase itemGunMinigun;
    public static TemplateItemBase itemGunLaser;
    public static TemplateItemBase itemGunFlamethrower;
    public static TemplateItemBase itemGunRocketLauncherLaser;
    public static TemplateItemBase itemGunRocketLauncher;
//    public static TemplateItemBase itemGunAircraft;
//    public static TemplateItemBase itemGunAircraftRocket;
//    public static TemplateItemBase itemGunAircraftRocketPanzer;

    //AMMO
    public static TemplateItemBase itemBulletLight;
    public static TemplateItemBase itemBulletMedium;
    public static TemplateItemBase itemBulletHeavy;
    public static TemplateItemBase itemOil;
    public static TemplateItemBase itemBulletRocket;
    public static TemplateItemBase itemBulletRocketLaser;
    public static TemplateItemBase itemBulletShell;


    //granaty
    public static TemplateItemBase itemGrenade;
    public static TemplateItemBase itemGrenadeHe;
    public static TemplateItemBase itemGrenadeStun;
    public static TemplateItemBase itemGrenadeSmoke;
    public static TemplateItemBase itemGrenadeSticky;
    public static TemplateItemBase itemGrenadeIncendiary;
    public static TemplateItemBase itemGrenadeIncendiaryLit;

    //util
    public static TemplateItemBase itemGoldCoin;
    public static TemplateItemBase itemLightometer;
    public static TemplateArmour itemNightvisionGoggles;
    public static TemplateArmour itemScubaTank;
    public static TemplateArmour itemParachute;
    public static TemplateItemBase itemTelescope;

    public static TemplateArmour itemJetPack;
    public static TemplateItemBase itemWrench;
    public static TemplateItemBase itemAtv;
    public static TemplateItemBase itemOilDrop;
    public static TemplateItemBase itemSentry;

    public static TemplateItemBase itemRope;
    public static TemplateItemBase itemGrapplingHook;

    //parts
    public static TemplateItemBase itemMetalParts;
    public static TemplateItemBase itemBarrelLong;
    public static TemplateItemBase itemBarrelShort;
    public static TemplateItemBase itemBarrelFat;
    public static TemplateItemBase itemBarrelShotgun;
    public static TemplateItemBase itemBarrelMinigun;
    public static TemplateItemBase itemGripWood;
    public static TemplateItemBase itemGripMetal;
    public static TemplateItemBase itemHandleMinigun;
    public static TemplateItemBase itemStockWood;
    public static TemplateItemBase itemStockMetal;
    public static TemplateItemBase itemReceiverMetal;
    public static TemplateItemBase itemReceiverDiamond;
    public static TemplateItemBase itemMagazine;
    public static TemplateItemBase itemScope;
    public static TemplateItemBase itemAtvBody;
    public static TemplateItemBase itemAtvWheel;

}
