
package net.kozibrodka.sdk.render;


import net.kozibrodka.sdk.entitySentry.SdkEntitySentry;
import net.kozibrodka.sdk.entitySentry.SdkModelSentry;
import net.kozibrodka.sdk_api.events.parachute.SdkModelParachute;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelBase;
import net.minecraft.entity.EntityBase;
import org.lwjgl.opengl.GL11;

public class SdkRenderSentry extends EntityRenderer
{
    public SdkRenderSentry()
    {
//        ((EntityRendererAccessor)this).setField_2676(new SdkModelParachute());
        field_2678 = 0.0F;
        model = new SdkModelSentry();
//        super.field_2676 = new SdkModelParachute();
    }

    public void method_1908(SdkEntitySentry entity, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1 + 1.5F, (float)d2);
        bindTexture("/assets/sdk/stationapi/textures/mob/mobSentry.png");
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        model.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, entity); //0.0625F
        GL11.glPopMatrix();
    }

    //    @Override
//    public void render(EntityBase arg, double d, double e, double f, float g, float h) {
//        model.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
//    }
    @Override
    public void render(EntityBase entity, double d, double d1, double d2,
                       float f, float f1)
    {
        method_1908((SdkEntitySentry)entity, d, d1, d2, f, f1);
    }

    protected SdkModelSentry model;

}
