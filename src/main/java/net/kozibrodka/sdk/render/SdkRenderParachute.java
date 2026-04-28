package net.kozibrodka.sdk.render;

import net.kozibrodka.sdk.model.SdkModelParachute;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SdkRenderParachute extends LivingEntityRenderer {

    public SdkRenderParachute(EntityModel entityModel) {
        super(entityModel, 0.0F);
    }
}