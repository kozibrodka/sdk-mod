
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryM4 extends SdkEntitySentry
{

    public SdkEntitySentryM4(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryM4(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunM4;
        ATTACK_DELAY = 125;
        range = 32F;
    }
}
