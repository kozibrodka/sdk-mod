package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkEntityShell;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkEntityBulletRocketLaser extends SdkEntityShell implements EntitySpawnDataProvider {

    public SdkEntityBulletRocketLaser(World world)
    {
        super(world);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityBulletRocketLaser(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3, float f4) {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        rotationYawOffset = 0.0F;
        setBoundingBoxSpacing(0.25F, 0.25F);
        rotationYawOffset = f3;
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).firingSound, ((SdkItemGun)ItemListener.itemGunRocketLauncherLaser).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void tick() {
        if (timeInAir > 5 && !world.isRemote) {
            laserAim();
        }
        super.tick();
    }

    @Override
    public String getServerExploSound() {
        return ((SdkItemGun) ItemListener.itemGunRocketLauncherLaser).explosionSound;
    }

    private void laserAim() {
        if (owner != null) {
//            System.out.println(owner.x + " " + owner.y + " " + owner.z);
            double servOffY = 0.0D;
            if(SdkEnvTool.isEnvServ()){
                servOffY = 1.62D;
            }
            double d = x - owner.x;
            double d1 = y - (owner.y + servOffY) - (double) ((owner instanceof PlayerEntity) ? 0.0F : owner.getShadowRadius());
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
        if(touchedWater){
            velocityY -= 0.1D;
        }
    }

    public float rotationYawOffset;
    protected static final float MAX_AIMING_ANGLE = 30F;
    protected static final float MAX_TURNING_ANGLE = 10F;

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletRocketLaser");
    }
}
