package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkNightVisionItem;
import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;


public class ItemNightvision extends SdkNightVisionItem implements ArmorTextureProvider {
    public ItemNightvision(Identifier i)
    {
        super(i, 127, 0, "sdk:nvgon", "sdk:nvgoff", 1.0F,"/assets/sdk/stationapi/textures/gui/miscNightvision.png", true);
    }

    @Override
    public Identifier getTexture(ArmorItem armour) {
        return ItemListener.MOD_ID.id("nightvision");
    }
}
