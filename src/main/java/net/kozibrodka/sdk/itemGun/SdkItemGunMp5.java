
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletMp5;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.casing.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunMp5 extends SdkItemGun
{

    public SdkItemGunMp5(Identifier i)
    {
        super(i);
        firingSound = "sdk:mp";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletLight;
        numBullets = 1;
        damage = 4;
        muzzleVelocity = 3F;
        spread = 1.0F;
        useDelay = 2;
        recoil = 1.0F;
        penetration = 0;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletMp5(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityBulletCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return new SdkEntityBulletCasing(world, entity, f);
    }
}
