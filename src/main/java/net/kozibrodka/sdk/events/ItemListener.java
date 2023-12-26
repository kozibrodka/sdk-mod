package net.kozibrodka.sdk.events;

import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.kozibrodka.sdk.atv.SdkItemAtv;
import net.kozibrodka.sdk.item.*;
import net.kozibrodka.sdk.itemGun.SdkItemGunAk47;
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

        //NADES

        //UTIL //
        itemGoldCoin = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemGoldCoin")).setTranslationKey(MOD_ID, "itemGoldCoin");
        itemLightometer = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemLightometer")).setTranslationKey(MOD_ID, "itemLightometer");
        itemNightvisionGoggles = (TemplateArmour) new SdkItemNightvision(Identifier.of(MOD_ID, "itemNightvisionGoggles"), 1).setTranslationKey(MOD_ID, "itemNightvisionGoggles");
        itemScubaTank = (TemplateArmour) new SdkItemScuba(Identifier.of(MOD_ID, "itemScubaTank"), 1).setTranslationKey(MOD_ID, "itemScubaTank");
        itemParachute = (TemplateArmour) new SdkItemParachute(Identifier.of(MOD_ID, "itemParachute"), 1).setTranslationKey(MOD_ID, "itemParachute");
        itemJetPack = (TemplateArmour) new SdkItemJetPack(Identifier.of(MOD_ID, "itemJetPack"), 1).setTranslationKey(MOD_ID, "itemJetPack");
        itemTelescope = (TemplateItemBase) new SdkItemTelescope(Identifier.of(MOD_ID, "itemTelescope")).setTranslationKey(MOD_ID, "itemTelescope");
        itemWrench = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemWrench")).setTranslationKey(MOD_ID, "itemWrench").setMaxStackSize(1).setDurability(15);
        itemAtv = (TemplateItemBase) new SdkItemAtv(Identifier.of(MOD_ID, "itemAtv")).setTranslationKey(MOD_ID, "itemAtv");

        itemRope = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "itemRope")).setTranslationKey(MOD_ID, "itemRope");
        itemGrapplingHook = (TemplateItemBase) new SdkItemGrapplingHook(Identifier.of(MOD_ID, "itemGrapplingHook")).setTranslationKey(MOD_ID, "itemGrapplingHook");

        SdkMap.nightvisionList.add(itemNightvisionGoggles.id);
        SdkMap.parachuteList.add(itemParachute.id);
        SdkMap.scubaTankList.add(itemScubaTank.id);
        SdkMap.telescopeList.add(itemTelescope.id);
        SdkMap.oilList.add(itemOil.id);
        SdkMap.jetpackList.add(itemJetPack.id);

//        SdkMap.noReloadList.add(jakistamitem.id);
//        SdkMap.redAmmoList.add(jakistamitem.id);
//        SdkMap.oilAmmoList.add(jakistamitem.id);
//        SdkMap.sniperList.add(jakistamitem.id);
//        SdkMap.scopedList.add(jakistamitem.id);

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
    public static TemplateItemBase itemGunAircraft;
    public static TemplateItemBase itemGunAircraftRocket;
    public static TemplateItemBase itemGunAircraftRocketPanzer;

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
    public static TemplateArmour itemJetPack;
    public static TemplateItemBase itemGoldCoin;
    public static TemplateItemBase itemLightometer;
    public static TemplateArmour itemNightvisionGoggles;
    public static TemplateArmour itemScubaTank;
    public static TemplateArmour itemParachute;
    public static TemplateItemBase itemTelescope;

    public static TemplateItemBase itemWrench;
    public static TemplateItemBase itemAtv;

    public static TemplateItemBase itemRope;
    public static TemplateItemBase itemGrapplingHook;

}
