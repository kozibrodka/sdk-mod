package net.kozibrodka.sdk.entityBullet;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import java.util.List;

public class SdkEntityBulletRocketLaser extends SdkEntityBullet {

    public SdkEntityBulletRocketLaser(World world) {
        super(world);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                      float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
        rotationYawOffset = f3;
    }

    public void playServerSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).firingSound, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void playImpactSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).impactSound, 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    public void tick() {
        baseTick();
        if (timeInAir > 5) {
            laserAim();
        }
        if (timeInAir == 200) {
            explode();
            return;
        }
        if (timeInAir % 2 == 0) {
            double d = 0.625D;
            double d1 = Math.sqrt(velocityX * velocityX + velocityZ * velocityZ + velocityY * velocityY);
            world.addParticle("smoke", x - (velocityX / d1) * d, y - (velocityY / d1) * d, z - (velocityZ / d1) * d, 0.0D, 0.0D, 0.0D);
        }
        if (inGround) {
            int i = world.getBlockId(xTile, yTile, zTile);
            if (i != inTile) {
                inGround = false;
                velocityX *= random.nextFloat() * 0.2F;
                velocityY *= random.nextFloat() * 0.2F;
                velocityZ *= random.nextFloat() * 0.2F;
                timeInTile = 0;
                timeInAir = 0;
            }
        } else {
            timeInAir++;
        }
        Vec3d vec3d = Vec3d.createCached(x, y, z);
        Vec3d vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1);
        vec3d = Vec3d.createCached(x, y, z);
        vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = world.getEntities(this, boundingBox.stretch(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d2 = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            Entity entity1 = (Entity) list.get(j);
            if (!entity1.isCollidable() || (entity1 == owner || owner != null && entity1 == owner.vehicle) && timeInAir < 5 || serverSpawned) {
                continue;
            }
            float f2 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d3 = vec3d.distanceTo(movingobjectposition1.pos);
            if (d3 < d2 || d2 == 0.0D) {
                entity = entity1;
                d2 = d3;
            }
        }

        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            int k = world.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if (movingobjectposition.entity != null || k != Block.GRASS.id) {
                if (movingobjectposition.entity != null) {
                    int l = damage;
                    if ((owner instanceof Monster) && (movingobjectposition.entity instanceof PlayerEntity)) {
                        if (world.difficulty == 0) {
                            l = 0;
                        }
                        if (world.difficulty == 1) {
                            l = l / 3 + 1;
                        }
                        if (world.difficulty == 3) {
                            l = (l * 3) / 2;
                        }
                    }
                    if (movingobjectposition.entity instanceof LivingEntity) {
                        SdkTools.attackEntityIgnoreDelay((LivingEntity) movingobjectposition.entity, owner, l);
                    } else {
                        if(movingobjectposition.entity instanceof WW2Plane || movingobjectposition.entity instanceof WW2Tank || movingobjectposition.entity instanceof WW2Truck || movingobjectposition.entity instanceof WW2Cannon)
                        {
                            if(movingobjectposition.entity instanceof WW2Truck && penetration >= 1)
                            {
                                movingobjectposition.entity.damage(this, l);
                            }
                            if(movingobjectposition.entity instanceof WW2Plane && penetration >= 2)
                            {
                                movingobjectposition.entity.damage(this, l);
                            }
                            if((movingobjectposition.entity instanceof WW2Tank && penetration >= 3) || (movingobjectposition.entity instanceof WW2Cannon && penetration >= 3))
                            {
                                movingobjectposition.entity.damage(this, l);
                            }
                        }else {
                            movingobjectposition.entity.damage(owner, l);
                        }
                    }
                }
                explode();
            }
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float) ((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
        for (pitch = (float) ((Math.atan2(velocityY, f) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) {
        }
        for (; pitch - prevPitch >= 180F; prevPitch += 360F) {
        }
        for (; yaw - prevYaw < -180F; prevYaw -= 360F) {
        }
        for (; yaw - prevYaw >= 180F; prevYaw += 360F) {
        }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        float f1 = 1.0F;
        float f3 = 0.0F;     //opad
        if (isSubmergedInWater()) {
            for (int i1 = 0; i1 < 4; i1++) {
                float f4 = 0.25F;
                world.addParticle("bubble", x - velocityX * (double) f4, y - velocityY * (double) f4, z - velocityZ * (double) f4, velocityX, velocityY, velocityZ);
            }

            f1 = 0.95F;
            f3 = 0.03F;
        }
        velocityX *= f1;
        velocityY *= f1;
        velocityZ *= f1;
        velocityY -= f3;
        setPosition(x, y, z);
    }

    private void explode() {
        Explosion explosion = new Explosion(world, null, x, (float) y, (float) z, 3F);
        explosion.explode();
        if (SdkConfig.explosionsDestroyBlocks) {
            explosion.playExplosionSound(true);
        } else {
            world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
        }
        for (int i = 0; i < 32; i++) {
            world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
            world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
        }

        markDead();
    }

    private void laserAim() {
        if (owner != null) {
            double d = x - owner.x;
            double d1 = y - owner.y - (double) ((owner instanceof PlayerEntity) ? 0.0F : owner.getShadowRadius());
            double d2 = z - owner.z;
            float f = owner.pitch;
            float f1 = (float) (Math.atan(Math.sqrt(d * d + d2 * d2) / d1) * 57.295779513082323D);
            if (d1 >= 0.0D) {
                f1 -= 90F;
            } else {
                f1 += 90F;
            }
            float f2 = f - f1;
            if (f2 > 0.0F) {
                f2 = Math.min(f2, 30F);
            } else {
                f2 = Math.max(f2, -30F);
            }
            f2 *= 3F;
            float f3 = f1 + f2;
            if (f3 < -90F) {
                f3 = -90F;
            } else if (f3 > 90F) {
                f3 = 90F;
            }
            float f4 = (owner.yaw + rotationYawOffset) % 360F;
            if (f4 < -180F) {
                f4 += 360F;
            }
            if (f4 < 0.0F) {
                f4 *= -1F;
            } else if (f4 < 180F) {
                f4 *= -1F;
            } else {
                f4 = 360F - f4;
            }
            float f5;
            if (d >= 0.0D && d2 >= 0.0D) {
                f5 = (float) (Math.atan(Math.abs(d / d2)) * 57.295779513082323D);
            } else if (d >= 0.0D && d2 <= 0.0D) {
                f5 = 90F + (float) (Math.atan(Math.abs(d2 / d)) * 57.295779513082323D);
            } else if (d <= 0.0D && d2 >= 0.0D) {
                f5 = -(90F - (float) (Math.atan(Math.abs(d2 / d)) * 57.295779513082323D));
            } else {
                f5 = -(180F - (float) (Math.atan(Math.abs(d / d2)) * 57.295779513082323D));
            }
            float f6 = f5 - f4;
            if (f6 > 180F) {
                f6 -= 360F;
            } else if (f6 < -180F) {
                f6 += 360F;
            }
            if (f6 > 30F) {
                f6 = 30F;
            } else if (f6 < -30F) {
                f6 = -30F;
            }
            f6 *= 3F;
            float f7 = f5 - f6;
            if (f7 > 180F) {
                f7 -= 360F;
            } else if (f7 < -180F) {
                f7 += 360F;
            }
            turnTowards(f3, f7);
        }
    }

    private void turnTowards(float f, float f1) {
        float f2 = yaw;
        float f3 = f1 - f2;
        if (f3 > 180F) {
            f3 -= 360F;
        } else if (f3 < -180F) {
            f3 += 360F;
        }
        if (f3 > 10F) {
            f3 = 10F;
        } else if (f3 < -10F) {
            f3 = -10F;
        }
        float f4 = f2 + f3;
        if (f4 > 180F) {
            f4 -= 360F;
        } else if (f4 < -180F) {
            f4 += 360F;
        }
        float f5 = Math.abs(f4);
        if (f4 < -90F) {
            f5 = f4 + 180F;
        } else if (f4 < 0.0F) {
            f5 = f4 + 90F;
        } else if (f4 > 90F) {
            f5 -= 90F;
        }
        float f6 = pitch * -1F;
        float f7 = f - f6;
        if (f7 > 90F) {
            f7 -= 180F;
        } else if (f7 < -90F) {
            f7 += 180F;
        }
        if (f7 > 10F) {
            f7 = 10F;
        } else if (f7 < -10F) {
            f7 = -10F;
        }
        float f8 = f6 + f7;
        if (f8 > 90F) {
            f8 -= 180F;
        } else if (f8 < -90F) {
            f8 += 180F;
        }
        double d = Math.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
        float f9 = Math.abs(f8);
        velocityY = Math.sin((double) f9 / 57.295779513082323D) * d;
        if (f8 > 0.0F) {
            velocityY *= -1D;
        }
        double d1 = Math.cos((double) f9 / 57.295779513082323D) * d;
        if (f4 <= -90F) {
            velocityX = -Math.sin((double) f5 / 57.295779513082323D) * d1;
            velocityZ = -Math.cos((double) f5 / 57.295779513082323D) * d1;
        } else if (f4 <= 0.0F) {
            velocityX = -Math.cos((double) f5 / 57.295779513082323D) * d1;
            velocityZ = Math.sin((double) f5 / 57.295779513082323D) * d1;
        } else if (f4 <= 90F) {
            velocityX = Math.sin((double) f5 / 57.295779513082323D) * d1;
            velocityZ = Math.cos((double) f5 / 57.295779513082323D) * d1;
        } else {
            velocityX = Math.cos((double) f5 / 57.295779513082323D) * d1;
            velocityZ = -Math.sin((double) f5 / 57.295779513082323D) * d1;
        }
    }

    public float rotationYawOffset;
    protected static final float MAX_AIMING_ANGLE = 30F;
    protected static final float MAX_TURNING_ANGLE = 10F;
}
