package net.kozibrodka.sdk.tileEntity;

import net.kozibrodka.sdk.mixin.ItemBaseAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;

public class SdkTileEntityPlaque extends BlockEntity
{

    public SdkTileEntityPlaque()
    {
        itemStack = null;
    }

    public boolean activated(World world, PlayerEntity entityplayer)
    {
        ItemStack itemstack = entityplayer.getHand();
        if(itemstack != null && itemstack.itemId < 256 && BlockRenderManager.isSideLit(Block.BLOCKS[itemstack.itemId].getRenderType()))
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
                entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                itemStack = itemstack;
            } else
            {
                itemstack.count--;
                if(itemstack.count == 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                }
                itemStack = new ItemStack(itemstack.getItem(), 1);
            }
            return true;
        }
        if(itemstack == null)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = itemStack;
            itemStack = null;
            return true;
        }
        if(itemStack.itemId == itemstack.itemId && itemstack.count < ((ItemBaseAccessor)itemstack.getItem()) .getMaxCount())
        {
            itemstack.count++;
            itemStack = null;
            return true;
        }
        if(itemstack.count == 1)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = itemStack;
            itemStack = itemstack;
            return true;
        } else
        {
            return false;
        }
    }

    public void removed(World world)
    {
        if(itemStack != null)
        {
            float f = 0.7F;
            double d = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            ItemEntity entityitem = new ItemEntity(world, (double)x + d, (double)y + d1, (double)z + d2, itemStack);
            entityitem.pickupDelay = 10;
            world.spawnEntity(entityitem);
            itemStack = null;
        }
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        NbtList nbttaglist = nbttagcompound.getList("Item");
        if(nbttaglist.size() > 0)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(0);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 == 0)
            {
                itemStack = new ItemStack(nbttagcompound1);
            }
        }
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        NbtList nbttaglist = new NbtList();
        if(itemStack != null)
        {
            NbtCompound nbttagcompound1 = new NbtCompound();
            nbttagcompound1.putByte("Slot", (byte)0);
            itemStack.writeNbt(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        nbttagcompound.put("Item", nbttaglist);
    }

    public ItemStack itemStack;
}
