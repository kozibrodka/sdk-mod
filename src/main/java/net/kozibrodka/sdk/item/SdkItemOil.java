package net.kozibrodka.sdk.item;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class SdkItemOil extends TemplateItemBase
{

    public SdkItemOil(Identifier i)
    {
        super(i);
        setDurability(63);
        maxStackSize = 1;
    }

//    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
//    {
//        if(l == 0)
//        {
//            j--;
//        }
//        if(l == 1)
//        {
//            j++;
//        }
//        if(l == 2)
//        {
//            k--;
//        }
//        if(l == 3)
//        {
//            k++;
//        }
//        if(l == 4)
//        {
//            i--;
//        }
//        if(l == 5)
//        {
//            i++;
//        }
//        if(world.getBlockId(i, j - 1, k) == mod_SdkGuns.blockOil.blockID && itemstack.getItemDamage() >= 4)
//        {
//            itemstack.damageItem(-4, SdkTools.minecraft.thePlayer);
//            world.setBlockWithNotify(i, j - 1, k, 0);
//        }
//        if(!world.isAirBlock(i, j, k))
//        {
//            return false;
//        }
//        if(mod_SdkGuns.blockOil.canPlaceBlockAt(world, i, j, k) && itemstack.getItemDamage() < itemstack.getItem().getMaxDamage())
//        {
//            itemstack.damageItem(4, SdkTools.minecraft.thePlayer);
//            world.setBlockWithNotify(i, j, k, mod_SdkGuns.blockOil.blockID);
//            if(itemstack.stackSize == 0)
//            {
//                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = new ItemStack(Item.bucketEmpty);
//                itemstack.stackSize = 1;
//            }
//        }
//        return true;
//    }
}
