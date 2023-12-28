package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;

public class SdkEntityGrenade extends Item
{

    public SdkEntityGrenade(Level world)
    {
        super(world);
        bounceSound = "sdk:grenadebounce";
        bounceFactor = 0.14999999999999999D;
        bounceSlowFactor = 0.80000000000000004D;
        initialVelocity = 1.0D;
        setSize(0.25F, 0.25F);
        exploded = false;
        fuse = 50;
        standingEyeHeight = 0.0F;
        item = new ItemInstance(ItemListener.itemGrenade, 1, 0);
    }

    public SdkEntityGrenade(Level world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public SdkEntityGrenade(Level world, Living entityliving)
    {
        this(world);
        method_1362(entityliving.yaw, 0.0F);
        double d = -MathHelper.sin((entityliving.yaw * 3.141593F) / 180F);
        double d1 = MathHelper.cos((entityliving.yaw * 3.141593F) / 180F);
        velocityX = initialVelocity * d * (double)MathHelper.cos((entityliving.pitch / 180F) * 3.141593F);
        velocityY = -initialVelocity * (double)MathHelper.sin((entityliving.pitch / 180F) * 3.141593F);
        velocityZ = initialVelocity * d1 * (double)MathHelper.cos((entityliving.pitch / 180F) * 3.141593F);
        if(entityliving.vehicle != null && (entityliving.vehicle instanceof Living))
        {
            entityliving = (Living)entityliving.vehicle;
        }
        velocityX += entityliving.velocityX;
        velocityY += entityliving.onGround ? 0.0D : entityliving.velocityY;
        velocityZ += entityliving.velocityZ;
        setPosition(entityliving.x + d * 0.80000000000000004D, entityliving.y + (double)entityliving.getStandingEyeHeight(), entityliving.z + d1 * 0.80000000000000004D);
        prevX = x;
        prevY = y;
        prevZ = z;
    }

    public boolean shouldRenderAtDistance(double d)
    {
        return true;
    }

    public void tick()
    {
        double d = velocityX;
        double d1 = velocityY;
        double d2 = velocityZ;
        prevX = x;
        prevY = y;
        prevZ = z;
        move(velocityX, velocityY, velocityZ);
        boolean flag = false;
        if(velocityX == 0.0D && d != 0.0D)
        {
            velocityX = -bounceFactor * d;
            velocityY = bounceSlowFactor * d1;
            velocityZ = bounceSlowFactor * d2;
            if(Math.abs(d) > 0.10000000000000001D)
            {
                flag = true;
            }
        }
        if(velocityY == 0.0D && d1 != 0.0D)
        {
            velocityX = bounceSlowFactor * d;
            velocityY = -bounceFactor * d1;
            velocityZ = bounceSlowFactor * d2;
            if(Math.abs(d1) > 0.10000000000000001D)
            {
                flag = true;
            }
        }
        if(velocityZ == 0.0D && d2 != 0.0D)
        {
            velocityX = bounceSlowFactor * d;
            velocityY = bounceSlowFactor * d1;
            velocityZ = -bounceFactor * d2;
            if(Math.abs(d2) > 0.10000000000000001D)
            {
                flag = true;
            }
        }
        if(flag)
        {
            handleBounce();
        }
        velocityY -= 0.040000000000000001D;
        velocityX *= 0.98999999999999999D;
        velocityY *= 0.98999999999999999D;
        velocityZ *= 0.98999999999999999D;
        handleExplode();
    }

    protected void handleBounce()
    {
        level.playSound(this, bounceSound, 0.25F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }

    protected void handleExplode()
    {
        if(fuse-- <= 0)
        {
            explode();
        }
    }

    protected void explode()
    {
        if(!exploded)
        {
            exploded = true;
            Explosion explosion = new Explosion(level, null, x, (float)y, (float)z, 3F);
            explosion.kaboomPhase1();
            if(SdkConfig.explosionsDestroyBlocks)
            {
                explosion.kaboomPhase2(true);
            } else
            {
                level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
            }
            for(int i = 0; i < 32; i++)
            {
                level.addParticle("explode", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
                level.addParticle("smoke", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
            }

            removed = true;
        }
    }

    public boolean method_1356()
    {
        return true;
    }

    public boolean damage(EntityBase entity, int i)
    {
        return false;
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("Fuse", (byte)fuse);
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        super.readCustomDataFromTag(nbttagcompound);
        fuse = nbttagcompound.getByte("Fuse");
    }

    public void onPlayerCollision(PlayerBase entityplayer)
    {
    }

    protected String bounceSound;
    protected double bounceFactor;
    protected double bounceSlowFactor;
    protected int fuse;
    protected boolean exploded;
    protected double initialVelocity;
    protected static final int FUSE_LENGTH = 50;
    protected static final double MIN_BOUNCE_SOUND_VELOCITY = 0.10000000000000001D;
}
