
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;

public class SdkEntitySentryDeagle extends SdkEntitySentry
{

    public SdkEntitySentryDeagle(World world)
    {
        super(world);
        setParameters();
        texture = "/assets/sdk/stationapi/textures/entity/mobSentry.png";
    }

    public SdkEntitySentryDeagle(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunDeagle;
        ATTACK_DELAY = 40;
        range = 32F;
    }
}
