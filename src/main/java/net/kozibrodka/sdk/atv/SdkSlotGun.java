package net.kozibrodka.sdk.atv;


import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

class SdkSlotGun extends Slot
{

    SdkSlotGun(InventoryBase iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    public int getMaxStackCount()
    {
        return 1;
    }

    public boolean canInsert(ItemInstance itemstack)
    {
        return itemstack.getType() instanceof SdkItemGun;
    }
}
