package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk_api.ingame.mod_SdkUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;


public class SdkItemTelescope extends TemplateItem
{

    public SdkItemTelescope(Identifier i)
    {
        super(i);
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
//        mod_SdkUtility.useZoom();
        return itemstack;
    }
}
