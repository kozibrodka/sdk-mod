package net.kozibrodka.sdk.grinder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;

public class SdkContainerGrinder extends ScreenHandler
{

    public SdkContainerGrinder(Inventory iinventory, SdkTileEntityGrinder sdktileentitygrinder)
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

    @Override
    public void sendContentUpdates()
    {
        super.sendContentUpdates();
        for(int i = 0; i < listeners.size(); i++)
        {
            ScreenHandlerListener icrafting = (ScreenHandlerListener)listeners.get(i);
            if(cookTime != grinder.cookTime)
            {
                icrafting.onPropertyUpdate(this, 0, grinder.cookTime);
            }
            if(burnTime != grinder.burnTime)
            {
                icrafting.onPropertyUpdate(this, 1, grinder.burnTime);
            }
            if(currentItemBurnTime != grinder.currentItemBurnTime)
            {
                icrafting.onPropertyUpdate(this, 2, grinder.currentItemBurnTime);
            }
        }

        cookTime = grinder.cookTime;
        burnTime = grinder.burnTime;
        currentItemBurnTime = grinder.currentItemBurnTime;
    }

    @Override
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

    @Override
    public boolean canUse(PlayerEntity entityplayer)
    {
        return grinder.canPlayerUse(entityplayer);
    }

    private final SdkTileEntityGrinder grinder;
    private int cookTime;
    private int burnTime;
    private int currentItemBurnTime;

    @Environment(EnvType.SERVER)
    @Override
    public void addListener(ScreenHandlerListener listener) {
        super.addListener(listener);
        listener.onPropertyUpdate(this, 0, grinder.cookTime);
        listener.onPropertyUpdate(this, 1, grinder.burnTime);
        listener.onPropertyUpdate(this, 2, grinder.currentItemBurnTime);
    }

//    @Override
//    public void sendContentUpdates() {
//        super.sendContentUpdates();
//
//        for (Object listener : listeners) {
//            ScreenHandlerListener shl = (ScreenHandlerListener) listener;
//            if (this.cookTime != grinder.cookTime) shl.onPropertyUpdate(this, 0, grinder.cookTime);
//            if (this.burnTime != grinder.burnTime) shl.onPropertyUpdate(this, 1, grinder.burnTime);
//            if (this.currentItemBurnTime != grinder.currentItemBurnTime) shl.onPropertyUpdate(this, 2, grinder.currentItemBurnTime);
//        }
//
//        this.cookTime = grinder.cookTime;
//        this.burnTime = grinder.burnTime;
//        this.currentItemBurnTime = grinder.currentItemBurnTime;
//    }
//
//    @Environment(EnvType.CLIENT)
//    @Override
//    public void setProperty(int id, int value) {
//        if (id == 0) grinder.cookTime = value;
//        if (id == 1) grinder.burnTime = value;
//        if (id == 2) grinder.currentItemBurnTime = value;
//    }
}
