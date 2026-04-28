package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.utils.SdkJetPackItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;


public class ItemJetPack extends SdkJetPackItem implements ArmorTextureProvider {

    public ItemJetPack(Identifier i)
    {
        super(i, ItemListener.itemOil, "sdk:jetpack", "flame", "smoke", Item.BUCKET);
//        setMaxDamage(127);
    }

    @Override
    public Identifier getTexture(ArmorItem armour) {
        return ItemListener.MOD_ID.id("jetPack");
    }
}
