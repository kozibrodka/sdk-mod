
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletRocket;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunRocketLauncher extends SdkItemGun
{

    public SdkItemGunRocketLauncher(Identifier i)
    {
        super(i);
        firingSound = "sdk:rocket";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletRocket;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 1.5F;
        spread = 0.0F;
        useDelay = 20;
        recoil = 0.0F;
        penetration = 3;
        explosionPower = 3.0F;
        explosionBlocks = true;
        explosionFire = false;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletRocket(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return null;
    }
}
