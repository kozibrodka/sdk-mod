
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.world.World;

public class SdkEntitySentryMp5 extends SdkEntitySentry
{

    public SdkEntitySentryMp5(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryMp5(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunMp5;
        ATTACK_DELAY = 18;
        range = 32F;
    }
}
