package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;

import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.kozibrodka.sdk.events.ItemListener;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;


public class SdkEntityBulletAk47 extends SdkEntityBullet implements EntitySpawnDataProvider
{

    public SdkEntityBulletAk47(World world)
    {
        super(world);
    }

    public SdkEntityBulletAk47(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public SdkEntityBulletAk47(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                               float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
    }

    @Override
    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunAk47).firingSound, ((SdkItemGun)ItemListener.itemGunAk47).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletAk47");
    }
}
