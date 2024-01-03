package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkRenderAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.entitySentry.SdkEntitySentry;
import net.kozibrodka.sdk.render.*;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasingShell;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.ClockTextureBinder;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.kozibrodka.sdk.entityBullet.*;

public class TextureListener {

    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.itemBulletLight.setTexture(Identifier.of(MOD_ID, "item/itemBullet9mm"));
        ItemListener.itemBulletHeavy.setTexture(Identifier.of(MOD_ID, "item/itemBullet50Cal"));
        ItemListener.itemBulletMedium.setTexture(Identifier.of(MOD_ID, "item/itemBullet357"));
        ItemListener.itemBulletRocket.setTexture(Identifier.of(MOD_ID, "item/itemBulletRocket"));
        ItemListener.itemBulletRocketLaser.setTexture(Identifier.of(MOD_ID, "item/itemBulletRocketLaser"));
        ItemListener.itemBulletShell.setTexture(Identifier.of(MOD_ID, "item/itemBulletShell"));

        ItemListener.itemGrenade.setTexture(Identifier.of(MOD_ID, "item/itemGrenade"));
        ItemListener.itemGrenadeHe.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeHE"));
        ItemListener.itemGrenadeStun.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeStun"));
        ItemListener.itemGrenadeSmoke.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeSmoke"));
        ItemListener.itemGrenadeSticky.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeSticky"));
        ItemListener.itemGrenadeIncendiary.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeIncendiary"));
        ItemListener.itemGrenadeIncendiaryLit.setTexture(Identifier.of(MOD_ID, "item/itemGrenadeIncendiaryLit"));

        ItemListener.itemGunAk47.setTexture(Identifier.of(MOD_ID, "item/itemGunAk47"));
        ItemListener.itemGunMp5.setTexture(Identifier.of(MOD_ID, "item/itemGunMp5"));
        ItemListener.itemGunShotgun.setTexture(Identifier.of(MOD_ID, "item/itemGunShotgun"));
        ItemListener.itemGunDeagle.setTexture(Identifier.of(MOD_ID, "item/itemGunDeagle"));
        ItemListener.itemGunSniper.setTexture(Identifier.of(MOD_ID, "item/itemGunSniper"));
        ItemListener.itemGunM4.setTexture(Identifier.of(MOD_ID, "item/itemGunM4"));
        ItemListener.itemGunSg552.setTexture(Identifier.of(MOD_ID, "item/itemGunSg552"));
        ItemListener.itemGunMinigun.setTexture(Identifier.of(MOD_ID, "item/itemGunMinigun"));
        ItemListener.itemGunLaser.setTexture(Identifier.of(MOD_ID, "item/itemGunLaser"));
        ItemListener.itemGunFlamethrower.setTexture(Identifier.of(MOD_ID, "item/itemGunFlamethrower"));
        ItemListener.itemGunRocketLauncherLaser.setTexture(Identifier.of(MOD_ID, "item/itemGunRocketLauncherLaser"));
        ItemListener.itemGunRocketLauncher.setTexture(Identifier.of(MOD_ID, "item/itemGunRocketLauncher"));

        ItemListener.itemWrench.setTexture(Identifier.of(MOD_ID, "item/itemWrench"));
        ItemListener.itemAtv.setTexture(Identifier.of(MOD_ID, "item/itemAtv"));
        ItemListener.itemTelescope.setTexture(Identifier.of(MOD_ID, "item/itemTelescope"));
        ItemListener.itemJetPack.setTexture(Identifier.of(MOD_ID, "item/itemJetPack"));
        ItemListener.itemGoldCoin.setTexture(Identifier.of(MOD_ID, "item/itemGoldCoin"));
        ItemListener.itemLightometer.setTexture(Identifier.of(MOD_ID, "item/itemLightometer"));
        ItemListener.itemNightvisionGoggles.setTexture(Identifier.of(MOD_ID, "item/itemNightvisionGoggles"));
        ItemListener.itemScubaTank.setTexture(Identifier.of(MOD_ID, "item/itemScubaTank"));
        ItemListener.itemParachute.setTexture(Identifier.of(MOD_ID, "item/itemParachute"));
        ItemListener.itemTelescope.setTexture(Identifier.of(MOD_ID, "item/itemTelescope"));
        ItemListener.itemOil.setTexture(Identifier.of(MOD_ID, "item/itemOil"));
        ItemListener.itemGrapplingHook.setTexture(Identifier.of(MOD_ID, "item/itemGrapplingHook"));
        ItemListener.itemRope.setTexture(Identifier.of(MOD_ID, "item/itemRope"));
        ItemListener.itemOilDrop.setTexture(Identifier.of(MOD_ID, "item/itemOilDrop"));
        ItemListener.itemAtvBody.setTexture(Identifier.of(MOD_ID, "item/itemAtvBody"));
        ItemListener.itemAtvWheel.setTexture(Identifier.of(MOD_ID, "item/itemAtvWheel"));

