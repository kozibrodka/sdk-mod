package net.kozibrodka.sdk.entityBullet;


import net.kozibrodka.sdk.entityNade.SdkEntitySmokeFX;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.kozibrodka.sdk_api.events.ingame.mod_SdkGuns;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.WalkingBase;
import net.minecraft.entity.animal.Pig;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class SdkEntityBulletLaser extends SdkEntityBullet
{

    public SdkEntityBulletLaser(Level world)
    {
        super(world);
        setSize(0.5F, 0.5F);
    }

    public SdkEntityBulletLaser(Level world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setSize(0.5F, 0.5F);
    }

    public SdkEntityBulletLaser(Level world, EntityBase entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setSize(0.5F, 0.5F);
    }

    public void playServerSound(Level world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunLaser).firingSound, ((SdkItemGun)ItemListener.itemGunLaser).soundRangeFactor, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
    }

    public void tick()
    {
        baseTick();
        if(timeInAir == 200)
        {
            remove();
        }
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
        HitResult movingobjectposition = rayTraceBlocks(vec3d, vec3d1);
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
            float f1 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f1, f1, f1);
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
                boolean flag = false;
//                if(owner instanceof SdkEntityLaserWolf)       //inna czesc moda
//                {
//                    SdkEntityLaserWolf sdkentitylaserwolf = (SdkEntityLaserWolf)owner;
//                    if(movingobjectposition.field_1989 instanceof EntityWolf)
//                    {
//                        EntityWolf entitywolf = (EntityWolf)movingobjectposition.field_1989;
//                        if(sdkentitylaserwolf.getWolfOwner().equals(entitywolf.getWolfOwner()))
//                        {
//                            flag = true;
//                        }
//                    } else
//                    if(movingobjectposition.field_1989 instanceof EntityPlayer)
//                    {
//                        EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.field_1989;
//                        if(sdkentitylaserwolf.getWolfOwner().equals(entityplayer.username))
//                        {
//                            flag = true;
//                        }
//                    }
//                }
                if(!flag)
                {
                    if(movingobjectposition.field_1989 instanceof WalkingBase)
                    {
                        if(entity instanceof Pig)
                        {
                            int l = rand.nextInt(3);
                            for(int l1 = 0; l1 < l; l1++)
                            {
                                entity.dropItem(ItemBase.cookedPorkchop.id, 1);
                            }

                        }
                        movingobjectposition.field_1989.remove();
                        if(movingobjectposition.field_1989.removed)
                        {
                            for(int i1 = 0; i1 < 16; i1++)
                            {
                                doSmoke(movingobjectposition.field_1989.x, movingobjectposition.field_1989.y + (double)(movingobjectposition.field_1989.height / 2.0F), movingobjectposition.field_1989.z, movingobjectposition.field_1989.width / 2.0F, movingobjectposition.field_1989.height / 2.0F);
                            }

                        }
                    }
                    if(!movingobjectposition.field_1989.removed)
                    {
                        damageEntity(movingobjectposition.field_1989);
                    }
                    remove();
                }
            } else
            {
                int k = level.getTileId(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
                if(k != BlockBase.GLASS.id)
                {
                    if(k == BlockBase.DIAMOND_BLOCK.id || k == BlockBase.GOLD_BLOCK.id || k == BlockBase.IRON_BLOCK.id)
                    {
                        int j1 = movingobjectposition.field_1987;   //side hit
                        if(j1 == 0 || j1 == 1)
                        {
                            velocityY *= -1D;
                        } else
                        if(j1 == 2 || j1 == 3)
                        {
                            velocityZ *= -1D;
                        } else
                        if(j1 == 4 || j1 == 5)
                        {
                            velocityX *= -1D;
                        }
                    } else
                    {
                        if(k != BlockBase.BEDROCK.id && k != BlockBase.OBSIDIAN.id && BlockBase.BY_ID[k].getHardness() < 1000000F)
                        {
                            if(k == BlockBase.SAND.id)
                            {
                                level.setTile(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z, BlockBase.GLASS.id);
                            } else
                            if(SdkConfig.laserSetsFireToBlocks && SdkTools.isFlammable(k))
                            {
                                level.setTile(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z, BlockBase.FIRE.id);
                            } else
                            {
                                level.setTile(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z, 0);
                            }
                            for(int k1 = 0; k1 < 16; k1++)
                            {
                                doSmoke((double)movingobjectposition.x + 0.5D, (double)movingobjectposition.y + 0.5D, (double)movingobjectposition.z + 0.5D, 0.5D, 0.5D);
                            }

                        }
                        remove();
                    }
                }
            }
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(velocityY, f) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) { }
        for(; pitch - prevPitch >= 180F; prevPitch += 360F) { }
        for(; yaw - prevYaw < -180F; prevYaw -= 360F) { }
        for(; yaw - prevYaw >= 180F; prevYaw += 360F) { }
        pitch = prevPitch + (pitch - prevPitch) * 1.0F;
        yaw = prevYaw + (yaw - prevYaw) * 1.0F;
        setPosition(x, y, z);
    }

    public void damageEntity(EntityBase entity)
    {
        int i = damage;
        if((owner instanceof MonsterEntityType) && (entity instanceof PlayerBase))
        {
            if(level.difficulty == 0)
            {
                i = 0;
            }
            if(level.difficulty == 1)
            {
                i = i / 3 + 1;
            }
            if(level.difficulty == 3)
            {
                i = (i * 3) / 2;
            }
        }
        if(entity instanceof Living)
        {
            SdkTools.attackEntityIgnoreDelay((Living) entity, owner, i);
        } else
        {
//            entity.damage(owner, i);
            entity.damage(this, i); //TESTTTT
        }
    }

    public void doSmoke(double d, double d1, double d2, double d3, double d4)
    {
        double d5 = (d + rand.nextDouble() * d3 * 2D) - d3;
        double d6 = (d1 + rand.nextDouble() * d4 * 2D) - d4;
        double d7 = (d2 + rand.nextDouble() * d3 * 2D) - d3;
        SdkTools.minecraft.particleManager.addParticle(new SdkEntitySmokeFX(level, d5, d6, d7, 0.0D, 0.0D, 0.0D, 2.5F, 1.0F, 1.0F, 1.0F));
//        ModLoader.getMinecraftInstance().particleManager.addEffect(new SdkEntitySmokeFX(level, d5, d6, d7, 0.0D, 0.0D, 0.0D, 2.5F, 1.0F, 1.0F, 1.0F));
    }

    public float getBrightnessAtEyes(float f)
    {
        return 2.0F;
    }

    public HitResult rayTraceBlocks(Vec3f vec3d, Vec3f vec3d1)
    {
        return rayTraceBlocks_do(vec3d, vec3d1, false);
    }

    public HitResult rayTraceBlocks_do(Vec3f vec3d, Vec3f vec3d1, boolean flag)
    {
        if(Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z))
        {
            return null;
        }
        if(Double.isNaN(vec3d1.x) || Double.isNaN(vec3d1.y) || Double.isNaN(vec3d1.z))
        {
            return null;
        }
        int i = MathHelper.floor(vec3d1.x);
        int j = MathHelper.floor(vec3d1.y);
        int k = MathHelper.floor(vec3d1.z);
        int l = MathHelper.floor(vec3d.x);
        int i1 = MathHelper.floor(vec3d.y);
        int j1 = MathHelper.floor(vec3d.z);
        for(int k1 = 200; k1-- >= 0;)
        {
            if(Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z))
            {
                return null;
            }
            if(l == i && i1 == j && j1 == k)
            {
                return null;
            }
            double d = 999D;
            double d1 = 999D;
            double d2 = 999D;
            if(i > l)
            {
                d = (double)l + 1.0D;
            }
            if(i < l)
            {
                d = (double)l + 0.0D;
            }
            if(j > i1)
            {
                d1 = (double)i1 + 1.0D;
            }
            if(j < i1)
            {
                d1 = (double)i1 + 0.0D;
            }
            if(k > j1)
            {
                d2 = (double)j1 + 1.0D;
            }
            if(k < j1)
            {
                d2 = (double)j1 + 0.0D;
            }
            double d3 = 999D;
            double d4 = 999D;
            double d5 = 999D;
            double d6 = vec3d1.x - vec3d.x;
            double d7 = vec3d1.y - vec3d.y;
            double d8 = vec3d1.z - vec3d.z;
            if(d != 999D)
            {
                d3 = (d - vec3d.x) / d6;
            }
            if(d1 != 999D)
            {
                d4 = (d1 - vec3d.y) / d7;
            }
            if(d2 != 999D)
            {
                d5 = (d2 - vec3d.z) / d8;
            }
            byte byte0 = 0;
            if(d3 < d4 && d3 < d5)
            {
                if(i > l)
                {
                    byte0 = 4;
                } else
                {
                    byte0 = 5;
                }
                vec3d.x = d;
                vec3d.y += d7 * d3;
                vec3d.z += d8 * d3;
            } else
            if(d4 < d5)
            {
                if(j > i1)
                {
                    byte0 = 0;
                } else
                {
                    byte0 = 1;
                }
                vec3d.x += d6 * d4;
                vec3d.y = d1;
                vec3d.z += d8 * d4;
            } else
            {
                if(k > j1)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 3;
                }
                vec3d.x += d6 * d5;
                vec3d.y += d7 * d5;
                vec3d.z = d2;
            }
            Vec3f vec3d2 = Vec3f.from(vec3d.x, vec3d.y, vec3d.z);
            l = (int)(vec3d2.x = MathHelper.floor(vec3d.x));
            if(byte0 == 5)
            {
                l--;
                vec3d2.x++;
            }
            i1 = (int)(vec3d2.y = MathHelper.floor(vec3d.y));
            if(byte0 == 1)
            {
                i1--;
                vec3d2.y++;
            }
            j1 = (int)(vec3d2.z = MathHelper.floor(vec3d.z));
            if(byte0 == 3)
            {
                j1--;
                vec3d2.z++;
            }
            int l1 = level.getTileId(l, i1, j1);
            int i2 = level.getTileMeta(l, i1, j1);
            BlockBase block = BlockBase.BY_ID[l1];
            if(l1 > 0 && block.isCollidable(i2, flag) && l1 != BlockBase.GLASS.id)
            {
                HitResult movingobjectposition = block.method_1564(level, l, i1, j1, vec3d, vec3d1); //collisionRayTrace
                if(movingobjectposition != null)
                {
                    return movingobjectposition;
                }
            }
        }

        return null;
    }
}
