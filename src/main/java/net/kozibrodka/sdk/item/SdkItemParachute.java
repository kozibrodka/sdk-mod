package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;

import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;


public class SdkItemParachute extends TemplateArmorItem implements ArmorTextureProvider {
    public SdkItemParachute(Identifier i, int j)
    {
        super(i, 1, j, 1);
        setMaxDamage(7);
    }

    @Override
    public Identifier getTexture(ArmorItem armour) {
        return ItemListener.MOD_ID.id("parachute");
    }
}
