package net.kozibrodka.sdk.entityBullet;


import net.kozibrodka.sdk.entityNade.SdkEntitySmokeFX;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.SdkConfig;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class SdkEntityBulletLaser extends SdkEntityBullet
{

    public SdkEntityBulletLaser(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public SdkEntityBulletLaser(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public SdkEntityBulletLaser(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunLaser).firingSound, ((SdkItemGun)ItemListener.itemGunLaser).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void playImpactSound(World world) {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunLaser).impactSound, 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    public void tick()
    {
        baseTick();
        if(timeInAir == 200)
        {
            markDead();
        }
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
        HitResult movingobjectposition = rayTraceBlocks(vec3d, vec3d1);
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
            float f1 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f1, f1, f1);
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
                    if(movingobjectposition.entity instanceof MobEntity)
                    {
                        if(entity instanceof PigEntity)
                        {
                            int l = random.nextInt(3);
                            for(int l1 = 0; l1 < l; l1++)
                            {
                                entity.dropItem(Item.COOKED_PORKCHOP.id, 1);
                            }

                        }
                        movingobjectposition.entity.markDead();
                        if(movingobjectposition.entity.dead)
                        {
                            for(int i1 = 0; i1 < 16; i1++)
                            {
                                doSmoke(movingobjectposition.entity.x, movingobjectposition.entity.y + (double)(movingobjectposition.entity.height / 2.0F), movingobjectposition.entity.z, movingobjectposition.entity.width / 2.0F, movingobjectposition.entity.height / 2.0F);
                            }

                        }
                    }
                    if(!movingobjectposition.entity.dead)
                    {
                        damageEntity(movingobjectposition.entity);
                    }
                    markDead();
                }
            } else
            {
                int k = world.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
                if(k != Block.GLASS.id)
                {
                    if(k == Block.DIAMOND_BLOCK.id || k == Block.GOLD_BLOCK.id || k == Block.IRON_BLOCK.id)
                    {
                        int j1 = movingobjectposition.side;   //side hit
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
                        if(k != Block.BEDROCK.id && k != Block.OBSIDIAN.id && Block.BLOCKS[k].getHardness() < 1000000F)
                        {
                            if(k == Block.SAND.id)
                            {
                                world.setBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, Block.GLASS.id);
                            } else
                            if(SdkConfig.laserSetsFireToBlocks && SdkTools.isFlammable(k))
                            {
                                world.setBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, Block.FIRE.id);
                            } else
                            {
                                world.setBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0);
                            }
                            for(int k1 = 0; k1 < 16; k1++)
                            {
                                doSmoke((double)movingobjectposition.blockX + 0.5D, (double)movingobjectposition.blockY + 0.5D, (double)movingobjectposition.blockZ + 0.5D, 0.5D, 0.5D);
                            }

                        }
                        markDead();
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

    public void damageEntity(Entity entity)
    {
        int i = damage;
        if((owner instanceof Monster) && (entity instanceof PlayerEntity))
        {
            if(world.difficulty == 0)
            {
                i = 0;
            }
            if(world.difficulty == 1)
            {
                i = i / 3 + 1;
            }
            if(world.difficulty == 3)
            {
                i = (i * 3) / 2;
            }
        }
        if(entity instanceof LivingEntity)
        {
            SdkTools.attackEntityIgnoreDelay((LivingEntity) entity, owner, i);
        } else
        {
////            entity.damage(owner, i);
//            entity.damage(this, i); //TESTTTT

            if(entity instanceof WW2Plane || entity instanceof WW2Tank || entity instanceof WW2Truck || entity instanceof WW2Cannon)
            {
                if(entity instanceof WW2Truck && penetration >= 1)
                {
                    entity.damage(this, i);
                }
                if(entity instanceof WW2Plane && penetration >= 2)
                {
                    entity.damage(this, i);
                }
                if((entity instanceof WW2Tank && penetration >= 3) || (entity instanceof WW2Cannon && penetration >= 3))
                {
                    entity.damage(this, i);
                }
            }else {
                entity.damage(owner, i);
            }
        }
    }

    public void doSmoke(double d, double d1, double d2, double d3, double d4)
    {
        double d5 = (d + random.nextDouble() * d3 * 2D) - d3;
        double d6 = (d1 + random.nextDouble() * d4 * 2D) - d4;
        double d7 = (d2 + random.nextDouble() * d3 * 2D) - d3;
//        world.addParticle(); /// ????
        SdkToolsRender.minecraft.particleManager.addParticle(new SdkEntitySmokeFX(world, d5, d6, d7, 0.0D, 0.0D, 0.0D, 2.5F, 1.0F, 1.0F, 1.0F));
//        ModLoader.getMinecraftInstance().particleManager.addEffect(new SdkEntitySmokeFX(level, d5, d6, d7, 0.0D, 0.0D, 0.0D, 2.5F, 1.0F, 1.0F, 1.0F));
    }

    public float getBrightnessAtEyes(float f)
    {
        return 2.0F;
    }

    public HitResult rayTraceBlocks(Vec3d vec3d, Vec3d vec3d1)
    {
        return rayTraceBlocks_do(vec3d, vec3d1, false);
    }

    public HitResult rayTraceBlocks_do(Vec3d vec3d, Vec3d vec3d1, boolean flag)
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
            Vec3d vec3d2 = Vec3d.createCached(vec3d.x, vec3d.y, vec3d.z);
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
            int l1 = world.getBlockId(l, i1, j1);
            int i2 = world.getBlockMeta(l, i1, j1);
            Block block = Block.BLOCKS[l1];
            if(l1 > 0 && block.hasCollision(i2, flag) && l1 != Block.GLASS.id)
            {
                HitResult movingobjectposition = block.raycast(world, l, i1, j1, vec3d, vec3d1); //collisionRayTrace
                if(movingobjectposition != null)
                {
                    return movingobjectposition;
                }
            }
        }

        return null;
    }
}