        ItemListener.itemMetalParts.setTexture(Identifier.of(MOD_ID, "item/itemMetalParts"));
        ItemListener.itemBarrelLong.setTexture(Identifier.of(MOD_ID, "item/itemBarrelLong"));
        ItemListener.itemBarrelShort.setTexture(Identifier.of(MOD_ID, "item/itemBarrelShort"));
        ItemListener.itemBarrelFat.setTexture(Identifier.of(MOD_ID, "item/itemBarrelFat"));
        ItemListener.itemBarrelShotgun.setTexture(Identifier.of(MOD_ID, "item/itemBarrelShotgun"));
        ItemListener.itemBarrelMinigun.setTexture(Identifier.of(MOD_ID, "item/itemBarrelMinigun"));
        ItemListener.itemGripWood.setTexture(Identifier.of(MOD_ID, "item/itemGripWood"));
        ItemListener.itemGripMetal.setTexture(Identifier.of(MOD_ID, "item/itemGripMetal"));
        ItemListener.itemHandleMinigun.setTexture(Identifier.of(MOD_ID, "item/itemHandleMinigun"));
        ItemListener.itemStockWood.setTexture(Identifier.of(MOD_ID, "item/itemStockWood"));
        ItemListener.itemStockMetal.setTexture(Identifier.of(MOD_ID, "item/itemStockMetal"));
        ItemListener.itemReceiverMetal.setTexture(Identifier.of(MOD_ID, "item/itemReceiverMetal"));
        ItemListener.itemReceiverDiamond.setTexture(Identifier.of(MOD_ID, "item/itemReceiverDiamond"));
        ItemListener.itemMagazine.setTexture(Identifier.of(MOD_ID, "item/itemMagazine"));
        ItemListener.itemScope.setTexture(Identifier.of(MOD_ID, "item/itemScope"));
        ItemListener.itemSentry.setTexture(Identifier.of(MOD_ID, "item/blockSentry"));

        lighter_top = registerBlockTexture("block/blockLighterTop");
        lighter_side = registerBlockTexture("block/blockLighterSide");
        cannon = registerBlockTexture("block/blockCannon");
        grinder = registerBlockTexture("block/blockGrinder");
        rope = registerBlockTexture("block/blockRope");
        nuke = registerBlockTexture("block/blockNuke");
        grappling = registerBlockTexture("block/blockGrapplingHook");
        oil_juction = registerBlockTexture("block/blockOilJunction");
        oil_line = registerBlockTexture("block/blockOilLine");
        empty = registerBlockTexture("block/fcBlockEmpty");
        plaque = registerBlockTexture("block/blockPlaque");

        BlockListener.blockRope.asItem().setTexturePosition(rope);
        BlockListener.blockOil.asItem().setTexturePosition(oil_juction);
        BlockListener.blockPlaque.asItem().setTexturePosition(plaque);
        BlockListener.blockRope.asItem().setTexturePosition(registerBlockTexture("block/blockRope"));
        BlockListener.blockOil.asItem().setTexturePosition(oil_juction);
        BlockListener.blockPlaque.asItem().setTexturePosition(plaque);
    }

    private int registerBlockTexture(String s) {
        if(s == null) {
            return 0;
        }
        return Atlases.getStationTerrain().addTexture(Identifier.of(MOD_ID, s)).index;
    }

    private int registerItemTexture(String s) {
        if(s == null) {
            return 0;
        }
        return Atlases.getStationGuiItems().addTexture(Identifier.of(MOD_ID, s)).index;
    }

    public static int lighter_top;
    public static int lighter_side;
    public static int cannon;
    public static int grinder;
    public static int rope;
    public static int nuke;
    public static int grappling;
    public static int oil_juction;
    public static int oil_line;
    public static int empty;
    public static int plaque;

//    @EventListener
//    public void registerAnimation(TextureRegisterEvent event) {
//        Atlases.getGuiItems().addTextureBinder(Identifier.of(MOD_ID, "blockOil"), "as", SdkTextureLightometerFX::new);
//    }

    @EventListener
    private static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(SdkEntityBulletCasing.class, new SdkRenderBulletCasing());
        event.renderers.put(SdkEntityBulletCasingShell.class, new SdkRenderBulletCasingShell());
        event.renderers.put(SdkEntityBulletAk47.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletDeagle.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletFlame.class, new SdkRenderBulletFlame());
        event.renderers.put(SdkEntityBulletM4.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletMinigun.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletMp5.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletSg552.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletShot.class, new SdkRenderBulletShot());
        event.renderers.put(SdkEntityBulletSniper.class, new SdkRenderBullet());
        event.renderers.put(SdkEntityBulletRocket.class, new SdkRenderBulletRocket());
        event.renderers.put(SdkEntityBulletRocketLaser.class, new SdkRenderBulletRocketLaser());
        event.renderers.put(SdkEntityBulletLaser.class, new SdkRenderBulletLaser());
        event.renderers.put(SdkEntityAtv.class, new SdkRenderAtv());
        event.renderers.put(SdkEntityGrapplingHook.class, new SdkRenderGrapplingHook());
        event.renderers.put(SdkEntityNukePrimed.class, new SdkRenderNukePrimed());
        event.renderers.put(SdkEntitySentry.class, new SdkRenderSentry());
    }

    @EventListener
    private static void registerTileEntityRenderers(BlockEntityRendererRegisterEvent event){
        event.renderers.put(SdkTileEntityPlaque.class, new SdkTileEntityRendererPlaque());
    }
}
