package net.kozibrodka.sdk.entityNade;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SdkEntityGrenade extends ItemEntity
{

    public SdkEntityGrenade(World world)
    {
        super(world);
        bounceSound = "sdk:grenadebounce";
        bounceFactor = 0.14999999999999999D;
        bounceSlowFactor = 0.80000000000000004D;
        initialVelocity = 1.0D;
        setBoundingBoxSpacing(0.25F, 0.25F);
        exploded = false;
        fuse = 50;
        standingEyeHeight = 0.0F;
        stack = new ItemStack(ItemListener.itemGrenade, 1, 0);
    }

    public SdkEntityGrenade(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public SdkEntityGrenade(World world, LivingEntity entityliving)
    {
        this(world);
        changeLookDirection(entityliving.yaw, 0.0F);
        double d = -MathHelper.sin((entityliving.yaw * 3.141593F) / 180F);
        double d1 = MathHelper.cos((entityliving.yaw * 3.141593F) / 180F);
        velocityX = initialVelocity * d * (double)MathHelper.cos((entityliving.pitch / 180F) * 3.141593F);
        velocityY = -initialVelocity * (double)MathHelper.sin((entityliving.pitch / 180F) * 3.141593F);
        velocityZ = initialVelocity * d1 * (double)MathHelper.cos((entityliving.pitch / 180F) * 3.141593F);
        if(entityliving.vehicle != null && (entityliving.vehicle instanceof LivingEntity))
        {
            entityliving = (LivingEntity)entityliving.vehicle;
        }
        velocityX += entityliving.velocityX;
        velocityY += entityliving.onGround ? 0.0D : entityliving.velocityY;
        velocityZ += entityliving.velocityZ;
        setPosition(entityliving.x + d * 0.80000000000000004D, entityliving.y + (double)entityliving.getEyeHeight(), entityliving.z + d1 * 0.80000000000000004D);
        prevX = x;
        prevY = y;
        prevZ = z;
    }

    public boolean shouldRender(double d)
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
        world.playSound(this, bounceSound, 0.25F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
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
            Explosion explosion = new Explosion(world, null, x, (float)y, (float)z, 3F);
            explosion.explode();
            if(SdkConfig.explosionsDestroyBlocks)
            {
                explosion.playExplosionSound(true);
            } else
            {
                world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
            }
            for(int i = 0; i < 32; i++)
            {
                world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
            }

            dead = true;
        }
    }

    public boolean isCollidable()
    {
        return true;
    }

    public boolean damage(Entity entity, int i)
    {
        return false;
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putByte("Fuse", (byte)fuse);
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        fuse = nbttagcompound.getByte("Fuse");
    }

    public void onPlayerInteraction(PlayerEntity entityplayer)
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
