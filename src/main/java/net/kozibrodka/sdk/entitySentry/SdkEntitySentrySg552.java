
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentrySg552 extends SdkEntitySentry
{

    public SdkEntitySentrySg552(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentrySg552(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunSg552;
        ATTACK_DELAY = 50;
        range = 48F;
    }
}
