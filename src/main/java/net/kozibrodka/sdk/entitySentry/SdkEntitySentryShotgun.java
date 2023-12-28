
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryShotgun extends SdkEntitySentry
{

    public SdkEntitySentryShotgun(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryShotgun(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunShotgun;
        ATTACK_DELAY = 100;
        range = 16F;
    }
}
