package net.kozibrodka.sdk.atv;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;


public class SdkRenderAtv extends EntityRenderer
{

    public SdkRenderAtv()
    {
        field_2678 = 0.5F; //shadowsize
        model = new SdkModelAtv();
    }

    public void method_1908(SdkEntityAtv sdkentityatv, double d, double d1, double d2,
                           float f, float f1)
    {
        GL11.glPushMatrix();
        float f2 = sdkentityatv.prevPitch + (sdkentityatv.pitch - sdkentityatv.prevPitch) * f1;
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f2, 0.0F, 0.0F, 1.0F);
        bindTexture("/assets/sdk/stationapi/textures/mob/mobAtv.png");
        GL11.glScalef(-1F, -1F, 1.0F);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        model.render(0.0F, 0.0F, 0.0F, 0.0F, sdkentityatv.getTurnSpeedForRender(), 0.0625F);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPushMatrix();
        GL11.glScalef(0.6666667F, 0.6666667F, 0.6666667F);
        GL11.glScalef(0.7F, 0.7F, 0.7F);
        GL11.glRotatef(225F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.125F, 1.125F, 0.625F);
        Tessellator tessellator = Tessellator.INSTANCE;
        for(int i = -1; i <= 1; i += 2)
        {
            ItemInstance itemstack = null;
            if(i == -1)
            {
                itemstack = sdkentityatv.gunA;
            } else
            {
                itemstack = sdkentityatv.gunB;
                GL11.glTranslatef(0.0F, 0.0F, -1.25F);
            }
            if(itemstack != null)
            {

//                int j = itemstack.getTexturePosition();   //juz widze ze zle
                bindTexture("/gui/items.png");
//                float f3 = (float)((j % 16) * 16 + 0) / 256F;
//                float f4 = (float)((j % 16) * 16 + 16) / 256F;
//                float f5 = (float)((j / 16) * 16 + 0) / 256F;
//                float f6 = (float)((j / 16) * 16 + 16) / 256F;

                int l = itemstack.getTexturePosition();
                Atlas.Sprite testTex =  Atlases.getGuiItems().getTexture(l);
                float f3 = (float)(testTex.getStartU());
                float f4 = (float)(testTex.getEndU());
                float f5 = (float)(testTex.getStartV());
                float f6 = (float)(testTex.getEndV());

                float f7 = 1.0F;
                float f8 = 0.5F;
                float f9 = 0.25F;
                GL11.glPushMatrix();
                tessellator.start();
                tessellator.colour(f1, f1, f1);
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.vertex(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
                tessellator.vertex(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
                tessellator.vertex(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
                tessellator.vertex(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
                tessellator.vertex(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
                tessellator.vertex(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
                tessellator.vertex(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
                tessellator.vertex(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    public void render(EntityBase entity, double d, double d1, double d2,
                       float f, float f1)
    {
        method_1908((SdkEntityAtv)entity, d, d1, d2, f, f1);
    }

    protected SdkModelAtv model;
}
