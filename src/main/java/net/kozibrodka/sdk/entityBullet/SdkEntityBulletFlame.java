package net.kozibrodka.sdk.entityBullet;

import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.events.SdkGlass;
import net.kozibrodka.sdk_api.utils.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class SdkEntityBulletFlame extends SdkEntityBullet implements EntitySpawnDataProvider {

    public SdkEntityBulletFlame(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.5F, 0.5F);
        maxTimeAir = 30;
    }

    public SdkEntityBulletFlame(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.5F, 0.5F);
        maxTimeAir = 30;
        bulletDrop = ((SdkItemGun) ItemListener.itemGunFlamethrower).bulletDrop;
    }

    public SdkEntityBulletFlame(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                    float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.5F, 0.5F);
        maxTimeAir = 30;
    }

    @Override
    public float getBrightnessAtEyes(float f)
    {
        return 2.0F;
    }

    @Override
    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) ItemListener.itemGunFlamethrower).firingSound, ((SdkItemGun)ItemListener.itemGunFlamethrower).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public void tick() {
        baseTick();
        if (serverSpawned && !serverSoundPlayed) {
            playServerSound(world);
            serverSoundPlayed = true;
        }
        if(y > 256 || timeInAir >= maxTimeAir || y < 0){ /// !world.isRemote && owner == null -> relog dead
            markDead();
        }
        if (prevPitch == 0.0F && prevYaw == 0.0F) {
            float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
            prevYaw = yaw = (float) ((Math.atan2(velocityX, velocityZ) * 180D) / Math.PI);
            prevPitch = pitch = (float) ((Math.atan2(velocityY, f) * 180D) / Math.PI);
        }
        timeInAir++;

        Vec3d vec3d = Vec3d.createCached(x, y, z);
        Vec3d vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
//        HitResult movingobjectposition = world.raycast(vec3d, vec3d1);  /// {BEZ KOLIZJI - ON, LIQUID - OFF}
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, true);  /// {BEZ KOLIZJI - ON, LIQUID - ON}
//        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, false, true); /// {BEZ KOLIZJI - OFF, LIQUID - OFF}

        vec3d = Vec3d.createCached(x, y, z);
        vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = world.getEntities(this, boundingBox.stretch(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            Entity entity1 = (Entity) list.get(j);
            if (!entity1.isCollidable() || (entity1 == owner || owner != null && entity1 == owner.vehicle || owner != null && entity1 == owner.passenger) && timeInAir < 5 || serverSpawned) {
                continue;
            }
            float f4 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f4, f4, f4);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.pos);
            if (d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }

        /// FlameThrow HitResoult - Entity + Solid Block + Liquid
        if (movingobjectposition != null) {
            int k = world.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            int kMeta = world.getBlockMeta(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if (movingobjectposition.entity != null) {
//                int l = (damage- ((timeInAir-1)/10)); /// Opad DMG z czasem
                int l = damage;
                if (movingobjectposition.entity instanceof LivingEntity) {
                    SdkTools.attackEntityIgnoreDelay((LivingEntity) movingobjectposition.entity, owner, l);
                } else {
                    if(movingobjectposition.entity instanceof SdkVehicle panzer)
                    {
                        if(penetration > panzer.getArmorFactor()){
                            movingobjectposition.entity.damage(this, l);
                        }
                    }else {
                        movingobjectposition.entity.damage(owner, l);
                    }
                }
                playImpactSound(world, Material.PLANT);
                movingobjectposition.entity.fireTicks = 300;
                noImpSound = true;
                markDead();
            }
            xTile = movingobjectposition.blockX;
            yTile = movingobjectposition.blockY;
            zTile = movingobjectposition.blockZ;
            inTile = k;

            if(inTile == 9){ /// WODA still
                playImpactSound(world, Material.WATER);
                world.playSound(this, "random.fizz", 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
                markDead();
            }
            if(inTile == 11){ /// LAVA still
                playImpactSound(world, Material.WATER);
                world.playSound(this, "random.fizz", 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
                markDead();
            }

            if(SdkMap.MELTABLE_LIST.contains(inTile))
            {
                if(SdkEnvTool.isEnvClient()){
                    destroyBlockClient(inTile, kMeta, 4.0F); // 8.0F też spoko
                }
                playImpactSound(world, Material.WATER);
                if(!world.isRemote) {
                    world.setBlock(xTile, yTile, zTile, Block.FLOWING_WATER.id);
                }
            } else
            {
                byte byte0 = (byte)(velocityX > 0.0D ? 1 : -1);
                byte byte1 = (byte)(velocityY > 0.0D ? 1 : -1);
                byte byte2 = (byte)(velocityZ > 0.0D ? 1 : -1);
                boolean flag = SdkMap.EVAPORABLE_LIST.contains(world.getBlockId(xTile - byte0, yTile, zTile));
                boolean flag1 = SdkMap.EVAPORABLE_LIST.contains(world.getBlockId(xTile, yTile - byte1, zTile));
                boolean flag2 = SdkMap.EVAPORABLE_LIST.contains(world.getBlockId(xTile, yTile, zTile - byte2));
                if(!world.isRemote) {
                    if (flag) {
                        world.setBlock(xTile - byte0, yTile, zTile, Block.FIRE.id);
                    }
                    if (flag1) {
                        world.setBlock(xTile, yTile - byte1, zTile, Block.FIRE.id);
                    }
                    if (flag2) {
                        world.setBlock(xTile, yTile, zTile - byte2, Block.FIRE.id);
                    }
                }
                if(flag || flag1 || flag2) {
                    playImpactSound(world, Material.PLANT);
                }
            }
            markDead();

        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        float f1 = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float) ((Math.atan2(velocityX, velocityZ) * 180D) / Math.PI);
        for (pitch = (float) ((Math.atan2(velocityY, f1) * 180D) / Math.PI); pitch - prevPitch < -180F; prevPitch -= 360F) {
        }
        for (; pitch - prevPitch >= 180F; prevPitch += 360F) {
        }
        for (; yaw - prevYaw < -180F; prevYaw -= 360F) {
        }
        for (; yaw - prevYaw >= 180F; prevYaw += 360F) {
        }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        if(world.updateMovementInFluid(boundingBox, Material.WATER, this))
        {
            world.playSound(this, "random.fizz", 0.8F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
            markDead();
        }
        setPosition(x, y, z);
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(EntityListener.MOD_ID, "BulletFlame");
    }

}
