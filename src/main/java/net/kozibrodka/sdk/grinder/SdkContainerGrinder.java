package net.kozibrodka.sdk.grinder;

import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.ContainerListener;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;

public class SdkContainerGrinder extends ContainerBase
{

    public SdkContainerGrinder(InventoryBase iinventory, SdkTileEntityGrinder sdktileentitygrinder)
    {
        cookTime = 0;
        burnTime = 0;
        currentItemBurnTime = 0;
        grinder = sdktileentitygrinder;
        addSlot(new Slot(sdktileentitygrinder, 0, 56, 17));
        addSlot(new Slot(sdktileentitygrinder, 1, 56, 53));
        addSlot(new Slot(sdktileentitygrinder, 2, 116, 35));
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                addSlot(new Slot(iinventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            addSlot(new Slot(iinventory, j, 8 + j * 18, 142));
        }

    }

    public void tick()
    {
        super.tick();
        for(int i = 0; i < listeners.size(); i++)
        {
            ContainerListener icrafting = (ContainerListener)listeners.get(i);
            if(cookTime != grinder.cookTime)
            {
                icrafting.updateProperty(this, 0, grinder.cookTime);
            }
            if(burnTime != grinder.burnTime)
            {
                icrafting.updateProperty(this, 1, grinder.burnTime);
            }
            if(currentItemBurnTime != grinder.currentItemBurnTime)
            {
                icrafting.updateProperty(this, 2, grinder.currentItemBurnTime);
            }
        }

        cookTime = grinder.cookTime;
        burnTime = grinder.burnTime;
        currentItemBurnTime = grinder.currentItemBurnTime;
    }

    public void setProperty(int i, int j)
    {
        if(i == 0)
        {
            grinder.cookTime = j;
        }
        if(i == 1)
        {
            grinder.burnTime = j;
        }
        if(i == 2)
        {
            grinder.currentItemBurnTime = j;
        }
    }

    public boolean canUse(PlayerBase entityplayer)
    {
        return grinder.canPlayerUse(entityplayer);
    }

    private SdkTileEntityGrinder grinder;
    private int cookTime;
    private int burnTime;
    private int currentItemBurnTime;
}
