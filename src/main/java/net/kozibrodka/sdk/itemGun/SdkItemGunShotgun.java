
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletShot;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasingShell;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;


public class SdkItemGunShotgun extends SdkItemGun
{

    public SdkItemGunShotgun(Identifier i)
    {
        super(i);
        firingSound = "sdk:shotgun";
        requiredBullet = ItemListener.itemBulletShell;
        numBullets = 12;
        damage = 2;
        muzzleVelocity = 3F;
        spread = 8F;
        useDelay = 16;
        recoil = 8F;
        penetration = 0;
    }

    public SdkEntityBullet getBulletEntity(Level world, EntityBase entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletShot(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(Level world, EntityBase entity, float f)
    {
        return new SdkEntityBulletCasingShell(world, entity, f);
    }
}
