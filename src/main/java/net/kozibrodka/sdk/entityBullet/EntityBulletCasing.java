package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class EntityBulletCasing extends SdkEntityCasing implements EntitySpawnDataProvider {

    public EntityBulletCasing(World world)
    {
        super(world);
    }

    public EntityBulletCasing(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityBulletCasing(World world, Entity entity, float f)
    {
        super(world, entity, f);
    }

    @Override
    public int getDroppedItemId() {
        return ItemListener.itemBulletCasing.id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletCasing");
    }
}
