
package net.kozibrodka.sdk.tileEntity;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.TilePos;
import net.minecraft.util.maths.Vec3f;

import java.util.*;

public class SdkExplosionNuke
{

    public SdkExplosionNuke(Level world, EntityBase entity, double d, double d1, double d2, float f, float f1, boolean flag)
    {
        isFlaming = flag;
        ExplosionRNG = new Random();
        destroyedBlockPositions = new HashSet();
        worldObj = world;
        exploder = entity;
        explosionSize = f;
        explosionX = d;
        explosionY = d1;
        explosionZ = d2;
        dropChance = f1;
    }

    public void doExplosionA()
    {
        float f = explosionSize;
        int i = 16;
        for(int j = 0; j < i; j++)
        {
            for(int l = 0; l < i; l++)
            {
label0:
                for(int j1 = 0; j1 < i; j1++)
                {
                    if(j != 0 && j != i - 1 && l != 0 && l != i - 1 && j1 != 0 && j1 != i - 1)
                    {
                        continue;
                    }
                    double d = ((float)j / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d1 = ((float)l / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d2 = ((float)j1 / ((float)i - 1.0F)) * 2.0F - 1.0F;
                    double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
                    d /= d3;
                    d1 /= d3;
                    d2 /= d3;
                    float f1 = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
                    double d5 = explosionX;
                    double d7 = explosionY;
                    double d9 = explosionZ;
                    float f2 = 0.3F;
                    do
                    {
                        if(f1 <= 0.0F)
                        {
                            continue label0;
                        }
                        int j4 = MathHelper.floor(d5);
                        int k4 = MathHelper.floor(d7);
                        int l4 = MathHelper.floor(d9);
                        int i5 = worldObj.getTileId(j4, k4, l4);
                        if(i5 > 0)
                        {
                            f1 -= (BlockBase.BY_ID[i5].getBlastResistance(exploder) + 0.3F) * f2;
                        }
                        if(f1 > 0.0F)
                        {
                            destroyedBlockPositions.add(new TilePos(j4, k4, l4));
                        }
                        d5 += d * (double)f2;
                        d7 += d1 * (double)f2;
                        d9 += d2 * (double)f2;
                        f1 -= f2 * 0.75F;
                    } while(true);
                }

            }

        }

        explosionSize *= 2.0F;
        int k = MathHelper.floor(explosionX - (double)explosionSize - 1.0D);
        int i1 = MathHelper.floor(explosionX + (double)explosionSize + 1.0D);
        int k1 = MathHelper.floor(explosionY - (double)explosionSize - 1.0D);
        int l1 = MathHelper.floor(explosionY + (double)explosionSize + 1.0D);
        int i2 = MathHelper.floor(explosionZ - (double)explosionSize - 1.0D);
        int j2 = MathHelper.floor(explosionZ + (double)explosionSize + 1.0D);
        List list = worldObj.getEntities(exploder, Box.createButWasteMemory(k, k1, i2, i1, l1, j2));
        Vec3f vec3d = Vec3f.from(explosionX, explosionY, explosionZ);
        for(int k2 = 0; k2 < list.size(); k2++)
        {
            EntityBase entity = (EntityBase)list.get(k2);
            double d4 = entity.distanceTo(explosionX, explosionY, explosionZ) / (double)explosionSize;
            if(d4 <= 1.0D)
            {
                double d6 = entity.x - explosionX;
                double d8 = entity.y - explosionY;
                double d10 = entity.z - explosionZ;
                double d11 = MathHelper.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
                d6 /= d11;
                d8 /= d11;
                d10 /= d11;
                double d12 = worldObj.method_163(vec3d, entity.boundingBox);
                double d13 = (1.0D - d4) * d12;
                entity.damage(exploder, (int)(((d13 * d13 + d13) / 2D) * 8D * (double)explosionSize + 1.0D));
                double d14 = d13;
                entity.velocityX += d6 * d14;
                entity.velocityY += d8 * d14;
                entity.velocityZ += d10 * d14;
            }
        }

        explosionSize = f;
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(destroyedBlockPositions);
        if(isFlaming)
        {
            for(int l2 = arraylist.size() - 1; l2 >= 0; l2--)
            {
                TilePos chunkposition = (TilePos)arraylist.get(l2);
                int i3 = chunkposition.x;
                int j3 = chunkposition.y;
                int k3 = chunkposition.z;
                int l3 = worldObj.getTileId(i3, j3, k3);
                int i4 = worldObj.getTileId(i3, j3 - 1, k3);
                if(l3 == 0 && BlockBase.FULL_OPAQUE[i4])
                {
                    worldObj.setTile(i3, j3, k3, BlockBase.FIRE.id);
                }
            }

        }
    }

    public void doExplosionB()
    {
        worldObj.playSound(explosionX, explosionY, explosionZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(destroyedBlockPositions);
        for(int i = arraylist.size() - 1; i >= 0; i--)
        {
            TilePos chunkposition = (TilePos)arraylist.get(i);
            int j = chunkposition.x;
            int k = chunkposition.y;
            int l = chunkposition.z;
            int i1 = worldObj.getTileId(j, k, l);
            for(int j1 = 0; j1 < 1; j1++)
            {
                double d = (float)j + worldObj.rand.nextFloat();
                double d1 = (float)k + worldObj.rand.nextFloat();
                double d2 = (float)l + worldObj.rand.nextFloat();
                double d3 = d - explosionX;
                double d4 = d1 - explosionY;
                double d5 = d2 - explosionZ;
                double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                d3 /= d6;
                d4 /= d6;
                d5 /= d6;
                double d7 = 0.5D / (d6 / (double)explosionSize + 0.10000000000000001D);
                d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
                d3 *= d7;
                d4 *= d7;
                d5 *= d7;
                worldObj.addParticle("explode", (d + explosionX * 1.0D) / 2D, (d1 + explosionY * 1.0D) / 2D, (d2 + explosionZ * 1.0D) / 2D, d3, d4, d5);
                worldObj.addParticle("smoke", d, d1, d2, d3, d4, d5);
            }

            if(i1 > 0)
            {
                BlockBase.BY_ID[i1].beforeDestroyedByExplosion(worldObj, j, k, l, worldObj.getTileMeta(j, k, l), dropChance);
                worldObj.setTile(j, k, l, 0);
                BlockBase.BY_ID[i1].onDestroyedByExplosion(worldObj, j, k, l);
            }
        }

    }

    public boolean isFlaming;
    private Random ExplosionRNG;
    private Level worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public EntityBase exploder;
    public float explosionSize;
    public Set destroyedBlockPositions;
    public float dropChance;
}
