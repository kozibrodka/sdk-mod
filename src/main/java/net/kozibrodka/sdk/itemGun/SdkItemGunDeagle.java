
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.EntityBulletCasing;
import net.kozibrodka.sdk.entityBullet.SdkEntityBulletDeagle;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;



public class SdkItemGunDeagle extends SdkItemGun
{

    public SdkItemGunDeagle(Identifier i)
    {
        super(i);
        firingSound = "sdk:deagle";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletMedium;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 4F;
        spread = 2.0F;
        useDelay = 8;
        recoil = 4F;
        penetration = 1;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletDeagle(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return new EntityBulletCasing(world, entity, f);
    }
}
