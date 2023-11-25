package net.kozibrodka.sdk.item;


import net.kozibrodka.sdk_api.events.ingame.mod_SdkUtility;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;


public class SdkItemTelescope extends TemplateItemBase
{

    public SdkItemTelescope(Identifier i)
    {
        super(i);
    }

    public ItemInstance use(ItemInstance itemstack, Level world, PlayerBase entityplayer)
    {
        mod_SdkUtility.useZoom();
        return itemstack;
    }
}
