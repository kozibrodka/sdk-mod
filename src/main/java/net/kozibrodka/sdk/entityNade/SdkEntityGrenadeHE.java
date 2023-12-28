package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;

public class SdkEntityGrenadeHE extends SdkEntityGrenade{

    public SdkEntityGrenadeHE(Level world)
    {
        super(world);
        item = new ItemInstance(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        item = new ItemInstance(ItemListener.itemGrenadeHe, 1, 0);
    }

    public SdkEntityGrenadeHE(Level world, Living entityliving)
    {
        super(world, entityliving);
        item = new ItemInstance(ItemListener.itemGrenadeHe, 1, 0);
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            Explosion explosion = new Explosion(level, null, x, (float)y, (float)z, 3F);
            explosion.kaboomPhase1();
            level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);

            for(int i = 0; i < 32; i++)
            {
                level.addParticle("explode", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
                level.addParticle("smoke", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
            }

            removed = true;
        }
    }
}
