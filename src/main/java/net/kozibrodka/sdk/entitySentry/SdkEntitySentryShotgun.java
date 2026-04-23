
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;

public class SdkEntitySentryShotgun extends SdkEntitySentry
{

    public SdkEntitySentryShotgun(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryShotgun(World world, double d, double d1, double d2)
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
