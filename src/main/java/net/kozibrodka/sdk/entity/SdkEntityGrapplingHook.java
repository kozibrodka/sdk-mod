package net.kozibrodka.sdk.entity;

import net.kozibrodka.sdk.events.BlockListener;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.HookListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.network.GrapplingPacket;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.List;

@HasTrackingParameters(trackingDistance = 240, updatePeriod = 1200, sendVelocity = TriState.TRUE)
public class SdkEntityGrapplingHook extends Entity implements EntitySpawnDataProvider
{

    public SdkEntityGrapplingHook(World world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        ticksInAir = 0;
        ticksCatchable = 0;
        bobber = null;
        setBoundingBoxSpacing(0.25F, 0.25F);
        ignoreFrustumCull = true;
    }

    public SdkEntityGrapplingHook(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        serverSpawned = true;
    }

    public SdkEntityGrapplingHook(World world, PlayerEntity entityplayer)
    {
        this(world);
        owner = entityplayer;
        HookListener.grapplingHooks.put(entityplayer, this);
        setPositionAndAnglesKeepPrevAngles(entityplayer.x, (entityplayer.y + 1.6200000000000001D) - (double)entityplayer.standingEyeHeight, entityplayer.z, entityplayer.yaw, entityplayer.pitch);
        x -= MathHelper.cos((yaw / 180F) * 3.141593F) * 0.16F;
        y -= 0.10000000149011612D;
        z -= MathHelper.sin((yaw / 180F) * 3.141593F) * 0.16F;
        setPosition(x, y, z);
        standingEyeHeight = 0.0F;
        float f = 0.4F;
        velocityX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F) * f;
        velocityZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F) * f;
        velocityY = -MathHelper.sin((pitch / 180F) * 3.141593F) * f;
        func_4042_a(velocityX, velocityY, velocityZ, 1.5F, 1.0F);
        startPosX = owner.x;
        startPosZ = owner.z;
    }

    @Override
    protected void initDataTracker()
    {
    }

    @Override
    public boolean shouldRender(double d)
    {
        return true;
    }

    public void func_4042_a(double d, double d1, double d2, float f,
                            float f1)
    {
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        velocityX = d;
        velocityY = d1;
        velocityZ = d2;
        float f3 = MathHelper.sqrt(d * d + d2 * d2);
        prevYaw = yaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevPitch = pitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGround = 0;
    }

    @Override
    public void setPositionAndAnglesAvoidEntities(double d, double d1, double d2, float f,
                                                  float f1, int i)
    {
        field_6387_m = d;
        field_6386_n = d1;
        field_6385_o = d2;
        field_6384_p = f;
        field_6383_q = f1;
        field_6388_l = i;
        velocityX = veloocityX;
        velocityY = veloocityY;
        velocityZ = veloocityZ;
    }

    @Override
    public void setVelocityClient(double d, double d1, double d2)
    {
        veloocityX = velocityX = d;
        veloocityY = velocityY = d1;
        velocityZ = veloocityZ = d2;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (serverSpawned && !serverSoundPlayed) {
            world.playSound(this, "sdk:grunt", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
            serverSoundPlayed = true;
        }
        if(!packetSend && SdkEnvTool.isEnvServ()){
            PacketHelper.sendToAllTracking(this, new GrapplingPacket(this.id, owner.name));
            packetSend = true;
        }
        if(field_6388_l > 0)
        {
            double d = x + (field_6387_m - x) / (double)field_6388_l;
            double d1 = y + (field_6386_n - y) / (double)field_6388_l;
            double d2 = z + (field_6385_o - z) / (double)field_6388_l;
            double d4;
            for(d4 = field_6384_p - (double)yaw; d4 < -180D; d4 += 360D) { }
            for(; d4 >= 180D; d4 -= 360D) { }
            yaw += d4 / (double)field_6388_l;
            pitch += (field_6383_q - (double)pitch) / (double)field_6388_l;
            field_6388_l--;
            setPosition(d, d1, d2);
            setRotation(yaw, pitch);
            return;
        }
        if(!world.isRemote)
        {
            if(owner == null)
            {
                markDead();
                return;
            }
            ItemStack itemstack = owner.getHand();
            if(owner.dead || !owner.isAlive() || itemstack == null || itemstack.getItem() != ItemListener.itemGrapplingHook || getSquaredDistance(owner) > 1024D)
            {
                markDead();
                return;
            }
            if(bobber != null)
            {
                if(bobber.dead)
                {
                    bobber = null;
                } else
                {
                    x = bobber.x;
                    y = bobber.boundingBox.minY + (double)bobber.height * 0.80000000000000004D;
                    z = bobber.z;
                    return;
                }
            }
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
                ticksInGround = 0;
                ticksInAir = 0;
            } else
            {
                ticksInGround++;
                if(ticksInGround == 1200)
                {
                    markDead();
                }
                return;
            }
        } else
        {
            ticksInAir++;
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
        double d3 = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.isCollidable() || entity1 == owner && ticksInAir < 5)
            {
                continue;
            }
            float f2 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d8 = vec3d.distanceTo(movingobjectposition1.pos);
            if(d8 < d3 || d3 == 0.0D)
            {
                entity = entity1;
                d3 = d8;
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
                if(movingobjectposition.entity.damage(owner, 0))
                {
                    bobber = movingobjectposition.entity;
                }
            } else
            {
                double d5 = velocityX;
                double d6 = velocityZ;
                xTile = movingobjectposition.blockX;
                yTile = movingobjectposition.blockY;
                zTile = movingobjectposition.blockZ;
                inTile = world.getBlockId(xTile, yTile, zTile);
                velocityX = (float)(movingobjectposition.pos.x - x);
                velocityY = (float)(movingobjectposition.pos.y - y);
                velocityZ = (float)(movingobjectposition.pos.z - z);
                float f3 = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
                x -= (velocityX / (double)f3) * 0.05000000074505806D;
                y -= (velocityY / (double)f3) * 0.05000000074505806D;
                z -= (velocityZ / (double)f3) * 0.05000000074505806D;
                if(movingobjectposition.pos.y - (double)yTile == 1.0D && (world.getBlockId(xTile, yTile + 1, zTile) == 0 || world.getBlockId(xTile, yTile + 1, zTile) == Block.SNOW.id) && yTile + 1 < 128)
                {
                    if(d5 == 0.0D || d6 == 0.0D)
                    {
                        d5 = x - startPosX;
                        d6 = z - startPosZ;
                    }
                    byte byte0 = (byte)(d5 > 0.0D ? 1 : -1);
                    byte byte1 = (byte)(d6 > 0.0D ? 1 : -1);
                    boolean flag = (world.getBlockId(xTile - byte0, yTile, zTile) == 0 || world.getBlockId(xTile - byte0, yTile, zTile) == Block.SNOW.id) && world.getBlockId(xTile - byte0, yTile + 1, zTile) == 0;
                    boolean flag1 = (world.getBlockId(xTile, yTile, zTile - byte1) == 0 || world.getBlockId(xTile, yTile, zTile - byte1) == Block.SNOW.id) && world.getBlockId(xTile, yTile + 1, zTile - byte1) == 0;
                    int k1 = xTile;
                    int l1 = yTile;
                    int i2 = zTile;
                    byte byte2 = 0;
                    boolean flag2 = false;
                    if(flag && !flag1 || flag && flag1 && Math.abs(d5) > Math.abs(d6))
                    {
                        k1 -= byte0;
                        flag2 = true;
                        if(byte0 > 0)
                        {
                            byte2 = 4;
                        } else
                        {
                            byte2 = 5;
                        }
                    } else
                    if(!flag && flag1 || flag && flag1 && Math.abs(d5) <= Math.abs(d6))
                    {
                        i2 -= byte1;
                        flag2 = true;
                        if(byte1 > 0)
                        {
                            byte2 = 2;
                        } else
                        {
                            byte2 = 3;
                        }
                    }
                    if(flag2)
                    {
                        world.setBlock(xTile, yTile + 1, zTile, BlockListener.blockGrapplingHook.id);
                        world.setBlockMeta(xTile, yTile + 1, zTile, byte2);
                        world.setBlock(k1, l1, i2, BlockListener.blockRope.id);
                        world.setBlockMeta(k1, l1, i2, byte2 + 4); ///
                        if(owner != null)
                        {
                            owner.clearStackInHand();
                        }
                        markDead();
                    } else
                    {
                        inGround = true;
                    }
                }
            }
        }
        if(inGround)
        {
            return;
        }
        move(velocityX, velocityY, velocityZ);
        float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(velocityY, f) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) { }
        for(; pitch - prevPitch >= 180F; prevPitch += 360F) { }
        for(; yaw - prevYaw < -180F; prevYaw -= 360F) { }
        for(; yaw - prevYaw >= 180F; prevYaw += 360F) { }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        float f1 = 0.92F;
        if(onGround || horizontalCollision)
        {
            f1 = 0.5F;
        }
        int k = 5;
        double d7 = 0.0D;
        for(int l = 0; l < k; l++)
        {
            double d10 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l)) / (double)k) - 0.125D) + 0.125D;
            double d11 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l + 1)) / (double)k) - 0.125D) + 0.125D;
            Box axisalignedbb1 = Box.createCached(boundingBox.minX, d10, boundingBox.minZ, boundingBox.maxX, d11, boundingBox.maxZ);
        }

        if(d7 > 0.0D)
        {
            if(ticksCatchable > 0)
            {
                ticksCatchable--;
            } else
            if(random.nextInt(500) == 0)
            {
                ticksCatchable = random.nextInt(30) + 10;
                velocityY -= 0.20000000298023224D;
                world.playSound(this, "random.splash", 0.25F, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
                float f4 = MathHelper.floor(boundingBox.minY);
                for(int i1 = 0; (float)i1 < 1.0F + width * 20F; i1++)
                {
                    float f5 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f7 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    world.addParticle("bubble", x + (double)f5, f4 + 1.0F, z + (double)f7, velocityX, velocityY - (double)(random.nextFloat() * 0.2F), velocityZ);
                }

                for(int j1 = 0; (float)j1 < 1.0F + width * 20F; j1++)
                {
                    float f6 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f8 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    world.addParticle("splash", x + (double)f6, f4 + 1.0F, z + (double)f8, velocityX, velocityY, velocityZ);
                }

            }
        }
        if(ticksCatchable > 0)
        {
            velocityY -= (double)(random.nextFloat() * random.nextFloat() * random.nextFloat()) * 0.20000000000000001D;
        }
        double d9 = d7 * 2D - 1.0D;
        velocityY += 0.039999999105930328D * d9;
        if(d7 > 0.0D)
        {
            f1 = (float)((double)f1 * 0.90000000000000002D);
            velocityY *= 0.80000000000000004D;
        }
        velocityX *= f1;
        velocityY *= f1;
        velocityZ *= f1;
        setPosition(x, y, z);
    }

    @Override
    public void writeNbt(NbtCompound nbttagcompound)
    {
        nbttagcompound.putShort("xTile", (short)xTile);
        nbttagcompound.putShort("yTile", (short)yTile);
        nbttagcompound.putShort("zTile", (short)zTile);
        nbttagcompound.putByte("inTile", (byte)inTile);
        nbttagcompound.putByte("inGround", (byte)(inGround ? 1 : 0));
    }

    @Override
    public void readNbt(NbtCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }

    @Override
    public float getShadowRadius()
    {
        return 0.0F;
    }

    public int catchFish()
    {
        byte byte0 = 0;
        if(bobber != null)
        {
            double d = owner.x - x;
            double d1 = owner.y - y;
            double d2 = owner.z - z;
            double d3 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
            double d4 = 0.10000000000000001D;
            bobber.velocityX += d * d4;
            bobber.velocityY += d1 * d4 + (double)MathHelper.sqrt(d3) * 0.080000000000000002D;
            bobber.velocityZ += d2 * d4;
            byte0 = 3;
        }
        if(inGround)
        {
            byte0 = 2;
        }
        markDead();
        return byte0;
    }

    @Override
    public void markDead()
    {
        super.markDead();
        HookListener.grapplingHooks.remove(owner);
        owner = null;
    }

    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public PlayerEntity owner;
    private int ticksInGround;
    private int ticksInAir;
    private int ticksCatchable;
    public Entity bobber;
    private int field_6388_l;
    private double field_6387_m;
    private double field_6386_n;
    private double field_6385_o;
    private double field_6384_p;
    private double field_6383_q;
    private double veloocityX;
    private double veloocityY;
    private double veloocityZ;
    private double startPosX;
    private double startPosZ;
    public boolean serverSpawned;
    public boolean serverSoundPlayed;
    public boolean packetSend;

    @Override
    public Identifier getHandlerIdentifier() {
        {
            return Identifier.of(EntityListener.MOD_ID, "GrapplingHook");
        }
    }
}
