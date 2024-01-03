package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockLighter extends TemplateBlock
{

    public SdkBlockLighter(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    public void onBlockPlaced(Level world, int i, int j, int k)
    {
        onAdjacentBlockUpdate(world, i, j, k, 0);
    }

    public void onAdjacentBlockUpdate(Level world, int i, int j, int k, int l)
    {
        if(world.hasRedstonePower(i, j, k))
        {
            if(world.getTileId(i, j + 1, k) == 0)
            {
                world.setTile(i, j + 1, k, BlockBase.FIRE.id);
            }
        } else
        if(world.getTileId(i, j + 1, k) == BlockBase.FIRE.id)
        {
            world.setTile(i, j + 1, k, 0);
        }
    }

    public int getTextureForSide(int i)
    {
        if(i == 1)
        {
            return TextureListener.lighter_top;
        } else
        {
            return TextureListener.lighter_side;
        }
    }
}
