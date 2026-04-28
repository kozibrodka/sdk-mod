
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.EntityBulletCasing;
import net.kozibrodka.sdk.entityBullet.SdkEntityBulletSg552;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunSg552 extends SdkItemGun
{

    public SdkItemGunSg552(Identifier i)
    {
        super(i);
        firingSound = "sdk:sg";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletMedium;
        numBullets = 1;
        damage = 8;
        muzzleVelocity = 6F;
        spread = 0.25F;
        useDelay = 5;
        recoil = 3F;
        penetration = 0;
        scopeOvText = "/assets/sdk/stationapi/textures/gui/miscScope.png";
        scopeMaxZoom = 0.25F;
        zoomSpeed = 0.075F;
        unscopedUnAccuracy = 0F;
        moveUnAccuracy = 0.0D;
        jumpUnAccuracy = 0.0D;
        noSneakUnAccuracy = 0.0D;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletSg552(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return new EntityBulletCasing(world, entity, f);
    }
}
