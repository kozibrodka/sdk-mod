package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk.mixin.EntityBaseAccessor;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityRope;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.client.model.block.BlockWithInventoryRenderer;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

public class SdkBlockRope extends TemplateBlockWithEntity implements BlockWithWorldRenderer, BlockWithInventoryRenderer
{

    public SdkBlockRope(Identifier iid)
    {
        super(iid, Material.WOOL);
        ascensionSpeed = 0.2F;
        descensionSpeed = -0.15F;
    }

    public void onEntityCollision(Level world, int i, int j, int k, EntityBase entity)
    {
        if(entity instanceof Living)
        {
            ((EntityBaseAccessor)entity).setFallDistance(0.0F);
            if(entity.velocityY < (double)descensionSpeed)
            {
                entity.velocityY = descensionSpeed;
            }
            if(entity.field_1624)
            {
                entity.velocityY = ascensionSpeed;
            }
        }
    }

    protected TileEntityBase createTileEntity()
    {
        return new SdkTileEntityRope();
    }

    public Box getCollisionShape(Level world, int i, int j, int k)
    {
        int l = world.getTileMeta(i, j, k);
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

    public Box getOutlineShape(Level world, int i, int j, int k)
    {
        int l = world.getTileMeta(i, j, k);
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
        return super.getOutlineShape(world, i, j, k);
    }

    public boolean isFullOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getDropCount(Random random)
    {
        return 0;
    }

    public float ascensionSpeed;
    public float descensionSpeed;
    private int renderType;

    @Override
    public boolean renderWorld(BlockRenderer renderblocks, BlockView iblockaccess, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
//        int l = block.getBlockTextureFromSide(0);
        int l = TextureListener.rope;
//        if(renderblocks.textureOverride >= 0)
//        {
//            l = renderblocks.textureOverride;
//        }
        float f = this.getBrightness(iblockaccess, i, j, k);
        tessellator.colour(f, f, f);

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

        int k1 = iblockaccess.getTileMeta(i, j, k);
        float f1 = 0.0F;
        float f2 = 0.05F;
        if(k1 == 5)
        {
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d1, d3);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
            tessellator.vertex((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d, d2);
            tessellator.vertex((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
        }
        if(k1 == 4)
        {
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.vertex((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
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
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d1, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
        }
        if(k1 == 2)
        {
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
            tessellator.vertex((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.vertex((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
        }
        return true;
    }

    @Override
    public void renderInventory(BlockRenderer tileRenderer, int meta) {
//        Tessellator tessellator = Tessellator.INSTANCE;
//        int l = TextureListener.rope;
//        Atlas.Sprite atlasTX =  Atlases.getTerrain().getTexture(l);
//        double d = atlasTX.getStartU();
//        double d1 = atlasTX.getEndU();
//        double d2 = atlasTX.getStartV();
//        double d3 = atlasTX.getEndV();
//        float f1 = 0.0F;
//        float f2 = 0.05F;
//        tessellator.draw();

    }
}
