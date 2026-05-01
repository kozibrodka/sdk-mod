
package net.kozibrodka.sdk.entitySentry;



import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntitySentryRocketLauncher extends SdkEntitySentry implements MobSpawnDataProvider
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

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "SentryRocketLauncher");
    }
}
