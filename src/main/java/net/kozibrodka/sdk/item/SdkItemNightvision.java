package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.item.armour.Armour;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;


public class SdkItemNightvision extends TemplateArmorItem implements ArmorTextureProvider {
    public SdkItemNightvision(Identifier i, int j)
    {
        super(i, 1, j, 0);
    }

    @Override
    public Identifier getTexture(Armour armour) {
        return ItemListener.MOD_ID.id("nightvision");
    }
}
