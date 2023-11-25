package net.kozibrodka.sdk.entity;

import net.minecraft.entity.EntityBase;

public class SdkEntityGrapplingHook extends EntityBase
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
        setSize(0.25F, 0.25F);
        ignoreFrustumCheck = true;
    }

    public SdkEntityGrapplingHook(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public SdkEntityGrapplingHook(World world, EntityPlayer entityplayer)
    {
        this(world);
        owner = entityplayer;
        mod_SdkGrapplingHook.grapplingHooks.put(entityplayer, this);
        setLocationAndAngles(entityplayer.posX, (entityplayer.posY + 1.6200000000000001D) - (double)entityplayer.yOffset, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        func_4042_a(motionX, motionY, motionZ, 1.5F, 1.0F);
        startPosX = owner.posX;
        startPosZ = owner.posZ;
    }

    protected void entityInit()
    {
    }

    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }

    public void func_4042_a(double d, double d1, double d2, float f,
                            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGround = 0;
    }

    public void setPositionAndRotation2(double d, double d1, double d2, float f,
                                        float f1, int i)
    {
        field_6387_m = d;
        field_6386_n = d1;
        field_6385_o = d2;
        field_6384_p = f;
        field_6383_q = f1;
        field_6388_l = i;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        velocityX = motionX = d;
        velocityY = motionY = d1;
        velocityZ = motionZ = d2;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(field_6388_l > 0)
        {
            double d = posX + (field_6387_m - posX) / (double)field_6388_l;
            double d1 = posY + (field_6386_n - posY) / (double)field_6388_l;
            double d2 = posZ + (field_6385_o - posZ) / (double)field_6388_l;
            double d4;
            for(d4 = field_6384_p - (double)rotationYaw; d4 < -180D; d4 += 360D) { }
            for(; d4 >= 180D; d4 -= 360D) { }
            rotationYaw += d4 / (double)field_6388_l;
            rotationPitch += (field_6383_q - (double)rotationPitch) / (double)field_6388_l;
            field_6388_l--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
            return;
        }
        if(!worldObj.multiplayerWorld)
        {
            if(owner == null)
            {
                setEntityDead();
                return;
            }
            ItemStack itemstack = owner.getCurrentEquippedItem();
            if(owner.isDead || !owner.isEntityAlive() || itemstack == null || itemstack.getItem() != mod_SdkGrapplingHook.itemGrapplingHook || getDistanceSqToEntity(owner) > 1024D)
            {
                setEntityDead();
                return;
            }
            if(bobber != null)
            {
                if(bobber.isDead)
                {
                    bobber = null;
                } else
                {
                    posX = bobber.posX;
                    posY = bobber.boundingBox.minY + (double)bobber.height * 0.80000000000000004D;
                    posZ = bobber.posZ;
                    return;
                }
            }
        }
        if(inGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);
            if(i != inTile)
            {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksInGround = 0;
                ticksInAir = 0;
            } else
            {
                ticksInGround++;
                if(ticksInGround == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            ticksInAir++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d3 = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.canBeCollidedWith() || entity1 == owner && ticksInAir < 5)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.func_1169_a(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d8 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if(d8 < d3 || d3 == 0.0D)
            {
                entity = entity1;
                d3 = d8;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(movingobjectposition.entityHit.attackEntityFrom(owner, 0))
                {
                    bobber = movingobjectposition.entityHit;
                }
            } else
            {
                double d5 = motionX;
                double d6 = motionZ;
                xTile = movingobjectposition.blockX;
                yTile = movingobjectposition.blockY;
                zTile = movingobjectposition.blockZ;
                inTile = worldObj.getBlockId(xTile, yTile, zTile);
                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
                float f3 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f3) * 0.05000000074505806D;
                posY -= (motionY / (double)f3) * 0.05000000074505806D;
                posZ -= (motionZ / (double)f3) * 0.05000000074505806D;
                if(movingobjectposition.hitVec.yCoord - (double)yTile == 1.0D && (worldObj.getBlockId(xTile, yTile + 1, zTile) == 0 || worldObj.getBlockId(xTile, yTile + 1, zTile) == Block.snow.blockID) && yTile + 1 < 128)
                {
                    if(d5 == 0.0D || d6 == 0.0D)
                    {
                        d5 = posX - startPosX;
                        d6 = posZ - startPosZ;
                    }
                    byte byte0 = (byte)(d5 > 0.0D ? 1 : -1);
                    byte byte1 = (byte)(d6 > 0.0D ? 1 : -1);
                    boolean flag = (worldObj.getBlockId(xTile - byte0, yTile, zTile) == 0 || worldObj.getBlockId(xTile - byte0, yTile, zTile) == Block.snow.blockID) && worldObj.getBlockId(xTile - byte0, yTile + 1, zTile) == 0;
                    boolean flag1 = (worldObj.getBlockId(xTile, yTile, zTile - byte1) == 0 || worldObj.getBlockId(xTile, yTile, zTile - byte1) == Block.snow.blockID) && worldObj.getBlockId(xTile, yTile + 1, zTile - byte1) == 0;
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
                        worldObj.setBlockWithNotify(xTile, yTile + 1, zTile, mod_SdkGrapplingHook.blockGrapplingHook.blockID);
                        worldObj.setBlockMetadataWithNotify(xTile, yTile + 1, zTile, byte2);
                        worldObj.setBlockWithNotify(k1, l1, i2, mod_SdkGrapplingHook.blockRope.blockID);
                        worldObj.setBlockMetadataWithNotify(k1, l1, i2, byte2);
                        if(owner != null)
                        {
                            owner.destroyCurrentEquippedItem();
                        }
                        setEntityDead();
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
        moveEntity(motionX, motionY, motionZ);
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.92F;
        if(onGround || isCollidedHorizontally)
        {
            f1 = 0.5F;
        }
        int k = 5;
        double d7 = 0.0D;
        for(int l = 0; l < k; l++)
        {
            double d10 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l + 0)) / (double)k) - 0.125D) + 0.125D;
            double d11 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l + 1)) / (double)k) - 0.125D) + 0.125D;
            AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBoxFromPool(boundingBox.minX, d10, boundingBox.minZ, boundingBox.maxX, d11, boundingBox.maxZ);
        }

        if(d7 > 0.0D)
        {
            if(ticksCatchable > 0)
            {
                ticksCatchable--;
            } else
            if(rand.nextInt(500) == 0)
            {
                ticksCatchable = rand.nextInt(30) + 10;
                motionY -= 0.20000000298023224D;
                worldObj.playSoundAtEntity(this, "random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                float f4 = MathHelper.floor_double(boundingBox.minY);
                for(int i1 = 0; (float)i1 < 1.0F + width * 20F; i1++)
                {
                    float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f7 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f5, f4 + 1.0F, posZ + (double)f7, motionX, motionY - (double)(rand.nextFloat() * 0.2F), motionZ);
                }

                for(int j1 = 0; (float)j1 < 1.0F + width * 20F; j1++)
                {
                    float f6 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f8 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f6, f4 + 1.0F, posZ + (double)f8, motionX, motionY, motionZ);
                }

            }
        }
        if(ticksCatchable > 0)
        {
            motionY -= (double)(rand.nextFloat() * rand.nextFloat() * rand.nextFloat()) * 0.20000000000000001D;
        }
        double d9 = d7 * 2D - 1.0D;
        motionY += 0.039999999105930328D * d9;
        if(d7 > 0.0D)
        {
            f1 = (float)((double)f1 * 0.90000000000000002D);
            motionY *= 0.80000000000000004D;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public int catchFish()
    {
        byte byte0 = 0;
        if(bobber != null)
        {
            double d = owner.posX - posX;
            double d1 = owner.posY - posY;
            double d2 = owner.posZ - posZ;
            double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
            double d4 = 0.10000000000000001D;
            bobber.motionX += d * d4;
            bobber.motionY += d1 * d4 + (double)MathHelper.sqrt_double(d3) * 0.080000000000000002D;
            bobber.motionZ += d2 * d4;
            byte0 = 3;
        }
        if(inGround)
        {
            byte0 = 2;
        }
        setEntityDead();
        return byte0;
    }

    public void setEntityDead()
    {
        super.setEntityDead();
        mod_SdkGrapplingHook.grapplingHooks.remove(owner);
        owner = null;
    }

    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public EntityPlayer owner;
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
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private double startPosX;
    private double startPosZ;
}
