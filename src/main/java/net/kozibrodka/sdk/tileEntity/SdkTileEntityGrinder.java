package net.kozibrodka.sdk.tileEntity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class SdkTileEntityGrinder extends BlockEntity
    implements Inventory
{

    public SdkTileEntityGrinder()
    {
        isActive = false;
        random = new Random();
        itemStacks = new ItemStack[3];
        burnTime = 0;
        currentItemBurnTime = 0;
        cookTime = 0;
    }

    public int size()
    {
        return itemStacks.length;
    }

    public ItemStack getStack(int i)
    {
        return itemStacks[i];
    }

    public ItemStack removeStack(int i, int j)
    {
        if(itemStacks[i] != null)
        {
            if(itemStacks[i].count <= j)
            {
                ItemStack itemstack = itemStacks[i];
                itemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = itemStacks[i].split(j);
            if(itemStacks[i].count == 0)
            {
                itemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setStack(int i, ItemStack itemstack)
    {
        itemStacks[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxCountPerStack())
        {
            itemstack.count = getMaxCountPerStack();
        }
    }

    public String getName()
    {
        return "Grinder";
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        NbtList nbttaglist = nbttagcompound.getList("Items");
        itemStacks = new ItemStack[size()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < itemStacks.length)
            {
                itemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        burnTime = nbttagcompound.getShort("BurnTime");
        cookTime = nbttagcompound.getShort("CookTime");
        currentItemBurnTime = nbttagcompound.getShort("CurrentItemBurnTime");
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("BurnTime", (short)burnTime);
        nbttagcompound.putShort("CookTime", (short)cookTime);
        nbttagcompound.putShort("CurrentItemBurnTime", (short)currentItemBurnTime);
        NbtList nbttaglist = new NbtList();
        for(int i = 0; i < itemStacks.length; i++)
        {
            if(itemStacks[i] != null)
            {
                NbtCompound nbttagcompound1 = new NbtCompound();
                nbttagcompound1.putByte("Slot", (byte)i);
                itemStacks[i].writeNbt(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getMaxCountPerStack()
    {
        return 64;
    }

    public int getCookProgressScaled(int i)
    {
        return (cookTime * i) / 200;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        if(currentItemBurnTime == 0)
        {
            return 0;
        } else
        {
            return (burnTime * i) / currentItemBurnTime;
        }
    }

    public boolean isBurning()
    {
        return burnTime > 0;
    }

    public void tick()
    {
        isActive = isBurning() && canSmelt();
        boolean flag = false;
        if(isActive || burnTime == 1)
        {
            burnTime--;
        }
        if(!world.isRemote)
        {
            if(burnTime == 0 && canSmelt())
            {
                currentItemBurnTime = burnTime = getItemBurnTime(itemStacks[1]);
                if(burnTime > 0)
                {
                    flag = true;
                    if(itemStacks[1] != null)
                    {
                        if(itemStacks[1].getItem().hasCraftingReturnItem())
                        {
                            itemStacks[1] = new ItemStack(itemStacks[1].getItem().getCraftingReturnItem());
                        } else
                        {
                            itemStacks[1].count--;
                        }
                        if(itemStacks[1].count == 0)
                        {
                            itemStacks[1] = null;
                        }
                    }
                }
            }
            if(isBurning() && canSmelt())
            {
                cookTime++;
                if(cookTime == 200)
                {
                    cookTime = 0;
                    smeltItem();
                    flag = true;
                }
            } else
            {
                cookTime = 0;
            }
            if(isActive != isBurning() && canSmelt())
            {
                flag = true;
            }
        }
        if(flag)
        {
            markDirty();
        }
    }

    private boolean canSmelt()
    {
        if(itemStacks[0] == null)
        {
            return false;
        }
        ItemStack itemstack = null;
        if(itemStacks[0].itemId == Block.GRASS_BLOCK.id || itemStacks[0].itemId == Item.FLINT.id)
        {
            itemstack = new ItemStack(Item.GUNPOWDER);
        }
        if(itemstack == null)
        {
            return false;
        }
        if(itemStacks[2] == null)
        {
            return true;
        }
        if(!itemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }
        if(itemStacks[2].count < getMaxCountPerStack() && itemStacks[2].count < itemStacks[2].getMaxCount())
        {
            return true;
        } else
        {
            return itemStacks[2].count < itemstack.getMaxCount();
        }
    }

    public void smeltItem()
    {
        if(!canSmelt())
        {
            return;
        }
        ItemStack itemstack = null;
        if(itemStacks[0].itemId == Item.FLINT.id)
        {
            itemstack = new ItemStack(Item.GUNPOWDER);
        } else
        if(itemStacks[0].itemId == Block.GRAVEL.id && random.nextInt(4) == 0)
        {
            itemstack = new ItemStack(Item.GUNPOWDER);
        } else
        {
            itemStacks[0].count--;
            if(itemStacks[0].count <= 0)
            {
                itemStacks[0] = null;
            }
            return;
        }
        if(itemStacks[2] == null)
        {
            itemStacks[2] = itemstack.copy();
        } else
        if(itemStacks[2].itemId == itemstack.itemId)
        {
            itemStacks[2].count += itemstack.count;
        }
        if(itemStacks[0].getItem().hasCraftingReturnItem())
        {
            itemStacks[0] = new ItemStack(itemStacks[0].getItem().getCraftingReturnItem());
        } else
        {
            itemStacks[0].count--;
        }
        if(itemStacks[0].count <= 0)
        {
            itemStacks[0] = null;
        }
    }

    private int getItemBurnTime(ItemStack itemstack)
    {
        if(itemstack != null)
        {
            if(itemstack.getItem().id == Item.DIAMOND.id)
            {
                return 12800;
            }
            if(itemstack.getItem().id == Item.IRON_INGOT.id)
            {
                return 1600;
            }
        }
        return 0;
    }

    public boolean canPlayerUse(PlayerEntity entityplayer)
    {
        if(world.getBlockEntity(x, y, z) != this)
        {
            return false;
        } else
        {
            return entityplayer.getSquaredDistance((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
        }
    }

    private ItemStack itemStacks[];
    public int burnTime;
    public int currentItemBurnTime;
    public int cookTime;
    public boolean isActive;
    private Random random;
}
