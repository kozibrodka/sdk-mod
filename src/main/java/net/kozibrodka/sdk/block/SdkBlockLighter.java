package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockLighter extends TemplateBlock
{

    public SdkBlockLighter(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    public void onPlaced(World world, int i, int j, int k)
    {
        neighborUpdate(world, i, j, k, 0);
    }

    public void neighborUpdate(World world, int i, int j, int k, int l)
    {
        if(world.isPowered(i, j, k))
        {
            if(world.getBlockId(i, j + 1, k) == 0)
            {
                world.setBlock(i, j + 1, k, Block.FIRE.id);
            }
        } else
        if(world.getBlockId(i, j + 1, k) == Block.FIRE.id)
        {
            world.setBlock(i, j + 1, k, 0);
        }
    }

    public int getTexture(int i)
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
