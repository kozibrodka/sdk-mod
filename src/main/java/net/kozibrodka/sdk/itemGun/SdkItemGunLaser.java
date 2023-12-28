
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletLaser;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;


public class SdkItemGunLaser extends SdkItemGun
{

    public SdkItemGunLaser(Identifier i)
    {
        super(i);
        firingSound = "sdk:laser";
        requiredBullet = ItemBase.redstoneDust;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 1.5F;
        spread = 0.0F;
        useDelay = 10;
        recoil = 0.0F;
        soundRangeFactor = 2.0F;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletLaser(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return null;
    }
}
