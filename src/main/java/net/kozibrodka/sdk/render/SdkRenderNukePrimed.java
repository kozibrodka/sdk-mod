package net.kozibrodka.sdk.render;

import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.events.BlockListener;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import org.lwjgl.opengl.GL11;

public class SdkRenderNukePrimed extends EntityRenderer
{

    public SdkRenderNukePrimed()
    {
        blockRenderer = new BlockRenderer();
        field_2678 = 0.5F;
    }

    public void func_153_a(SdkEntityNukePrimed sdkentitynukeprimed, double d, double d1, double d2,
                           float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        if(((float)sdkentitynukeprimed.fuse - f1) + 1.0F < 10F)
        {
            float f2 = 1.0F - (((float)sdkentitynukeprimed.fuse - f1) + 1.0F) / 10F;
            if(f2 < 0.0F)
            {
                f2 = 0.0F;
            }
            if(f2 > 1.0F)
            {
                f2 = 1.0F;
            }
            f2 *= f2;
            f2 *= f2;
            float f4 = 1.0F + f2 * 0.3F;
            GL11.glScalef(f4, f4, f4);
        }
        float f3 = (1.0F - (((float)sdkentitynukeprimed.fuse - f1) + 1.0F) / 100F) * 0.8F;
        bindTexture("/terrain.png");
        blockRenderer.method_48(BlockListener.blockNuke, 0, sdkentitynukeprimed.getBrightnessAtEyes(f1));
        if((sdkentitynukeprimed.fuse / 5) % 2 == 0)
        {
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f3);
            blockRenderer.method_48(BlockListener.blockNuke, 0, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        }
        GL11.glPopMatrix();
    }

    public void render(EntityBase entity, double d, double d1, double d2,
                         float f, float f1)
    {
        func_153_a((SdkEntityNukePrimed)entity, d, d1, d2, f, f1);
    }

    private BlockRenderer blockRenderer;
}
