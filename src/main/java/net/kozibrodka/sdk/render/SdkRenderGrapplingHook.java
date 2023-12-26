package net.kozibrodka.sdk.render;

import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.util.maths.MathHelper;
import org.lwjgl.opengl.GL11;

public class SdkRenderGrapplingHook extends EntityRenderer
{

    public SdkRenderGrapplingHook()
    {
    }

    public void func_4011_a(SdkEntityGrapplingHook sdkentitygrapplinghook, double d, double d1, double d2,
                            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        bindTexture("/assets/sdk/stationapi/textures/item/itemGrapplingHookThrown.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 1.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.5F;
        GL11.glRotatef(180F - dispatcher.field_2497, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-dispatcher.field_2498, 1.0F, 0.0F, 0.0F);
        tessellator.start();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.vertex(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
        tessellator.vertex(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
        tessellator.vertex(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
        tessellator.vertex(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
        tessellator.draw();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
        if(sdkentitygrapplinghook.owner != null)
        {
            float f9 = ((sdkentitygrapplinghook.owner.prevYaw + (sdkentitygrapplinghook.owner.yaw - sdkentitygrapplinghook.owner.prevYaw) * f1) * 3.141593F) / 180F;
            float f10 = ((sdkentitygrapplinghook.owner.prevPitch + (sdkentitygrapplinghook.owner.pitch - sdkentitygrapplinghook.owner.prevPitch) * f1) * 3.141593F) / 180F;
            double d3 = MathHelper.sin(f9);
            double d4 = MathHelper.cos(f9);
            double d5 = MathHelper.sin(f10);
            double d6 = MathHelper.cos(f10);
            double d7 = (sdkentitygrapplinghook.owner.prevX + (sdkentitygrapplinghook.owner.x - sdkentitygrapplinghook.owner.prevX) * (double)f1) - d4 * 0.69999999999999996D - d3 * 0.5D * d6;
            double d8 = (sdkentitygrapplinghook.owner.prevY + (sdkentitygrapplinghook.owner.y - sdkentitygrapplinghook.owner.prevY) * (double)f1) - d5 * 0.5D;
            double d9 = ((sdkentitygrapplinghook.owner.prevZ + (sdkentitygrapplinghook.owner.z - sdkentitygrapplinghook.owner.prevZ) * (double)f1) - d3 * 0.69999999999999996D) + d4 * 0.5D * d6;
            if(dispatcher.options.thirdPerson)
            {
                float f11 = ((sdkentitygrapplinghook.owner.field_1013 + (sdkentitygrapplinghook.owner.field_1012 - sdkentitygrapplinghook.owner.field_1013) * f1) * 3.141593F) / 180F;
                double d11 = MathHelper.sin(f11);
                double d13 = MathHelper.cos(f11);
                d7 = (sdkentitygrapplinghook.owner.prevX + (sdkentitygrapplinghook.owner.x - sdkentitygrapplinghook.owner.prevX) * (double)f1) - d13 * 0.34999999999999998D - d11 * 0.84999999999999998D;
                d8 = (sdkentitygrapplinghook.owner.prevY + (sdkentitygrapplinghook.owner.y - sdkentitygrapplinghook.owner.prevY) * (double)f1) - 0.45000000000000001D;
                d9 = ((sdkentitygrapplinghook.owner.prevZ + (sdkentitygrapplinghook.owner.z - sdkentitygrapplinghook.owner.prevZ) * (double)f1) - d11 * 0.34999999999999998D) + d13 * 0.84999999999999998D;
            }
            double d10 = sdkentitygrapplinghook.prevX + (sdkentitygrapplinghook.x - sdkentitygrapplinghook.prevX) * (double)f1;
            double d12 = sdkentitygrapplinghook.prevY + (sdkentitygrapplinghook.y - sdkentitygrapplinghook.prevY) * (double)f1 + 0.25D;
            double d14 = sdkentitygrapplinghook.prevZ + (sdkentitygrapplinghook.z - sdkentitygrapplinghook.prevZ) * (double)f1;
            double d15 = (float)(d7 - d10);
            double d16 = (float)(d8 - d12);
            double d17 = (float)(d9 - d14);
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            tessellator.start(3);
            tessellator.colour(0);
            int i = 16;
            for(int j = 0; j <= i; j++)
            {
                float f12 = (float)j / (float)i;
                tessellator.addVertex(d + d15 * (double)f12, d1 + d16 * (double)(f12 * f12 + f12) * 0.5D + 0.25D, d2 + d17 * (double)f12);
            }

            tessellator.draw();
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        }
    }

    public void render(EntityBase entity, double d, double d1, double d2,
                       float f, float f1)
    {
        func_4011_a((SdkEntityGrapplingHook)entity, d, d1, d2, f, f1);
    }
}
