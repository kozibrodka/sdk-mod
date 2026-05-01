

package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntitySentryFlamethrower extends SdkEntitySentry implements MobSpawnDataProvider
{

    public SdkEntitySentryFlamethrower(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentryFlamethrower(World world, double d, double d1, double d2)
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

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "SentryFlamethrower");
    }

    @Override
    protected int getDroppedItemId()
    {
        return 0;
    }
}
