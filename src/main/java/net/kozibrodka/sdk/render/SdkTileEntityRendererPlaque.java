package net.kozibrodka.sdk.render;

import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;

public class SdkTileEntityRendererPlaque extends TileEntityRenderer
{

    public SdkTileEntityRendererPlaque()
    {
        blockrender = new BlockRenderer();
    }

    public void render(TileEntityBase tileentity, double d, double d1, double d2,
                                 float f)
    {
        renderTileEntityPlaque((SdkTileEntityPlaque)tileentity, d, d1, d2, f);
    }

    public void renderTileEntityPlaque(SdkTileEntityPlaque sdktileentityplaque, double d, double d1, double d2, 
            float f)
    {
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.8F, (float)d2 + 0.5F);
        int i = sdktileentityplaque.level.getTileMeta(sdktileentityplaque.x, sdktileentityplaque.y, sdktileentityplaque.z);
        float f1 = sdktileentityplaque.getTile().getBrightness(sdktileentityplaque.level, sdktileentityplaque.x, sdktileentityplaque.y, sdktileentityplaque.z);
        switch(i)
        {
        case 5: // '\005'
            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
            break;

        case 4: // '\004'
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            break;

        case 3: // '\003'
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            break;

        case 2: // '\002'
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            break;
        }
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        ItemInstance itemstack = sdktileentityplaque.itemStack;
        if(itemstack != null)
        {
            if(itemstack.itemId < 256 && BlockRenderer.method_42(BlockBase.BY_ID[itemstack.itemId].getRenderType()))
            {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslatef(0.0F, -0.625F, 0.375F);
//                method_1064("/terrain.png");
                method_1064("/terrain.png");
                GL11.glPushMatrix();
                blockrender.method_48(BlockBase.BY_ID[itemstack.itemId], itemstack.getDamage(), 1.0F);
                GL11.glPopMatrix();
            } else
            {
                GL11.glScalef(0.875F, 0.875F, 0.875F);
                GL11.glTranslatef(0.0F, -0.5625F, 0.5F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                int j = itemstack.getTexturePosition();


//                if(itemstack.itemId < 256)
//                {
//                    method_1064("/terrain.png");
//                } else
//                {
//                    method_1064("/gui/items.png");
//                }

//                float f2 = (float)((j % 16) * 16 + 0) / 256F;
//                float f3 = (float)((j % 16) * 16 + 16) / 256F;
//                float f4 = (float)((j / 16) * 16 + 0) / 256F;
//                float f5 = (float)((j / 16) * 16 + 16) / 256F;
                Atlas.Sprite atlasTX =  Atlases.getGuiItems().getTexture(j);
                float f2 = (float) atlasTX.getStartU();
                float f3 = (float) atlasTX.getEndU();
                float f4 = (float) atlasTX.getStartV();
                float f5 = (float) atlasTX.getEndV();


                float f6 = 1.0F;
                float f7 = 0.5F;
                float f8 = 0.25F;
                GL11.glPushMatrix();
                tessellator.start();
                tessellator.colour(f1, f1, f1);
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.vertex(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
                tessellator.vertex(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
                tessellator.vertex(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
                tessellator.vertex(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
                tessellator.vertex(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
                tessellator.vertex(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
                tessellator.vertex(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
                tessellator.vertex(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    private BlockRenderer blockrender;
}
