package net.kozibrodka.sdk.atv;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModelBase;

public class SdkModelAtv extends EntityModelBase
{

    public SdkModelAtv()
    {
        body = new Cuboid(0, 0);
        body.method_1817(0.0F, 0.0F, 0.0F, 16, 6, 10);
        body.setRotationPoint(-8F, -5F, -5F);
        front = new Cuboid(0, 16);
        front.method_1817(0.0F, 0.0F, 0.0F, 1, 1, 10);
        front.setRotationPoint(7F, -6F, -5F);
        wheels = new Cuboid[4];
        wheels[0] = new Cuboid(22, 16);
        wheels[0].method_1817(-2F, -2F, -1F, 4, 4, 2);
        wheels[0].setRotationPoint(5F, 1.0F, 5F);
        wheels[1] = new Cuboid(22, 16);
        wheels[1].method_1817(-2F, -2F, -1F, 4, 4, 2);
        wheels[1].setRotationPoint(5F, 1.0F, -5F);
        wheels[2] = new Cuboid(22, 16);
        wheels[2].method_1817(-2F, -2F, -1F, 4, 4, 2);
        wheels[2].setRotationPoint(-5F, 1.0F, 5F);
        wheels[3] = new Cuboid(22, 16);
        wheels[3].method_1817(-2F, -2F, -1F, 4, 4, 2);
        wheels[3].setRotationPoint(-5F, 1.0F, -5F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setAngles(f, f1, f2, f3, f4, f5);
        body.method_1815(f5);
        front.method_1815(f5);
        for(int i = 0; i < wheels.length; i++)
        {
            wheels[i].method_1815(f5);
        }

    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        wheels[0].yaw = f4 / 57.29578F;
        wheels[1].yaw = f4 / 57.29578F;
        wheels[2].yaw = -f4 / 57.29578F;
        wheels[3].yaw = -f4 / 57.29578F;
    }

    public Cuboid body;
    public Cuboid front;
    public Cuboid wheels[];
}
