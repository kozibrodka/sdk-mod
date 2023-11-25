package net.kozibrodka.sdk.mixin;

import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ScreenBase.class)
public interface ScreenBaseAccessor {

    @Accessor
    TextRenderer getTextManager();
}
