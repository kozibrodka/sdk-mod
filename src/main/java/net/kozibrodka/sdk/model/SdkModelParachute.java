package net.kozibrodka.sdk.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class SdkModelParachute extends EntityModel
{

    public SdkModelParachute()
    {
        top = new ModelPart(0, 0);
        top.addCuboid(-8F, 8F, -8F, 16, 1, 16);
        side1 = new ModelPart(0, 0);
        side1.addCuboid(-8F, 8F, -8F, 16, 16, 1);
        side2 = new ModelPart(0, 0);
        side2.addCuboid(-8F, 8F, 7F, 16, 16, 1);
        side3 = new ModelPart(0, 0);
        side3.addCuboid(-8F, 8F, -8F, 1, 16, 16);
        side4 = new ModelPart(0, 0);
        side4.addCuboid(7F, 8F, -8F, 1, 16, 16);
        models = (new ModelPart[] {
                top, side1, side2, side3, side4
        });
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        for(int i = 0; i < models.length; i++)
        {
            models[i].yaw = f3 / 57.29578F;
            models[i].pitch = f4 / 57.29578F;
        }

    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        for(int i = 0; i < models.length; i++)
        {
            models[i].render(f5);
        }

    }

    ModelPart top;
    ModelPart side1;
    ModelPart side2;
    ModelPart side3;
    ModelPart side4;
    ModelPart[] models;
}
