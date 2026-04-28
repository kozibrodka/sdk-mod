package net.kozibrodka.sdk.tileEntity;

import net.kozibrodka.sdk.events.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

public class SdkTileEntityRope extends BlockEntity
{

    public SdkTileEntityRope()
    {
        delay = 5;
    }

    @Override
    public void tick()
    {
        if(delay == 0)
        {
            if(world.getBlockId(x, y - 1, z) == 0 || world.getBlockId(x, y - 1, z) == Block.SNOW.id)
            {
                world.setBlock(x, y - 1, z, BlockListener.blockRope.id);
                int metaD = world.getBlockMeta(x, y, z);
                if(metaD > 5){
                    metaD -= 4;
                }
                world.setBlockMeta(x, y - 1, z, metaD);
                delay--;
            }
        } else
        if(delay > 0)
        {
            delay--;
        }
        super.tick();
    }

    @Override
    public void readNbt(NbtCompound nbttagcompound)
    {
        super.readNbt(nbttagcompound);
        delay = nbttagcompound.getShort("Delay");
    }

    @Override
    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("Delay", (short)delay);
    }

    public int delay;
}
