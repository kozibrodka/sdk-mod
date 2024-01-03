
package net.kozibrodka.sdk.entitySentry;


import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModelBase;

public class SdkModelSentry extends EntityModelBase
{

    public SdkModelSentry()
    {
        head = new Cuboid(0, 0);
        head.method_1817(-3F, -3F, -1F, 6, 6, 6);
        fatBarrel = new Cuboid(24, 0);
        fatBarrel.method_1817(-2F, -2F, -4F, 4, 4, 3);
        thinBarrel = new Cuboid(24, 7);
        thinBarrel.method_1817(-1F, -1F, -9F, 2, 2, 5);
        stand = new Cuboid(46, 0);
        stand.method_1817(-1F, 3F, -1F, 2, 10, 2);
        leg1 = new Cuboid(38, 0);
        leg1.method_1817(-1F, 0.0F, -1F, 2, 14, 2);
        leg1.setRotationPoint(0.0F, 13F, 0.0F);
        leg1.pitch = 0.6632251F;
        leg1.yaw = 0.7853981F;
        leg2 = new Cuboid(38, 0);
        leg2.method_1817(-1F, 0.0F, -1F, 2, 14, 2);
        leg2.setRotationPoint(0.0F, 13F, 0.0F);
        leg2.pitch = 0.6632251F;
        leg2.yaw = 2.356194F;
        leg3 = new Cuboid(38, 0);
        leg3.method_1817(-1F, 0.0F, -1F, 2, 14, 2);
        leg3.setRotationPoint(0.0F, 13F, 0.0F);
        leg3.pitch = 0.6632251F;
        leg3.yaw = 3.926991F;
        leg4 = new Cuboid(38, 0);
        leg4.method_1817(-1F, 0.0F, -1F, 2, 14, 2);
        leg4.setRotationPoint(0.0F, 13F, 0.0F);
        leg4.pitch = 0.6632251F;
        leg4.yaw = 5.497787F;
        foot1 = new Cuboid(0, 12);
        foot1.method_1817(-8F, 23F, -8F, 5, 1, 5);
        foot2 = new Cuboid(0, 12);
        foot2.method_1817(-8F, 23F, -8F, 5, 1, 5);
        foot2.yaw = 1.570796F;
        foot3 = new Cuboid(0, 12);
        foot3.method_1817(-8F, 23F, -8F, 5, 1, 5);
        foot3.yaw = 3.141593F;
        foot4 = new Cuboid(0, 12);
        foot4.method_1817(-8F, 23F, -8F, 5, 1, 5);
        foot4.yaw = 4.712389F;
        headModels = (new Cuboid[] {
            head, fatBarrel, thinBarrel
        });
        bodyModels = (new Cuboid[] {
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
            headModels[i].method_1815(f5);
        }

        for(int j = 0; j < bodyModels.length; j++)
        {
            bodyModels[j].method_1815(f5);
        }

    }

    Cuboid head;
    Cuboid fatBarrel;
    Cuboid thinBarrel;
    Cuboid stand;
    Cuboid leg1;
    Cuboid leg2;
    Cuboid leg3;
    Cuboid leg4;
    Cuboid foot1;
    Cuboid foot2;
    Cuboid foot3;
    Cuboid foot4;
    Cuboid headModels[];
    Cuboid bodyModels[];
}
