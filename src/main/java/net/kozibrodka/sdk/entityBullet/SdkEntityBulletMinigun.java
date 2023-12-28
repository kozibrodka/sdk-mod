package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;

public class SdkEntityBulletMinigun extends SdkEntityBullet
{

    public SdkEntityBulletMinigun(Level world)
    {
        super(world);
    }

    public SdkEntityBulletMinigun(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public SdkEntityBulletMinigun(Level world, EntityBase entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                  float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
    }

    public void playServerSound(Level world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunMinigun).firingSound, ((SdkItemGun)ItemListener.itemGunMinigun).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }
}
