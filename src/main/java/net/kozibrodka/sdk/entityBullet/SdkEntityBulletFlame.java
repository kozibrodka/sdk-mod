package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class SdkEntityBulletFlame extends SdkEntityBullet
{

    public SdkEntityBulletFlame(Level world)
    {
        super(world);
        setSize(0.5F, 0.5F);
    }

    public SdkEntityBulletFlame(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setSize(0.5F, 0.5F);
    }

    public SdkEntityBulletFlame(Level world, EntityBase entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setSize(0.5F, 0.5F);
    }

    public void playServerSound(Level world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunFlamethrower).firingSound, ((SdkItemGun)ItemListener.itemGunFlamethrower).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }

    public void tick()
    {
        baseTick();
        if(timeInAir == 30)
        {
            remove();
        }
        level.addParticle("smoke", x, y, z, 0.0D, 0.0D, 0.0D);
        if(inGround)
        {
            int i = level.getTileId(xTile, yTile, zTile);
            if(i != inTile)
            {
                inGround = false;
                velocityX *= rand.nextFloat() * 0.2F;
                velocityY *= rand.nextFloat() * 0.2F;
                velocityZ *= rand.nextFloat() * 0.2F;
                timeInTile = 0;
                timeInAir = 0;
            }
        } else
        {
            timeInAir++;
        }
        Vec3f vec3d = Vec3f.from(x, y, z);
        Vec3f vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = level.method_160(vec3d, vec3d1);
        vec3d = Vec3f.from(x, y, z);
        vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        EntityBase entity = null;
        List list = level.getEntities(this, boundingBox.method_86(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            EntityBase entity1 = (EntityBase)list.get(j);
            if(!entity1.method_1356() || (entity1 == owner || owner != null && entity1 == owner.vehicle) && timeInAir < 5 || serverSpawned)
            {
                continue;
            }
            float f = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f, f, f);
            HitResult movingobjectposition1 = axisalignedbb.method_89(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.method_1294(movingobjectposition1.field_1988);
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
            if(movingobjectposition.field_1989 != null)
            {
                int k = damage;
                if((owner instanceof MonsterEntityType) && (movingobjectposition.field_1989 instanceof PlayerBase))
                {
                    if(level.difficulty == 0)
                    {
                        k = 0;
                    }
                    if(level.difficulty == 1)
                    {
                        k = k / 3 + 1;
                    }
                    if(level.difficulty == 3)
                    {
                        k = (k * 3) / 2;
                    }
                }
                if(movingobjectposition.field_1989 instanceof Living)
                {
                    SdkTools.attackEntityIgnoreDelay((Living)movingobjectposition.field_1989, owner, k);
                } else
                {
                    movingobjectposition.field_1989.damage(owner, k);
                }
                movingobjectposition.field_1989.fire = 300;
            } else
            {
                xTile = movingobjectposition.x;
                yTile = movingobjectposition.y;
                zTile = movingobjectposition.z;
                if(level.getTileId(xTile, yTile, zTile) == BlockBase.ICE.id && BlockBase.ICE.getHardness() < 1000000F)
                {
                    BlockBase.ICE.onBlockRemoved(level, xTile, yTile, zTile); //nie dziala
                } else
                {
                    byte byte0 = (byte)(velocityX > 0.0D ? 1 : -1);
                    byte byte1 = (byte)(velocityY > 0.0D ? 1 : -1);
                    byte byte2 = (byte)(velocityZ > 0.0D ? 1 : -1);
                    boolean flag = level.getTileId(xTile - byte0, yTile, zTile) == 0 || level.getTileId(xTile - byte0, yTile, zTile) == BlockBase.SNOW.id;
                    boolean flag1 = level.getTileId(xTile, yTile - byte1, zTile) == 0 || level.getTileId(xTile, yTile - byte1, zTile) == BlockBase.SNOW.id;
                    boolean flag2 = level.getTileId(xTile, yTile, zTile - byte2) == 0 || level.getTileId(xTile, yTile, zTile - byte2) == BlockBase.SNOW.id;
                    if(flag)
                    {
                        level.setTile(xTile - byte0, yTile, zTile, BlockBase.FIRE.id);
                    }
                    if(flag1)
                    {
                        level.setTile(xTile, yTile - byte1, zTile, BlockBase.FIRE.id);
                    }
                    if(flag2)
                    {
                        level.setTile(xTile, yTile, zTile - byte2, BlockBase.FIRE.id);
                    }
                }
            }
            remove();
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        setRotationToVelocity();
        if(level.method_170(boundingBox, Material.WATER, this)) //handlematerialacceleration
        {
            level.playSound(this, "random.fizz", 0.8F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
            remove();
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

    public void setVelocity(double d, double d1, double d2)
    {
        super.setVelocity(d, d1, d2);
        setRotationToVelocity();
    }

    public float getBrightnessAtEyes(float f)
    {
        return 2.0F;
    }
}
