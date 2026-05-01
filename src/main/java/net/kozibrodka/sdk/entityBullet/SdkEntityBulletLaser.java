package net.kozibrodka.sdk.entityBullet;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.entity.SdkEntityLaserWolf;
import net.kozibrodka.sdk.entityNade.SdkEntitySmokeFX;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class SdkEntityBulletLaser extends SdkEntityBullet implements EntitySpawnDataProvider
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
        bulletDrop = ((SdkItemGun) ItemListener.itemGunLaser).bulletDrop;
    }

    public SdkEntityBulletLaser(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.5F, 0.5F);
    }

    @Override
    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunLaser).firingSound, ((SdkItemGun)ItemListener.itemGunLaser).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    protected MobEntity getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1.0D;
        MobEntity entityliving = null;
        List list = world.getEntities(this, boundingBox.expand(d, d, d));

        for (Object o : list) {
            Entity entity1 = (Entity) o;
            if (!(entity1 instanceof MobEntity)) {
                continue;
            }
            double d2 = entity1.getSquaredDistance(entity.x, entity.y, entity.z);
            if ((d < 0.0D || d2 < d * d) && (d1 == -1.0D || d2 < d1) && ((MobEntity) entity1).canSee(entity)) {
                d1 = d2;
                entityliving = (MobEntity) entity1;
            }
        }
        return entityliving;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void processServerEntityStatus(byte status) {
        if (status == 6) {
            MobEntity hited = getClosestEntityLiving(this,2.0D);
            if(hited != null) {
                for (int i1 = 0; i1 < 16; i1++) {
                    doSmoke(hited.x, hited.y + (double)(hited.height / 2.0F), hited.z, hited.width / 2.0F, hited.height / 2.0F);
                }
            }
        }  else {
            super.processServerEntityStatus(status);
        }
    }

    @Override
    public void tick()
    {
        baseTick();
        if (serverSpawned && !serverSoundPlayed) {
            playServerSound(world);
            serverSoundPlayed = true;
        }
        if(y > 256 || timeInAir >= maxTimeAir || y < 0){
            markDead();
        }
        timeInAir++;
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
                if(owner instanceof SdkEntityLaserWolf sdkentitylaserwolf)
                {
                    if(movingobjectposition.entity instanceof WolfEntity entitywolf)
                    {
                        if(sdkentitylaserwolf.getOwnerName().equals(entitywolf.getOwnerName()))
                        {
                            flag = true;
                        }
                    } else
                    if(movingobjectposition.entity instanceof PlayerEntity entityplayer)
                    {
                        if(sdkentitylaserwolf.getOwnerName().equals(entityplayer.name))
                        {
                            flag = true;
                        }
                    }
                }
                if(!flag)
                {
                    if(movingobjectposition.entity instanceof MobEntity)
                    {
                        world.broadcastEntityEvent(this, (byte)6);
                        if(entity instanceof PigEntity && !world.isRemote)
                        {
                            int l = random.nextInt(3);
                            for(int l1 = 0; l1 < l; l1++)
                            {
                                entity.dropItem(Item.COOKED_PORKCHOP.id, 1);
                            }

                        }
                        movingobjectposition.entity.markDead();
                        if(movingobjectposition.entity.dead && SdkEnvTool.isEnvClient())
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
                    if(SdkMap.BOUNCABLE_LIST.contains(k))
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
                            if(SdkTools.isFlammable(k))
                            {
                                world.setBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, Block.FIRE.id);
                            } else
                            {
                                world.setBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0);
                            }
                            if(SdkEnvTool.isEnvClient()) {
                                for (int k1 = 0; k1 < 16; k1++) {
                                    doSmoke((double) movingobjectposition.blockX + 0.5D, (double) movingobjectposition.blockY + 0.5D, (double) movingobjectposition.blockZ + 0.5D, 0.5D, 0.5D);
                                }
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
        int l = (damage- ((timeInAir-1)/10)); /// Opad DMG z czasem
        if (entity instanceof LivingEntity) {
            SdkTools.attackEntityIgnoreDelay((LivingEntity) entity, owner, l);
        } else {
            if(entity instanceof SdkVehicle panzer)
            {
                if(penetration > panzer.getArmorFactor()){
                    entity.damage(this, l);
                }
            }else {
                entity.damage(owner, l);
            }
        }
        noImpSound = true;
        markDead();
    }

    @Environment(EnvType.CLIENT)
    public void doSmoke(double d, double d1, double d2, double d3, double d4)
    {
        double d5 = (d + random.nextDouble() * d3 * 2D) - d3;
        double d6 = (d1 + random.nextDouble() * d4 * 2D) - d4;
        double d7 = (d2 + random.nextDouble() * d3 * 2D) - d3;
        SdkToolsRender.minecraft.particleManager.addParticle(new SdkEntitySmokeFX(world, d5, d6, d7, 0.0D, 0.0D, 0.0D, 2.5F, 1.0F, 1.0F, 1.0F));
    }

    @Override
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

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletLaser");
    }
}
