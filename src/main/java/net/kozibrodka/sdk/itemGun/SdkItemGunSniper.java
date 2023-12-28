
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletSniper;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;


public class SdkItemGunSniper extends SdkItemGun
{

    public SdkItemGunSniper(Identifier i)
    {
        super(i);
        firingSound = "sdk:sniper";
        requiredBullet = ItemListener.itemBulletHeavy;
        numBullets = 1;
        damage = 12;
        muzzleVelocity = 8F;
        spread = 0.0F;
        useDelay = 20;
        recoil = 8F;
        soundRangeFactor = 8F;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletSniper(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return new SdkEntityBulletCasing(world, entity, f);
    }
}
