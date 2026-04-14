package net.kozibrodka.sdk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public interface EntityBaseAccessor {

    @Accessor
    Random getRandom();

    @Accessor
    int getHearts();

    @Invoker
    void invokeSetRotation(float f, float g);

    @Accessor
    int getMaxAir();

    @Accessor
    void setFallDistance(float f);


}
