package net.kozibrodka.sdk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public interface EntityBaseAccessor {

    @Accessor
    Random getRand();

    @Accessor
    int getField_1613();

    @Invoker
    void invokeSetRotation(float f, float g);

    @Accessor
    int getField_1648();

    @Accessor
    void setFallDistance(float f);


}
