package net.kozibrodka.sdk.entityBullet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityBulletSniper extends SdkEntityBullet implements EntitySpawnDataProvider
{

    public SdkEntityBulletSniper(World world)
    {
        super(world);
    }

    public SdkEntityBulletSniper(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public SdkEntityBulletSniper(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                 float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setPositionAndAnglesAvoidEntities(double x, double y, double z, float pitch, float yaw, int interpolationSteps) {
        this.setPosition(x, y, z);
        this.setRotation(pitch, yaw);
    }

    @Override
    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunSniper).firingSound, ((SdkItemGun)ItemListener.itemGunSniper).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletSniper");
    }
}