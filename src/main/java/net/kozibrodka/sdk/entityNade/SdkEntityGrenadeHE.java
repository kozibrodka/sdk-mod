package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeAP;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeHE;
import net.kozibrodka.sdk_api.utils.SdkEntityGrenade;
import net.kozibrodka.sdk_api.utils.SdkExplosion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityGrenadeHE extends SdkEntityGrenade implements EntitySpawnDataProvider {

    public SdkEntityGrenadeHE(World world)
    {
        super(world);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stack = new ItemStack(ItemListener.itemGrenadeHe, 1, 0);
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, SdkItemGrenadeHE.throwSound, 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    protected void handleBounce() {
        world.playSound(this, SdkItemGrenadeHE.bounceSound, 0.25F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            boolean flagW = false;
            if(checkWaterCollisions()){
                flagW = true;
                for (int i = 0; i < 128; i++) {
                    world.addParticle("splash", x + world.random.nextDouble() - 0.5D, y + (world.random.nextDouble()*3.0D), z + world.random.nextDouble() - 0.5D, 1, 1, 1);
                    world.addParticle("bubble", x, y, z, (world.random.nextDouble() - 0.5D) * 3.0D, (world.random.nextDouble() - 0.5D) * 10.0D, (world.random.nextDouble() - 0.5D) * 3.0D);
                }
            }else {
                for (int i = 0; i < 32; i++) {
                    world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                    world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                }
            }
            SdkExplosion explosion = new SdkExplosion(world, null, x,  y,  z, 3F, false, true, "random.explode", flagW,0.3F);
            explosion.explodeA();
            explosion.explodeB(true);
            dead = true;
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "NadeHe");
    }
}
