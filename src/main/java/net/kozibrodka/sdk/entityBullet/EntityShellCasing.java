package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class EntityShellCasing extends SdkEntityCasing implements EntitySpawnDataProvider {

    public EntityShellCasing(World world)
    {
        super(world);
    }

    public EntityShellCasing(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    public EntityShellCasing(World world, Entity entity, float f)
    {
        super(world, entity, f);
    }

    @Override
    public int getDroppedItemId() {
        return ItemListener.itemShellCasing.id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "ShellCasing");
    }
}
