package net.kozibrodka.sdk.tileEntity;

import net.kozibrodka.sdk.mixin.ItemBaseAccessor;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

public class SdkTileEntityPlaque extends TileEntityBase
{

    public SdkTileEntityPlaque()
    {
        itemStack = null;
    }

    public boolean activated(Level world, PlayerBase entityplayer)
    {
        ItemInstance itemstack = entityplayer.getHeldItem();
        if(itemstack != null && itemstack.itemId < 256 && BlockRenderer.method_42(BlockBase.BY_ID[itemstack.itemId].getRenderType()))
        {
            return false;
        }
        if(itemStack == null)
        {
            if(itemstack == null)
            {
                return false;
            }
            if(itemstack.count == 1)
            {
                entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                itemStack = itemstack;
            } else
            {
                itemstack.count--;
                if(itemstack.count == 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                }
                itemStack = new ItemInstance(itemstack.getType(), 1);
            }
            return true;
        }
        if(itemstack == null)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = itemStack;
            itemStack = null;
            return true;
        }
        if(itemStack.itemId == itemstack.itemId && itemstack.count < ((ItemBaseAccessor)itemstack.getType()) .getMaxStackSize())
        {
            itemstack.count++;
            itemStack = null;
            return true;
        }
        if(itemstack.count == 1)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = itemStack;
            itemStack = itemstack;
            return true;
        } else
        {
            return false;
        }
    }

    public void removed(Level world)
    {
        if(itemStack != null)
        {
            float f = 0.7F;
            double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            Item entityitem = new Item(world, (double)x + d, (double)y + d1, (double)z + d2, itemStack);
            entityitem.pickupDelay = 10;
            world.spawnEntity(entityitem);
            itemStack = null;
        }
    }

    public void readIdentifyingData(CompoundTag nbttagcompound)
    {
        super.readIdentifyingData(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Item");
        if(nbttaglist.size() > 0)
        {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.get(0);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 == 0)
            {
                itemStack = new ItemInstance(nbttagcompound1);
            }
        }
    }

    public void writeIdentifyingData(CompoundTag nbttagcompound)
    {
        super.writeIdentifyingData(nbttagcompound);
        ListTag nbttaglist = new ListTag();
        if(itemStack != null)
        {
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound1.put("Slot", (byte)0);
            itemStack.toTag(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        nbttagcompound.put("Item", nbttaglist);
    }

    public ItemInstance itemStack;
}
