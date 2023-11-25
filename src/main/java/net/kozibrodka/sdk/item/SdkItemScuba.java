package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.item.armour.Armour;
import net.modificationstation.stationapi.api.client.item.ArmourTextureProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.armour.TemplateArmour;

public class SdkItemScuba extends TemplateArmour implements ArmourTextureProvider {
    public SdkItemScuba(Identifier i, int j)
    {
        super(i, -1, j, 1);
    }

    @Override
    public Identifier getTexture(Armour armour) {
        return ItemListener.MOD_ID.id("scubaTank");
    }
}
