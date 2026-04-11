
package net.kozibrodka.sdk.entitySentry;


import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class SdkModelSentry extends EntityModel
{

    public SdkModelSentry()
    {
        head = new ModelPart(0, 0);
        head.addCuboid(-3F, -3F, -1F, 6, 6, 6);
        fatBarrel = new ModelPart(24, 0);
        fatBarrel.addCuboid(-2F, -2F, -4F, 4, 4, 3);
        thinBarrel = new ModelPart(24, 7);
        thinBarrel.addCuboid(-1F, -1F, -9F, 2, 2, 5);
        stand = new ModelPart(46, 0);
        stand.addCuboid(-1F, 3F, -1F, 2, 10, 2);
        leg1 = new ModelPart(38, 0);
        leg1.addCuboid(-1F, 0.0F, -1F, 2, 14, 2);
        leg1.setPivot(0.0F, 13F, 0.0F);
        leg1.pitch = 0.6632251F;
        leg1.yaw = 0.7853981F;
        leg2 = new ModelPart(38, 0);
        leg2.addCuboid(-1F, 0.0F, -1F, 2, 14, 2);
        leg2.setPivot(0.0F, 13F, 0.0F);
        leg2.pitch = 0.6632251F;
        leg2.yaw = 2.356194F;
        leg3 = new ModelPart(38, 0);
        leg3.addCuboid(-1F, 0.0F, -1F, 2, 14, 2);
        leg3.setPivot(0.0F, 13F, 0.0F);
        leg3.pitch = 0.6632251F;
        leg3.yaw = 3.926991F;
        leg4 = new ModelPart(38, 0);
        leg4.addCuboid(-1F, 0.0F, -1F, 2, 14, 2);
        leg4.setPivot(0.0F, 13F, 0.0F);
        leg4.pitch = 0.6632251F;
        leg4.yaw = 5.497787F;
        foot1 = new ModelPart(0, 12);
        foot1.addCuboid(-8F, 23F, -8F, 5, 1, 5);
        foot2 = new ModelPart(0, 12);
        foot2.addCuboid(-8F, 23F, -8F, 5, 1, 5);
        foot2.yaw = 1.570796F;
        foot3 = new ModelPart(0, 12);
        foot3.addCuboid(-8F, 23F, -8F, 5, 1, 5);
        foot3.yaw = 3.141593F;
        foot4 = new ModelPart(0, 12);
        foot4.addCuboid(-8F, 23F, -8F, 5, 1, 5);
        foot4.yaw = 4.712389F;
        headModels = (new ModelPart[] {
            head, fatBarrel, thinBarrel
        });
        bodyModels = (new ModelPart[] {
            stand, leg1, leg2, leg3, leg4, foot1, foot2, foot3, foot4
        });
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, SdkEntitySentry sentry)
    {
        for(int i = 0; i < headModels.length; i++)
        {
            headModels[i].yaw = sentry.yaw / 57.29578F; //TODO:
            headModels[i].pitch = sentry.pitch / 57.29578F;
        }

    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5, SdkEntitySentry sentry)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, sentry);
        for(int i = 0; i < headModels.length; i++)
        {
            headModels[i].render(f5);
        }

        for(int j = 0; j < bodyModels.length; j++)
        {
            bodyModels[j].render(f5);
        }

    }

    ModelPart head;
    ModelPart fatBarrel;
    ModelPart thinBarrel;
    ModelPart stand;
    ModelPart leg1;
    ModelPart leg2;
    ModelPart leg3;
    ModelPart leg4;
    ModelPart foot1;
    ModelPart foot2;
    ModelPart foot3;
    ModelPart foot4;
    ModelPart headModels[];
    ModelPart bodyModels[];
}
