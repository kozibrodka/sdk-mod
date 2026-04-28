package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkScubaItem;
import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;


public class ItemScuba extends SdkScubaItem implements ArmorTextureProvider {

    public ItemScuba(Identifier i)
    {
        super(i, 127,0);
    }

    @Override
    public Identifier getTexture(ArmorItem armour) {
        return ItemListener.MOD_ID.id("scubaTank");
    }
}
