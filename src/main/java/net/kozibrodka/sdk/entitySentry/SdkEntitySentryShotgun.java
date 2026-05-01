
package net.kozibrodka.sdk.entitySentry;


import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntitySentryShotgun extends SdkEntitySentry implements MobSpawnDataProvider
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

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "SentryShotgun");
    }
}
