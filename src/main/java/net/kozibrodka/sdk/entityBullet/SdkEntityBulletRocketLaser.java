package net.kozibrodka.sdk.entityBullet;


import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class SdkEntityBulletRocketLaser extends SdkEntityBullet {

    public SdkEntityBulletRocketLaser(Level world) {
        super(world);
        rotationYawOffset = 0.0F;
        setSize(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(Level world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        rotationYawOffset = 0.0F;
        setSize(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(Level world, EntityBase entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                      float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        rotationYawOffset = 0.0F;
        setSize(0.25F, 0.25F);
        rotationYawOffset = f3;
    }

    public void playServerSound(Level world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).firingSound, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
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
            level.addParticle("smoke", x - (velocityX / d1) * d, y - (velocityY / d1) * d, z - (velocityZ / d1) * d, 0.0D, 0.0D, 0.0D);
        }
        if (inGround) {
            int i = level.getTileId(xTile, yTile, zTile);
            if (i != inTile) {
                inGround = false;
                velocityX *= rand.nextFloat() * 0.2F;
                velocityY *= rand.nextFloat() * 0.2F;
                velocityZ *= rand.nextFloat() * 0.2F;
                timeInTile = 0;
                timeInAir = 0;
            }
        } else {
            timeInAir++;
        }
        Vec3f vec3d = Vec3f.from(x, y, z);
        Vec3f vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = level.method_160(vec3d, vec3d1);
        vec3d = Vec3f.from(x, y, z);
        vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        EntityBase entity = null;
        List list = level.getEntities(this, boundingBox.method_86(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d2 = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            EntityBase entity1 = (EntityBase) list.get(j);
            if (!entity1.method_1356() || (entity1 == owner || owner != null && entity1 == owner.vehicle) && timeInAir < 5 || serverSpawned) {
                continue;
            }
            float f2 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition1 = axisalignedbb.method_89(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d3 = vec3d.method_1294(movingobjectposition1.field_1988);
            if (d3 < d2 || d2 == 0.0D) {
                entity = entity1;
                d2 = d3;
            }
        }

        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            int k = level.getTileId(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
            if (movingobjectposition.field_1989 != null || k != BlockBase.TALLGRASS.id) {
                if (movingobjectposition.field_1989 != null) {
                    int l = damage;
                    if ((owner instanceof MonsterEntityType) && (movingobjectposition.field_1989 instanceof PlayerBase)) {
                        if (level.difficulty == 0) {
                            l = 0;
                        }
                        if (level.difficulty == 1) {
                            l = l / 3 + 1;
                        }
                        if (level.difficulty == 3) {
                            l = (l * 3) / 2;
                        }
                    }
                    if (movingobjectposition.field_1989 instanceof Living) {
                        SdkTools.attackEntityIgnoreDelay((Living) movingobjectposition.field_1989, owner, l);
                    } else {
                        movingobjectposition.field_1989.damage(owner, l);
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
        if (method_1334()) {
            for (int i1 = 0; i1 < 4; i1++) {
                float f4 = 0.25F;
                level.addParticle("bubble", x - velocityX * (double) f4, y - velocityY * (double) f4, z - velocityZ * (double) f4, velocityX, velocityY, velocityZ);
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
        Explosion explosion = new Explosion(level, null, x, (float) y, (float) z, 3F);
        explosion.kaboomPhase1();
        if (SdkConfig.explosionsDestroyBlocks) {
            explosion.kaboomPhase2(true);
        } else {
            level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
        }
        for (int i = 0; i < 32; i++) {
            level.addParticle("explode", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
            level.addParticle("smoke", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
        }

        remove();
    }

    private void laserAim() {
        if (owner != null) {
            double d = x - owner.x;
            double d1 = y - owner.y - (double) ((owner instanceof PlayerBase) ? 0.0F : owner.getEyeHeight());
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
