
package net.kozibrodka.sdk.itemGun;
import net.kozibrodka.sdk.entityBullet.EntityBulletCasing;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.kozibrodka.sdk.entityBullet.SdkEntityBulletAk47;
import net.kozibrodka.sdk.events.ItemListener;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunAk47 extends SdkItemGun
{

    public SdkItemGunAk47(Identifier i)
    {
        super(i);
        firingSound = "sdk:ak";
        reloadSound = "sdk:reload";
        emptySound = "sdk:gunempty";
        ammoHudText = "/assets/sdk/stationapi/textures/gui/guiAmmoBullet.png";
        requiredBullet = ItemListener.itemBulletLight;
        numBullets = 1;
        damage = 5;
        muzzleVelocity = 4F;
        spread = 0.5F;
        useDelay = 2;
        recoil = 2.0F;
        penetration = 0;
    }


    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4)
    {
        return new SdkEntityBulletAk47(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f)
    {
        return new EntityBulletCasing(world, entity, f);
    }
}
