
package net.kozibrodka.sdk.itemGun;

import net.kozibrodka.sdk.entityBullet.SdkEntityBulletFlame;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.casing.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunFlamethrower extends SdkItemGun
{

    public SdkItemGunFlamethrower(Identifier i)
    {
        super(i);
        firingSound = "sdk:flamethrower";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoOil.png";
        requiredBullet = ItemListener.itemOil;
        numBullets = 1;
        damage = 1;
        muzzleVelocity = 0.75F;
        spread = 0.0F;
        useDelay = 1;
        recoil = 0.0F;
        soundDelay = 12;
        soundRangeFactor = 2.0F;
        penetration = 3;
        ammoRenderLiquid = true;
        bucketAmmo = Item.BUCKET.id;
        bulletDrop = 0.0F;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletFlame(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityBulletCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return null;
    }
}
