package net.kozibrodka.sdk.tileEntity;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.util.Random;

public class SdkTileEntityGrinder extends TileEntityBase
    implements InventoryBase
{

    public SdkTileEntityGrinder()
    {
        isActive = false;
        random = new Random();
        itemStacks = new ItemInstance[3];
        burnTime = 0;
        currentItemBurnTime = 0;
        cookTime = 0;
    }

    public int getInventorySize()
    {
        return itemStacks.length;
    }

    public ItemInstance getInventoryItem(int i)
    {
        return itemStacks[i];
    }

    public ItemInstance takeInventoryItem(int i, int j)
    {
        if(itemStacks[i] != null)
        {
            if(itemStacks[i].count <= j)
            {
                ItemInstance itemstack = itemStacks[i];
                itemStacks[i] = null;
                return itemstack;
            }
            ItemInstance itemstack1 = itemStacks[i].split(j);
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

    public void setInventoryItem(int i, ItemInstance itemstack)
    {
        itemStacks[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxItemCount())
        {
            itemstack.count = getMaxItemCount();
        }
    }

    public String getContainerName()
    {
        return "Grinder";
    }

    public void readIdentifyingData(CompoundTag nbttagcompound)
    {
        super.readIdentifyingData(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Items");
        itemStacks = new ItemInstance[getInventorySize()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.get(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < itemStacks.length)
            {
                itemStacks[byte0] = new ItemInstance(nbttagcompound1);
            }
        }

        burnTime = nbttagcompound.getShort("BurnTime");
        cookTime = nbttagcompound.getShort("CookTime");
        currentItemBurnTime = nbttagcompound.getShort("CurrentItemBurnTime");
    }

    public void writeIdentifyingData(CompoundTag nbttagcompound)
    {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("BurnTime", (short)burnTime);
        nbttagcompound.put("CookTime", (short)cookTime);
        nbttagcompound.put("CurrentItemBurnTime", (short)currentItemBurnTime);
        ListTag nbttaglist = new ListTag();
        for(int i = 0; i < itemStacks.length; i++)
        {
            if(itemStacks[i] != null)
            {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte)i);
                itemStacks[i].toTag(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getMaxItemCount()
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
        if(!level.isServerSide)
        {
            if(burnTime == 0 && canSmelt())
            {
                currentItemBurnTime = burnTime = getItemBurnTime(itemStacks[1]);
                if(burnTime > 0)
                {
                    flag = true;
                    if(itemStacks[1] != null)
                    {
                        if(itemStacks[1].getType().hasContainerItemType())
                        {
                            itemStacks[1] = new ItemInstance(itemStacks[1].getType().getContainerItemType());
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
        ItemInstance itemstack = null;
        if(itemStacks[0].itemId == BlockBase.GRASS.id || itemStacks[0].itemId == ItemBase.flint.id)
        {
            itemstack = new ItemInstance(ItemBase.gunpowder);
        }
        if(itemstack == null)
        {
            return false;
        }
        if(itemStacks[2] == null)
        {
            return true;
        }
        if(!itemStacks[2].isDamageAndIDIdentical(itemstack))
        {
            return false;
        }
        if(itemStacks[2].count < getMaxItemCount() && itemStacks[2].count < itemStacks[2].getMaxStackSize())
        {
            return true;
        } else
        {
            return itemStacks[2].count < itemstack.getMaxStackSize();
        }
    }

    public void smeltItem()
    {
        if(!canSmelt())
        {
            return;
        }
        ItemInstance itemstack = null;
        if(itemStacks[0].itemId == ItemBase.flint.id)
        {
            itemstack = new ItemInstance(ItemBase.gunpowder);
        } else
        if(itemStacks[0].itemId == BlockBase.GRAVEL.id && random.nextInt(4) == 0)
        {
            itemstack = new ItemInstance(ItemBase.gunpowder);
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
        if(itemStacks[0].getType().hasContainerItemType())
        {
            itemStacks[0] = new ItemInstance(itemStacks[0].getType().getContainerItemType());
        } else
        {
            itemStacks[0].count--;
        }
        if(itemStacks[0].count <= 0)
        {
            itemStacks[0] = null;
        }
    }

    private int getItemBurnTime(ItemInstance itemstack)
    {
        if(itemstack != null)
        {
            if(itemstack.getType().id == ItemBase.diamond.id)
            {
                return 12800;
            }
            if(itemstack.getType().id == ItemBase.ironIngot.id)
            {
                return 1600;
            }
        }
        return 0;
    }

    public boolean canPlayerUse(PlayerBase entityplayer)
    {
        if(level.getTileEntity(x, y, z) != this)
        {
            return false;
        } else
        {
            return entityplayer.squaredDistanceTo((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
        }
    }

    private ItemInstance itemStacks[];
    public int burnTime;
    public int currentItemBurnTime;
    public int cookTime;
    public boolean isActive;
    private Random random;
}
