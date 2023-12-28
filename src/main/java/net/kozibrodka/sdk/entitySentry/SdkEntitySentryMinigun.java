
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryMinigun extends SdkEntitySentry
{

    public SdkEntitySentryMinigun(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryMinigun(Level world, double d, double d1, double d2)
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
