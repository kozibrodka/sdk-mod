package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

public class SdkBlockPlaque extends TemplateBlockWithEntity implements BlockWithWorldRenderer
{

    public SdkBlockPlaque(Identifier iid)
    {
        super(iid, Material.WOOD);
//        renderType = k;
    }

    protected BlockEntity createBlockEntity()
    {
        return new SdkTileEntityPlaque();
    }

    public Box getCollisionShape(World world, int i, int j, int k)
    {
        int l = world.getBlockMeta(i, j, k);
        float f = 0.125F;
        if(l == 2)
        {
            setBoundingBox(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
        if(l == 3)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        if(l == 4)
        {
            setBoundingBox(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if(l == 5)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
        return super.getCollisionShape(world, i, j, k);
    }

    public Box getBoundingBox(World world, int i, int j, int k)
    {
        int l = world.getBlockMeta(i, j, k);
        float f = 0.125F;
        if(l == 2)
        {
            setBoundingBox(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
        if(l == 3)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        if(l == 4)
        {
            setBoundingBox(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if(l == 5)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
        return super.getBoundingBox(world, i, j, k);
    }

    public boolean isOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getRenderType()
    {
        return renderType;
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        } else
        {
            SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)world.getBlockEntity(i, j, k);
            return sdktileentityplaque.activated(world, entityplayer);
        }
    }

    public void onBreak(World world, int i, int j, int k)
    {
        if(world.isRemote)
        {
            return;
        } else
        {
            SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)world.getBlockEntity(i, j, k);
            sdktileentityplaque.removed(world);
            return;
        }
    }

    public boolean canPlaceAt(World world, int i, int j, int k)
    {
        if(world.method_1783(i - 1, j, k))
        {
            return true;
        }
        if(world.method_1783(i + 1, j, k))
        {
            return true;
        }
        if(world.method_1783(i, j, k - 1))
        {
            return true;
        } else
        {
            return world.method_1783(i, j, k + 1);
        }
    }

    public void onPlaced(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMeta(i, j, k);
        if((i1 == 0 || l == 2) && world.method_1783(i, j, k + 1))
        {
            i1 = 2;
        }
        if((i1 == 0 || l == 3) && world.method_1783(i, j, k - 1))
        {
            i1 = 3;
        }
        if((i1 == 0 || l == 4) && world.method_1783(i + 1, j, k))
        {
            i1 = 4;
        }
        if((i1 == 0 || l == 5) && world.method_1783(i - 1, j, k))
        {
            i1 = 5;
        }
        world.setBlockMeta(i, j, k, i1);
    }

    public void neighborUpdate(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMeta(i, j, k);
        boolean flag = false;
        if(i1 == 2 && world.method_1783(i, j, k + 1))
        {
            flag = true;
        }
        if(i1 == 3 && world.method_1783(i, j, k - 1))
        {
            flag = true;
        }
        if(i1 == 4 && world.method_1783(i + 1, j, k))
        {
            flag = true;
        }
        if(i1 == 5 && world.method_1783(i - 1, j, k))
        {
            flag = true;
        }
        if(!flag)
        {
            dropStacks(world, i, j, k, i1);
            world.setBlock(i, j, k, 0);
        }
        super.neighborUpdate(world, i, j, k, l);
    }

    public int getDroppedItemCount(Random random)
    {
        return 1;
    }

    private int renderType;

    @Override
    public boolean renderWorld(BlockRenderManager renderblocks, BlockView iblockaccess, int i, int j, int k) {
        Block block = this;
        Tessellator tessellator = Tessellator.INSTANCE;
        int l = TextureListener.plaque;
//        if(renderblocks.overrideBlockTexture >= 0)
//        {
//            l = renderblocks.overrideBlockTexture;
//        }
        float f = block.getLuminance(iblockaccess, i, j, k);
        tessellator.color(f, f, f);
        
//        int i1 = (l & 0xf) << 4;
//        int j1 = l & 0xf0;
//        double d = (float)i1 / 256F;
//        double d1 = ((float)i1 + 15.99F) / 256F;
//        double d2 = (float)j1 / 256F;
//        double d3 = ((float)j1 + 15.99F) / 256F;

        Atlas.Sprite atlasTX =  Atlases.getTerrain().getTexture(l);
        double d = atlasTX.getStartU();
        double d1 = atlasTX.getEndU();
        double d2 = atlasTX.getStartV();
        double d3 = atlasTX.getEndV();
        
        int k1 = iblockaccess.getBlockMeta(i, j, k);
        float f1 = 0.0F;
        float f2 = 0.05F;
        if(k1 == 5)
        {
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
        }
        if(k1 == 4)
        {
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d1, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
        }
        if(k1 == 3)
        {
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d1, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
        }
        if(k1 == 2)
        {
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
        }
        return true;
    }
}
