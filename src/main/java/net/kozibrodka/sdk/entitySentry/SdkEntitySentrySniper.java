
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentrySniper extends SdkEntitySentry
{

    public SdkEntitySentrySniper(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentrySniper(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunSniper;
        ATTACK_DELAY = 100;
        range = 64F;
    }
}
