

package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryFlamethrower extends SdkEntitySentry
{

    public SdkEntitySentryFlamethrower(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryFlamethrower(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunFlamethrower;
        ATTACK_DELAY = 1;
        range = 16F;
    }

    protected int getDropItemId()
    {
        return 0;
    }
}
