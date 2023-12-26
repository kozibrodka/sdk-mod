
package net.kozibrodka.sdk.entity;


import net.kozibrodka.sdk.tileEntity.SdkExplosionNuke;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;

public class SdkEntityNukePrimed extends EntityBase
{

    public SdkEntityNukePrimed(Level world)
    {
        super(world);
        fuse = 0;
        field_1593 = true;
        setSize(0.98F, 0.98F);
        standingEyeHeight = height / 2.0F;
    }

    public SdkEntityNukePrimed(Level world, double d, double d1, double d2)
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

    protected void initDataTracker()
    {
    }

    public boolean shouldRenderAtDistance(double d)
    {
        return true;
    }

    public boolean method_1356()
    {
        return !removed;
    }

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
            remove();
            explode();
        } else
        {
            level.addParticle("smoke", x, y + 0.5D, z, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        SdkExplosionNuke sdkexplosionnuke = new SdkExplosionNuke(level, null, x, y, z, 8F, 0.0F, false);
        sdkexplosionnuke.doExplosionA();
        sdkexplosionnuke.doExplosionB();
    }

    protected void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        nbttagcompound.put("Fuse", (byte)fuse);
    }

    protected void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        fuse = nbttagcompound.getByte("Fuse");
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public int fuse;
}
