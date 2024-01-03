package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.BlockListener;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemOil extends TemplateItem
{

    public SdkItemOil(Identifier i)
    {
        super(i);
        setDurability(63);
        maxStackSize = 1;
    }

    public boolean useOnTile(ItemInstance itemstack, PlayerBase entityplayer, Level world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            j--;
        }
        if(l == 1)
        {
            j++;
        }
        if(l == 2)
        {
            k--;
        }
        if(l == 3)
        {
            k++;
        }
        if(l == 4)
        {
            i--;
        }
        if(l == 5)
        {
            i++;
        }
        if(world.getTileId(i, j - 1, k) == BlockListener.blockOil.id && itemstack.getDamage() >= 4)
        {
            itemstack.applyDamage(-4, SdkTools.minecraft.player);
            world.setTile(i, j - 1, k, 0);
        }
        if(!world.isAir(i, j, k))
        {
            return false;
        }
        if(BlockListener.blockOil.canPlaceAt(world, i, j, k) && itemstack.getDamage() < itemstack.getType().getDurability())
        {
            itemstack.applyDamage(4, SdkTools.minecraft.player);
            world.setTile(i, j, k, BlockListener.blockOil.id);
            if(itemstack.count == 0)
            {
                entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = new ItemInstance(ItemBase.bucket);
                itemstack.count = 1;
            }
        }
        return true;
    }
}
