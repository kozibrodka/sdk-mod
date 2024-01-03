package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.item.armour.Armour;
import net.modificationstation.stationapi.api.client.item.ArmourTextureProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.armour.TemplateArmour;

public class SdkItemParachute extends TemplateArmour implements ArmourTextureProvider {
    public SdkItemParachute(Identifier i, int j)
    {
        super(i, 1, j, 1);
        setDurability(7);
    }

    @Override
    public Identifier getTexture(Armour armour) {
        return ItemListener.MOD_ID.id("parachute");
    }
}
