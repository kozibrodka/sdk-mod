
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.world.World;

public class SdkEntitySentryMinigun extends SdkEntitySentry
{

    public SdkEntitySentryMinigun(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryMinigun(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunMinigun;
        ATTACK_DELAY = 5;
        range = 32F;
    }
}
