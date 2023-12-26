package net.kozibrodka.sdk.tileEntity;

import net.kozibrodka.sdk.events.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;

public class SdkTileEntityRope extends TileEntityBase
{

    public SdkTileEntityRope()
    {
        delay = 5;
    }

    public void tick()
    {
        if(delay == 0)
        {
            if(level.getTileId(x, y - 1, z) == 0 || level.getTileId(x, y - 1, z) == BlockBase.SNOW.id)
            {
                level.setTile(x, y - 1, z, BlockListener.blockRope.id);
                level.setTileMeta(x, y - 1, z, level.getTileMeta(x, y, z));
                delay--;
            }
        } else
        if(delay > 0)
        {
            delay--;
        }
        super.tick();
    }

    public void readIdentifyingData(CompoundTag nbttagcompound)
    {
        super.readIdentifyingData(nbttagcompound);
        delay = nbttagcompound.getShort("Delay");
    }

    public void writeIdentifyingData(CompoundTag nbttagcompound)
    {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("Delay", (short)delay);
    }

    public int delay;
}
