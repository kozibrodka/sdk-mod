package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityBulletShot extends SdkEntityBullet implements EntitySpawnDataProvider {

    public SdkEntityBulletShot(World world) {
        super(world);
        setBoundingBoxSpacing(0.03125F, 0.03125F);
    }

    public SdkEntityBulletShot(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.03125F, 0.03125F);
    }

    public SdkEntityBulletShot(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                               float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.03125F, 0.03125F);
    }

    @Override
    public void playServerSound(World world) {
        /// Bullets > 1, no playServerSound
//        world.playSound(this, ((SdkItemGun) ItemListener.itemGunShotgun).firingSound, ((SdkItemGun) ItemListener.itemGunShotgun).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletShot");
    }
}
