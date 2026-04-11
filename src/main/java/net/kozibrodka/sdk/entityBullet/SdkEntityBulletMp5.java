package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SdkEntityBulletMp5 extends SdkEntityBullet
{

    public SdkEntityBulletMp5(World world)
    {
        super(world);
    }

    public SdkEntityBulletMp5(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public SdkEntityBulletMp5(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                              float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
    }

    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunMp5).firingSound, ((SdkItemGun)ItemListener.itemGunMp5).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }
}
