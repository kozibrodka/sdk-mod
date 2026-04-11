package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk.mixin.EntityBaseAccessor;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityRope;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.model.block.BlockWithInventoryRenderer;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
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

    public void onEntityCollision(World world, int i, int j, int k, Entity entity)
    {
        if(entity instanceof LivingEntity)
        {
            ((EntityBaseAccessor)entity).setFallDistance(0.0F);
            if(entity.velocityY < (double)descensionSpeed)
            {
                entity.velocityY = descensionSpeed;
            }
            if(entity.horizontalCollision)
            {
                entity.velocityY = ascensionSpeed;
            }
        }
    }

    protected BlockEntity createBlockEntity()
    {
        return new SdkTileEntityRope();
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

    public int getDroppedItemCount(Random random)
    {
        return 0;
    }

    public float ascensionSpeed;
    public float descensionSpeed;
    private int renderType;

    @Override
    public boolean renderWorld(BlockRenderManager renderblocks, BlockView iblockaccess, int i, int j, int k) {
        Tessellator tessellator = Tessellator.INSTANCE;
//        int l = block.getBlockTextureFromSide(0);
        int l = TextureListener.rope;
//        if(renderblocks.textureOverride >= 0)
//        {
//            l = renderblocks.textureOverride;
//        }
        float f = this.getLuminance(iblockaccess, i, j, k);
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
    public void renderInventory(BlockRenderManager tileRenderer, int meta) {
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
