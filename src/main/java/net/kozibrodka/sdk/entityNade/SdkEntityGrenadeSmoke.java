package net.kozibrodka.sdk.entityNade;


import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class SdkEntityGrenadeSmoke extends SdkEntityGrenade
{

    public SdkEntityGrenadeSmoke(Level world)
    {
        super(world);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        item = new ItemInstance(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        item = new ItemInstance(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    public SdkEntityGrenadeSmoke(Level world, Living entityliving)
    {
        super(world, entityliving);
        BOUNCE_SOUND = "sdk:smokegrenadebounce";
        item = new ItemInstance(ItemListener.itemGrenadeSmoke, 1, 0);
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            level.playSound(this, "sdk:smokegrenade", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        }
        if(fuse < -500)
        {
            removed = true;
        }
        if(exploded)
        {
            double d = Math.min(8D, ((double)(-fuse) * 8D) / 250D);
            int i = Math.min(250, -fuse);
            for(int j = 0; j < i; j++)
            {
                level.addParticle("largesmoke", (x + rand.nextDouble() * d) - 0.5D * d, y + rand.nextDouble() * d, (z + rand.nextDouble() * d) - 0.5D * d, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    protected String BOUNCE_SOUND;
    private static final int SMOKE_TIME = 500;
    private static final int MAX_DIAMETER_TIME = 250;
    private static final double MAX_DIAMETER = 8D;
}
