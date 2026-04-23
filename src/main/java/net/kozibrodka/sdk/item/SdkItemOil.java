package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.events.BlockListener;
import net.kozibrodka.sdk_api.utils.SdkToolsRender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemOil extends TemplateItem
{

    public SdkItemOil(Identifier i)
    {
        super(i);
        setMaxDamage(63);
        maxCount = 1;
    }

    public boolean useOnBlock(ItemStack itemstack, PlayerEntity entityplayer, World world, int i, int j, int k, int l)
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
        if(world.getBlockId(i, j - 1, k) == BlockListener.blockOil.id && itemstack.getDamage() >= 4)
        {
            itemstack.damage(-4, SdkToolsRender.minecraft.player);
            world.setBlock(i, j - 1, k, 0);
        }
        if(!world.isAir(i, j, k))
        {
            return false;
        }
        if(BlockListener.blockOil.canPlaceAt(world, i, j, k) && itemstack.getDamage() < itemstack.getItem().getMaxDamage())
        {
            itemstack.damage(4, SdkToolsRender.minecraft.player);
            world.setBlock(i, j, k, BlockListener.blockOil.id);
            if(itemstack.count == 0)
            {
                entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = new ItemStack(Item.BUCKET);
                itemstack.count = 1;
            }
        }
        return true;
    }
}
