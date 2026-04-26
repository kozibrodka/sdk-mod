package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityShell;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityBulletRocket extends SdkEntityShell implements EntitySpawnDataProvider {

    public SdkEntityBulletRocket(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocket(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocket(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3, float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    @Override
    public String getServerExploSound() {
        return ((SdkItemGun) ItemListener.itemGunRocketLauncher).explosionSound;
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncher).firingSound, ((SdkItemGun)ItemListener.itemGunRocketLauncher).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletRocket");
    }
}
