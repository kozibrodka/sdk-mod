package net.kozibrodka.sdk.atv;


import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkSlotGun;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;

public class SdkContainerAtv extends ContainerBase
{

    public SdkContainerAtv(InventoryBase iinventory, SdkEntityAtv sdkentityatv)
    {
        atv = sdkentityatv;
        addSlot(new SdkSlotGun(sdkentityatv, 0, 56, 17));
        addSlot(new SdkSlotGun(sdkentityatv, 1, 104, 17));
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                addSlot(new Slot(iinventory, k + i * 9 + 9, 8 + k * 18, 48 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            addSlot(new Slot(iinventory, j, 8 + j * 18, 106));
        }

    }

    public boolean canUse(PlayerBase entityplayer)
    {
        return atv.canPlayerUse(entityplayer);
    }

    private SdkEntityAtv atv;
}
