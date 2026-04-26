package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeHE;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeIncendiary;
import net.kozibrodka.sdk_api.utils.SdkEntityGrenade;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class SdkEntityGrenadeIncendiary extends SdkEntityGrenade implements EntitySpawnDataProvider
{

    public SdkEntityGrenadeIncendiary(World world)
    {
        super(world);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    public SdkEntityGrenadeIncendiary(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stack = new ItemStack(ItemListener.itemGrenadeIncendiaryLit, 1, 0);
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, SdkItemGrenadeIncendiary.throwSound, 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void tick()
    {
        super.tick();
        List list = world.getEntities(this, boundingBox);
        if(list.size() > 0)
        {
            Entity entity = (Entity)list.get(0);
            if(entity instanceof LivingEntity)
            {
                entity.fireTicks = 300;
                explode();
            }
        }
    }

    @Override
    protected void handleBounce()
    {
        explode();
    }

    @Override
    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            if(SdkEnvTool.isEnvClient()) {
                world.playSound(this, Block.GLASS.soundGroup.getBreakSound(), (Block.GLASS.soundGroup.getVolume() + 1.0F) / 2.0F, Block.GLASS.soundGroup.getPitch() * 0.8F);
            }
            int i = (int)Math.floor(x);
            int j = (int)Math.floor(y);
            int k = (int)Math.floor(z);
            if(!world.isRemote) {
                for (int l = -2; l <= 2; l++) {
                    for (int i1 = -2; i1 <= 2; i1++) {
                        for (int j1 = -2; j1 <= 2; j1++) {
                            int k1 = Math.abs(l) + Math.abs(i1) + Math.abs(j1);
                            if (k1 <= 2 && world.isAir(i + l, j + i1, k + j1)) {
                                world.setBlock(i + l, j + i1, k + j1, Block.FIRE.id);
                            }
                        }
                    }
                }
            }
            markDead();
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "NadeFire");
    }

    private static final int RADIUS = 2;
}
