
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.EntityBulletCasing;
import net.kozibrodka.sdk.entityBullet.SdkEntityBulletSniper;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunSniper extends SdkItemGun
{

    public SdkItemGunSniper(Identifier i)
    {
        super(i);
        firingSound = "sdk:sniper";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletHeavy;
        numBullets = 1;
        damage = 12;
        muzzleVelocity = 8F;
        spread = 0.0F;
        useDelay = 20;
        recoil = 8F;
        soundRangeFactor = 8F;
        penetration = 1;
        scopeOvText = "/assets/sdk/stationapi/textures/gui/miscScope.png";
        scopeMaxZoom = 0.125F;
        zoomSpeed = 0.075F;
        unscopedUnAccuracy = 8F;
        moveUnAccuracy = 0.25D;
        jumpUnAccuracy = 0.25D;
        noSneakUnAccuracy = 0.25D;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletSniper(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return new EntityBulletCasing(world, entity, f);
    }
}
