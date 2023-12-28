
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryAk47 extends SdkEntitySentry
{

    public SdkEntitySentryAk47(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryAk47(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunAk47;
        ATTACK_DELAY = 25;
        range = 32F;
    }
}
