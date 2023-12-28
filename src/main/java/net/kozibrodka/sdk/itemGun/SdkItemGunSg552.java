
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletSg552;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;


public class SdkItemGunSg552 extends SdkItemGun
{

    public SdkItemGunSg552(Identifier i)
    {
        super(i);
        firingSound = "sdk:sg";
        requiredBullet = ItemListener.itemBulletMedium;
        numBullets = 1;
        damage = 8;
        muzzleVelocity = 6F;
        spread = 0.25F;
        useDelay = 5;
        recoil = 3F;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletSg552(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return new SdkEntityBulletCasing(world, entity, f);
    }
}
