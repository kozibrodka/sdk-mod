package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.itemNade.SdkItemGrenadeSmoke;
import net.kozibrodka.sdk_api.utils.SdkEntityGrenade;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityGrenadeSmoke extends SdkEntityGrenade implements EntitySpawnDataProvider
{

    public SdkEntityGrenadeSmoke(World world)
    {
        super(world);
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, SdkItemGrenadeSmoke.throwSound, 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    protected void handleBounce() {
        world.playSound(this, SdkItemGrenadeSmoke.bounceSound, 0.25F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            world.playSound(this, SdkItemGrenadeSmoke.explosionSound, 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
        }
        if(fuse < -500)
        {
            dead = true;
        }
        if(exploded && SdkEnvTool.isEnvClient())
        {
            double d = Math.min(8D, ((double)(-fuse) * 8D) / 250D);
            int i = Math.min(250, -fuse);
            for(int j = 0; j < i; j++)
            {
                world.addParticle("largesmoke", (x + random.nextDouble() * d) - 0.5D * d, y + random.nextDouble() * d, (z + random.nextDouble() * d) - 0.5D * d, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "NadeSmoke");
    }

    protected String BOUNCE_SOUND;
    private static final int SMOKE_TIME = 500;
    private static final int MAX_DIAMETER_TIME = 250;
    private static final double MAX_DIAMETER = 8D;
}
