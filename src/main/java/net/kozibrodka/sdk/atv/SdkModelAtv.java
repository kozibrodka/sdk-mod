package net.kozibrodka.sdk.atv;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class SdkModelAtv extends EntityModel
{

    public SdkModelAtv()
    {
        body = new ModelPart(0, 0);
        body.addCuboid(0.0F, 0.0F, 0.0F, 16, 6, 10);
        body.setPivot(-8F, -5F, -5F);
        front = new ModelPart(0, 16);
        front.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 10);
        front.setPivot(7F, -6F, -5F);
        wheels = new ModelPart[4];
        wheels[0] = new ModelPart(22, 16);
        wheels[0].addCuboid(-2F, -2F, -1F, 4, 4, 2);
        wheels[0].setPivot(5F, 1.0F, 5F);
        wheels[1] = new ModelPart(22, 16);
        wheels[1].addCuboid(-2F, -2F, -1F, 4, 4, 2);
        wheels[1].setPivot(5F, 1.0F, -5F);
        wheels[2] = new ModelPart(22, 16);
        wheels[2].addCuboid(-2F, -2F, -1F, 4, 4, 2);
        wheels[2].setPivot(-5F, 1.0F, 5F);
        wheels[3] = new ModelPart(22, 16);
        wheels[3].addCuboid(-2F, -2F, -1F, 4, 4, 2);
        wheels[3].setPivot(-5F, 1.0F, -5F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setAngles(f, f1, f2, f3, f4, f5);
        body.render(f5);
        front.render(f5);
        for(int i = 0; i < wheels.length; i++)
        {
            wheels[i].render(f5);
        }

    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        wheels[0].yaw = f4 / 57.29578F;
        wheels[1].yaw = f4 / 57.29578F;
        wheels[2].yaw = -f4 / 57.29578F;
        wheels[3].yaw = -f4 / 57.29578F;
    }

    public ModelPart body;
    public ModelPart front;
    public ModelPart wheels[];
}
