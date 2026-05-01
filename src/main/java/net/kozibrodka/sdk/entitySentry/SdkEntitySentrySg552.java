
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntitySentrySg552 extends SdkEntitySentry implements MobSpawnDataProvider
{

    public SdkEntitySentrySg552(World world)
    {
        super(world);
        setParameters();
    }

    public SdkEntitySentrySg552(World world, double d, double d1, double d2)
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

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "SentrySg552");
    }
}
