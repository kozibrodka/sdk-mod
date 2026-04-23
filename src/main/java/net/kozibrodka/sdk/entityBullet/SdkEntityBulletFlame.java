package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class SdkEntityBulletFlame extends SdkEntityBullet
{

    public SdkEntityBulletFlame(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public SdkEntityBulletFlame(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public SdkEntityBulletFlame(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunFlamethrower).firingSound, ((SdkItemGun)ItemListener.itemGunFlamethrower).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void playImpactSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunFlamethrower).impactSound, 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    public void tick()
    {
        baseTick();
        if(timeInAir == 30)
        {
            markDead();
        }
        world.addParticle("smoke", x, y, z, 0.0D, 0.0D, 0.0D);
        if(inGround)
        {
            int i = world.getBlockId(xTile, yTile, zTile);
            if(i != inTile)
            {
                inGround = false;
                velocityX *= random.nextFloat() * 0.2F;
                velocityY *= random.nextFloat() * 0.2F;
                velocityZ *= random.nextFloat() * 0.2F;
                timeInTile = 0;
                timeInAir = 0;
            }
        } else
        {
            timeInAir++;
        }
        Vec3d vec3d = Vec3d.createCached(x, y, z);
        Vec3d vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1);
        vec3d = Vec3d.createCached(x, y, z);
        vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = world.getEntities(this, boundingBox.stretch(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.isCollidable() || (entity1 == owner || owner != null && entity1 == owner.vehicle) && timeInAir < 5 || serverSpawned)
            {
                continue;
            }
            float f = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f, f, f);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.pos);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new HitResult(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entity != null)
            {
                int k = damage;
                if((owner instanceof Monster) && (movingobjectposition.entity instanceof PlayerEntity))
                {
                    if(world.difficulty == 0)
                    {
                        k = 0;
                    }
                    if(world.difficulty == 1)
                    {
                        k = k / 3 + 1;
                    }
                    if(world.difficulty == 3)
                    {
                        k = (k * 3) / 2;
                    }
                }
                if(movingobjectposition.entity instanceof LivingEntity)
                {
                    SdkTools.attackEntityIgnoreDelay((LivingEntity)movingobjectposition.entity, owner, k);
                } else
                {
                    if(movingobjectposition.entity instanceof WW2Plane || movingobjectposition.entity instanceof WW2Tank || movingobjectposition.entity instanceof WW2Truck || movingobjectposition.entity instanceof WW2Cannon)
                    {
                        if(movingobjectposition.entity instanceof WW2Truck && penetration >= 1)
                        {
                            movingobjectposition.entity.damage(this, k);
                        }
                        if(movingobjectposition.entity instanceof WW2Plane && penetration >= 2)
                        {
                            movingobjectposition.entity.damage(this, k);
                        }
                        if((movingobjectposition.entity instanceof WW2Tank && penetration >= 3) || (movingobjectposition.entity instanceof WW2Cannon && penetration >= 3))
                        {
                            movingobjectposition.entity.damage(this, k);
                        }
                    }else {
                        movingobjectposition.entity.damage(owner, k);
                    }
                }
                movingobjectposition.entity.fireTicks = 300;
            } else
            {
                xTile = movingobjectposition.blockX;
                yTile = movingobjectposition.blockY;
                zTile = movingobjectposition.blockZ;
                if(world.getBlockId(xTile, yTile, zTile) == Block.ICE.id && Block.ICE.getHardness() < 1000000F)
                {
                    Block.ICE.onBreak(world, xTile, yTile, zTile); //nie dziala
                } else
                {
                    byte byte0 = (byte)(velocityX > 0.0D ? 1 : -1);
                    byte byte1 = (byte)(velocityY > 0.0D ? 1 : -1);
                    byte byte2 = (byte)(velocityZ > 0.0D ? 1 : -1);
                    boolean flag = world.getBlockId(xTile - byte0, yTile, zTile) == 0 || world.getBlockId(xTile - byte0, yTile, zTile) == Block.SNOW.id;
                    boolean flag1 = world.getBlockId(xTile, yTile - byte1, zTile) == 0 || world.getBlockId(xTile, yTile - byte1, zTile) == Block.SNOW.id;
                    boolean flag2 = world.getBlockId(xTile, yTile, zTile - byte2) == 0 || world.getBlockId(xTile, yTile, zTile - byte2) == Block.SNOW.id;
                    if(flag)
                    {
                        world.setBlock(xTile - byte0, yTile, zTile, Block.FIRE.id);
                    }
                    if(flag1)
                    {
                        world.setBlock(xTile, yTile - byte1, zTile, Block.FIRE.id);
                    }
                    if(flag2)
                    {
                        world.setBlock(xTile, yTile, zTile - byte2, Block.FIRE.id);
                    }
                }
            }
            markDead();
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        setRotationToVelocity();
        if(world.updateMovementInFluid(boundingBox, Material.WATER, this)) //handlematerialacceleration
        {
            world.playSound(this, "random.fizz", 0.8F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
            markDead();
        }
        setPosition(x, y, z);
    }

    public void setRotationToVelocity()
    {
//        float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
//        yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
//        for(pitch = (float)((Math.atan2(velocityZ, f) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) { }
//        for(; pitch - prevPitch >= 180F; prevPitch += 360F) { }
//        for(; yaw - prevYaw < -180F; prevYaw -= 360F) { }
//        for(; yaw - prevYaw >= 180F; prevYaw += 360F) { }
    }

    public void setVelocityClient(double d, double d1, double d2)
    {
        super.setVelocityClient(d, d1, d2);
        setRotationToVelocity();
    }

    public float getBrightnessAtEyes(float f)
    {
        return 2.0F;
    }
}
