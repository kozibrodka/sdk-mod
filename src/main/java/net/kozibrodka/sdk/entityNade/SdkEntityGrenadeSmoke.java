package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SdkEntityGrenadeSmoke extends SdkEntityGrenade
{

    public SdkEntityGrenadeSmoke(World world)
    {
        super(world);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(World world, LivingEntity entityliving)
    {
        super(world, entityliving);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        stack = new ItemStack(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            world.playSound(this, "sdk:smokegrenade", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
        }
        if(fuse < -500)
        {
            dead = true;
        }
        if(exploded)
        {
            double d = Math.min(8D, ((double)(-fuse) * 8D) / 250D);
            int i = Math.min(250, -fuse);
            for(int j = 0; j < i; j++)
            {
                world.addParticle("largesmoke", (x + random.nextDouble() * d) - 0.5D * d, y + random.nextDouble() * d, (z + random.nextDouble() * d) - 0.5D * d, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    protected String BOUNCE_SOUND;
    private static final int SMOKE_TIME = 500;
    private static final int MAX_DIAMETER_TIME = 250;
    private static final double MAX_DIAMETER = 8D;
}
