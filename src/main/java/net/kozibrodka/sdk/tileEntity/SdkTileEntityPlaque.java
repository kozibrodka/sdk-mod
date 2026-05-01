package net.kozibrodka.sdk.tileEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.network.AskPacket;
import net.kozibrodka.sdk_api.network.CoordSoundPacket;
import net.kozibrodka.sdk_api.network.SdkPacketHelper;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;

import java.util.List;

public class SdkTileEntityPlaque extends BlockEntity
{

    public SdkTileEntityPlaque()
    {
        itemStack = null;
    }

    public boolean hasAsked;

    @Override
    public void tick() {
        if(world.isRemote && !hasAsked){
            hasAsked = true;
            PacketHelper.send(new AskPacket(0, 0, x, y, z));
        }
    }

    @Environment(EnvType.SERVER)
    public void sendItemAll(int id, int meta, int x, int y, int z) {
        List list2 = world.players;
        if (!list2.isEmpty()) {
            for (Object o : list2) {
                ServerPlayerEntity player1 = (ServerPlayerEntity) o;
                PacketHelper.sendTo(player1, new AskPacket(id, meta, x, y, z));
            }
        }
    }

    public void updateRender(){
        if(SdkEnvTool.isEnvServ()){
            if(itemStack == null){
                sendItemAll(0, 0, x,y,z);
            }else{
                sendItemAll(itemStack.itemId, itemStack.getDamage(), x,y,z);
            }
        }
    }

    public boolean activated(World world, PlayerEntity entityplayer)
    {
        ItemStack itemstack = entityplayer.getHand();
//        if(itemstack != null && itemstack.itemId < 256 && BlockRenderManager.isSideLit(Block.BLOCKS[itemstack.itemId].getRenderType()))
//        {
//            return false;
//        }
        if(itemstack != null && itemstack.getItem() instanceof BlockItem)
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
                updateRender();
            } else
            {
                itemstack.count--;
                if(itemstack.count == 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                }
                itemStack = new ItemStack(itemstack.getItem(), 1);
                updateRender();
            }
            return true;
        }
        if(itemstack == null)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = itemStack;
            itemStack = null;
            updateRender();
            return true;
        }
        if(itemStack.itemId == itemstack.itemId && itemstack.count < itemstack.getItem().getMaxCount())
        {
            itemstack.count++;
            itemStack = null;
            updateRender();
            return true;
        }
        if(itemstack.count == 1)
        {
            entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = itemStack;
            itemStack = itemstack;
            updateRender();
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

    @Override
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

    @Override
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
