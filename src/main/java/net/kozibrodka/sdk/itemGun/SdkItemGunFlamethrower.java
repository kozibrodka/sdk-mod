
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletFlame;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;



public class SdkItemGunFlamethrower extends SdkItemGun
{

    public SdkItemGunFlamethrower(Identifier i)
    {
        super(i);
        firingSound = "sdk:flamethrower";
        requiredBullet = ItemListener.itemOil;
        numBullets = 1;
        damage = 1;
        muzzleVelocity = 0.75F;
        spread = 0.0F;
        useDelay = 1;
        recoil = 0.0F;
        soundDelay = 12;
        soundRangeFactor = 2.0F;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletFlame(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return null;
    }
}
