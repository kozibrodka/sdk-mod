package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkRenderAtv;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;
import net.kozibrodka.sdk.entityBullet.*;

public class TextureListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.itemBulletLight.setTexture(Identifier.of(MOD_ID, "item/itemBullet9mm"));
        ItemListener.itemBulletHeavy.setTexture(Identifier.of(MOD_ID, "item/itemBullet50Cal"));
        ItemListener.itemBulletMedium.setTexture(Identifier.of(MOD_ID, "item/itemBullet357"));
        ItemListener.itemBulletRocket.setTexture(Identifier.of(MOD_ID, "item/itemBulletRocket"));
        ItemListener.itemBulletRocketLaser.setTexture(Identifier.of(MOD_ID, "item/itemBulletRocketLaser"));
        ItemListener.itemBulletShell.setTexture(Identifier.of(MOD_ID, "item/itemBulletShell"));
        ItemListener.itemOil.setTexture(Identifier.of(MOD_ID, "item/itemOil"));

        ItemListener.itemGunAk47.setTexture(Identifier.of(MOD_ID, "item/itemGunAk47"));

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


    @EventListener
    private static void registerEntityRenderers(EntityRendererRegisterEvent event) {
//        event.renderers.put(SdkEntityBulletCasing.class, new SdkRenderBulletCasing());
//        event.renderers.put(SdkEntityBulletCasingShell.class, new SdkRenderBulletCasingShell());
//        event.renderers.put(SdkEntityBulletAk47.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletDeagle.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletFlame.class, new SdkRenderBulletFlame());
//        event.renderers.put(SdkEntityBulletM4.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletMinigun.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletMp5.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletSg552.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletShot.class, new SdkRenderBulletShot());
//        event.renderers.put(SdkEntityBulletSniper.class, new SdkRenderBullet());
//        event.renderers.put(SdkEntityBulletRocket.class, new SdkRenderBulletRocket());
//        event.renderers.put(SdkEntityBulletRocketLaser.class, new SdkRenderBulletRocketLaser());
//        event.renderers.put(SdkEntityBulletLaser.class, new SdkRenderBulletLaser());
        event.renderers.put(SdkEntityAtv.class, new SdkRenderAtv());
    }
}
