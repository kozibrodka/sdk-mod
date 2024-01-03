
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletM4;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;



public class SdkItemGunM4 extends SdkItemGun
{

    public SdkItemGunM4(Identifier i)
    {
        super(i);
        firingSound = "sdk:m";
        requiredBullet = ItemListener.itemBulletLight;
        numBullets = 1;
        burstShots = 2;
        damage = 5;
        muzzleVelocity = 4F;
        spread = 0.5F;
        useDelay = 10;
        recoil = 1.0F;
        penetration = 0;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletM4(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return new SdkEntityBulletCasing(world, entity, f);
    }
}
