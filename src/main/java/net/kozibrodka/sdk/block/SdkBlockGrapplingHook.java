package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.BlockListener;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class SdkBlockGrapplingHook extends TemplateBlockBase implements BlockWithWorldRenderer
{

    public SdkBlockGrapplingHook(Identifier iid)
    {
        super(iid, Material.WOOD);
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public Box getCollisionShape(Level world, int i, int j, int k)
    {
        return null;
    }

    public boolean isFullOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public boolean canPlaceAt(Level world, int i, int j, int k)
    {
        int l = world.getTileId(i, j - 1, k);
        if(l == 0 || !BlockBase.BY_ID[l].isFullOpaque())
        {
            return false;
        } else
        {
            return world.getMaterial(i, j - 1, k).blocksMovement();
        }
    }

    public void onAdjacentBlockUpdate(Level world, int i, int j, int k, int l)
    {
        func_314_h(world, i, j, k);
    }

    private boolean func_314_h(Level world, int i, int j, int k)
    {
        if(!canPlaceAt(world, i, j, k))
        {
            drop(world, i, j, k, world.getTileMeta(i, j, k));
            world.setTile(i, j, k, 0);
            onBlockDestroyed(world, i, j, k);
            return false;
        } else
        {
            return true;
        }
    }

    public int getDropId(int i, Random random)
    {
        return ItemListener.itemGrapplingHook.id;
    }

    public int getDropCount(Random random)
    {
        return 1;
    }

    public boolean isSideRendered(BlockView iblockaccess, int i, int j, int k, int l)
    {
        Material materialM = iblockaccess.getMaterial(i, j, k);
        if(l == 1)
        {
            return true;
        }
        if(materialM == material)
        {
            return false;
        } else
        {
            return super.isSideRendered(iblockaccess, i, j, k, l);
        }
    }

    public void activate(Level world, int i, int j, int k, int l)
    {
        onBlockDestroyed(world, i, j, k);
    }

    public void onDestroyedByExplosion(Level world, int i, int j, int k)
    {
        onBlockDestroyed(world, i, j, k);
    }

    private void onBlockDestroyed(Level world, int i, int j, int k)
    {
        int ai[][] = {
            {
                i - 1, j - 1, k
            }, {
                i + 1, j - 1, k
            }, {
                i, j - 1, k - 1
            }, {
                i, j - 1, k + 1
            }
        };
        for(int l = 0; l < ai.length; l++)
        {
            if(world.getTileId(ai[l][0], ai[l][1], ai[l][2]) != BlockListener.blockRope.id)
            {
                continue;
            }
            for(int i1 = ai[l][1]; world.getTileId(ai[l][0], i1, ai[l][2]) == BlockListener.blockRope.id; i1--)
            {
                world.setTile(ai[l][0], i1, ai[l][2], 0);
            }

        }

    }

    @Override
    public boolean renderWorld(BlockRenderer renderblocks, BlockView iblockaccess, int i, int j, int k) {
        int l = this.getColourMultiplier(iblockaccess, i, j, k);
        float f = (float)(l >> 16 & 0xff) / 255F;
        float f1 = (float)(l >> 8 & 0xff) / 255F;
        float f2 = (float)(l & 0xff) / 255F;
        return renderGrapplingHook2(renderblocks, i, j, k, f, f1, f2, iblockaccess);
    }

    public boolean renderGrapplingHook2(BlockRenderer renderblocks, int i, int j, int k, float f, float f1, float f2,
                                               BlockView iblockaccess)
    {
        Tessellator tessellator = Tessellator.INSTANCE;
        boolean flag = false;
        float f3 = 1.0F;
        float f4 = f3 * f;
        float f5 = f3 * f1;
        float f6 = f3 * f2;
        if(iblockaccess.getTileId(i,j,k) == BlockBase.GRASS.id)
        {
            f = f1 = f2 = 1.0F;
        }
        float f7 = getBrightness(iblockaccess, i, j, k);
        int l = iblockaccess.getTileMeta(i, j, k);
        if(isSideRendered(iblockaccess, i, j + 1, k, 1))
        {
            float f8 = getBrightness(iblockaccess, i, j + 1, k);
            if(maxY != 1.0D && !material.isLiquid())
            {
                f8 = f7;
            }
            tessellator.colour(f4 * f8, f5 * f8, f6 * f8);
//            int i1 = getBlockTexture(iblockaccess, i, j, k, 1);
            int i1 = TextureListener.grappling;
//            if(renderblocks.overrideBlockTexture >= 0)
//            {
//                i1 = renderblocks.overrideBlockTexture;
//            }
            renderGrapplingHook3(i, j, k, i1, l);
            flag = true;
        }
        return flag;
    }

    public void renderGrapplingHook3(double d, double d1, double d2, int i, int j)
    {
        Tessellator tessellator = Tessellator.INSTANCE;

//        int k = (i & 0xf) << 4;
//        int l = i & 0xf0;
//        double d3 = ((double)k + minX * 16D) / 256D;
//        double d4 = (((double)k + maxX * 16D) - 0.01D) / 256D;
//        double d5 = ((double)l + minZ * 16D) / 256D;
//        double d6 = (((double)l + maxZ * 16D) - 0.01D) / 256D;

        Atlas.Sprite atlasTX =  Atlases.getTerrain().getTexture(i);
        double d3 = atlasTX.getStartU();
        double d4 = atlasTX.getEndU();
        double d5 = atlasTX.getStartV();
        double d6 = atlasTX.getEndV();

//        if(minX < 0.0D || maxX > 1.0D)
//        {
//            d3 = atlasTX.getEndU();
//            d4 = atlasTX.getStartU();
//        }
//        if(minZ < 0.0D || maxZ > 1.0D)
//        {
//            d5 = atlasTX.getEndV();
//            d6 = atlasTX.getStartV();
//        }

        double d7 = d + minX;
        double d8 = d + maxX;
        double d9 = d1 + maxY;
        double d10 = d2 + minZ;
        double d11 = d2 + maxZ;
        switch(j)
        {
            case 2: // '\002'
                double d12 = d5;
                d5 = d6;
                d6 = d12;
                d12 = d3;
                d3 = d4;
                d4 = d12;
                break;

            case 4: // '\004'
                double d13 = d5;
                d5 = d6;
                d6 = d13;
                break;

            case 5: // '\005'
                double d14 = d3;
                d3 = d4;
                d4 = d14;
                break;
        }
        tessellator.vertex(d8, d9, d11, d4, d6);
        tessellator.vertex(d8, d9, d10, d4, d5);
        tessellator.vertex(d7, d9, d10, d3, d5);
        tessellator.vertex(d7, d9, d11, d3, d6);
    }
}
