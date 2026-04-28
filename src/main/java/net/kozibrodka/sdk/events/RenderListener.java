package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkRenderAtv;
import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.entity.SdkEntityParachute;
import net.kozibrodka.sdk.entityBullet.*;
import net.kozibrodka.sdk.entitySentry.SdkEntitySentry;
import net.kozibrodka.sdk.model.SdkModelParachute;
import net.kozibrodka.sdk.model.SdkModelSentry;
import net.kozibrodka.sdk.render.*;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class RenderListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

//    @EventListener
//    public void registerAnimation(TextureRegisterEvent event) {
//        Atlases.getGuiItems().addTextureBinder(Identifier.of(MOD_ID, "blockOil"), SdkTextureLightometerFX::new);
//    }

    @EventListener
    public static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(EntityBulletCasing.class, new SdkRenderBulletCasing());
        event.renderers.put(EntityShellCasing.class, new SdkRenderShellCasing());
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
//        event.renderers.put(SdkEntitySentry.class, new SdkRenderSentry());
        event.renderers.put(SdkEntitySentry.class, new LivingEntityRenderer(new SdkModelSentry(), 0.5F));
        event.renderers.put(SdkEntityParachute.class, new SdkRenderParachute(new SdkModelParachute()));
    }

    @EventListener
    public static void registerTileEntityRenderers(BlockEntityRendererRegisterEvent event){
        event.renderers.put(SdkTileEntityPlaque.class, new SdkTileEntityRendererPlaque());
    }
}
