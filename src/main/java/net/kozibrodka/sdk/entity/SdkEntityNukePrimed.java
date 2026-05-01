
package net.kozibrodka.sdk.entity;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk_api.utils.SdkExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 240, updatePeriod = 1200, sendVelocity = TriState.TRUE)
public class SdkEntityNukePrimed extends Entity implements EntitySpawnDataProvider
{

    public SdkEntityNukePrimed(World world)
    {
        super(world);
        fuse = 0;
        blocksSameBlockSpawning = true;
        setBoundingBoxSpacing(0.98F, 0.98F);
        standingEyeHeight = height / 2.0F;
    }

    public SdkEntityNukePrimed(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        float f = (float)(Math.random() * 3.1415927410125732D * 2D);
        velocityX = -MathHelper.sin((f * 3.141593F) / 180F) * 0.02F;
        velocityY = 0.20000000298023224D;
        velocityZ = -MathHelper.cos((f * 3.141593F) / 180F) * 0.02F;
        fuse = 80;
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    @Override
    protected void initDataTracker()
    {
    }

    @Override
    public boolean shouldRender(double d)
    {
        return true;
    }

    @Override
    public boolean isCollidable()
    {
        return !dead;
    }

    public boolean playedSound;

    @Override
    public void tick()
    {
        prevX = x;
        prevY = y;
        prevZ = z;
        velocityY -= 0.039999999105930328D;
        move(velocityX, velocityY, velocityZ);
        velocityX *= 0.98000001907348633D;
        velocityY *= 0.98000001907348633D;
        velocityZ *= 0.98000001907348633D;
        if(onGround)
        {
            velocityX *= 0.69999998807907104D;
            velocityZ *= 0.69999998807907104D;
            velocityY *= -0.5D;
        }
        if(fuse-- <= 0)
        {
            markDead();
            explode();
        } else
        {
            world.addParticle("smoke", x, y + 0.5D, z, 0.0D, 0.0D, 0.0D);
        }
        if(!playedSound){
            playedSound = true;
            world.playSound(this, "random.fuse", 1.0F, 1.0F);
        }
    }

    private void explode()
    {
//        SdkExplosionNuke sdkexplosionnuke = new SdkExplosionNuke(world, null, x, y, z, 8F, 0.0F, false);
//        sdkexplosionnuke.doExplosionA();
//        sdkexplosionnuke.doExplosionB();
        boolean flagW = false;
        if(checkWaterCollisions()) {
            flagW = true;
        }
        SdkExplosion explosion = new SdkExplosion(world, null, x,  y,  z, 8.0F, false, true, "random.explode", flagW, 0.0F); //todo 0 luck
        explosion.explodeA();
        explosion.explodeB(true);
    }

    @Override
    protected void writeNbt(NbtCompound nbttagcompound)
    {
        nbttagcompound.putByte("Fuse", (byte)fuse);
    }

    @Override
    protected void readNbt(NbtCompound nbttagcompound)
    {
        fuse = nbttagcompound.getByte("Fuse");
    }

    @Override
    public float getShadowRadius()
    {
        return 0.0F;
    }

    public int fuse;

    @Override
    public Identifier getHandlerIdentifier() {
        {
            return Identifier.of(EntityListener.MOD_ID, "NukePrimed");
        }
    }
}
