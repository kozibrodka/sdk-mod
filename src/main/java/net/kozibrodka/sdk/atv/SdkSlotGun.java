package net.kozibrodka.sdk.atv;


import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

class SdkSlotGun extends Slot
{

    SdkSlotGun(Inventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    public int getMaxItemCount()
    {
        return 1;
    }

    public boolean canInsert(ItemStack itemstack)
    {
        return itemstack.getItem() instanceof SdkItemGun;
    }
}
