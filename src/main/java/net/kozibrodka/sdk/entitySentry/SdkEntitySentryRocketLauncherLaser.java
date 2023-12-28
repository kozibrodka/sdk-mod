
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.level.Level;

public class SdkEntitySentryRocketLauncherLaser extends SdkEntitySentry
{

    public SdkEntitySentryRocketLauncherLaser(Level world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryRocketLauncherLaser(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunRocketLauncherLaser;
        ATTACK_DELAY = 200;
        range = 32F;
    }
}
