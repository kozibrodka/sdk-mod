
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletLaser;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.casing.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunLaser extends SdkItemGun
{

    public SdkItemGunLaser(Identifier i)
    {
        super(i);
        firingSound = "sdk:laser";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoRedstone.png";
        requiredBullet = Item.REDSTONE;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 1.5F;
        spread = 0.0F;
        useDelay = 10;
        recoil = 0.0F;
        soundRangeFactor = 2.0F;
        penetration = 3;
        ammoRenderLiquid = true;
        bulletDrop = 0.0F;
        panzerDamage = 10;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletLaser(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityBulletCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return null;
    }
}
