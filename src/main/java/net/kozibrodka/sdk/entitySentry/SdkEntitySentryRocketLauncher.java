
package net.kozibrodka.sdk.entitySentry;



import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.world.World;

public class SdkEntitySentryRocketLauncher extends SdkEntitySentry
{

    public SdkEntitySentryRocketLauncher(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryRocketLauncher(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setParameters();
    }

    private void setParameters()
    {
        gun = (SdkItemGun) ItemListener.itemGunRocketLauncher;
        ATTACK_DELAY = 150;
        range = 32F;
    }
}
