package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.events.TextureListener;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockNuke extends TemplateBlock
{

    public SdkBlockNuke(Identifier iid)
    {
        super(iid, Material.TNT);
        setTickRandomly(true);
    }

    public int getTexture(int i)
    {
        if(i == 0)
        {
            return Block.TNT.getTexture(0);
        }
        if(i == 1)
        {
            return Block.TNT.getTexture(1);
        } else
        {
            return TextureListener.nuke;
        }
    }

    public int getTickRate()
    {
        return 40;
    }

    public void onPlaced(World world, int i, int j, int k)
    {
        world.scheduleBlockUpdate(i, j, k, id, getTickRate());
    }

    public void onTick(World world, int i, int j, int k, Random random)
    {
        if(!checkExplode(world, i, j, k))
        {
            world.scheduleBlockUpdate(i, j, k, id, getTickRate());
        }
    }

    public void neighborUpdate(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.BLOCKS[l].canEmitRedstonePower() && world.isPowered(i, j, k))
        {
            explode(world, i, j, k);
        }
    }

    public boolean checkExplode(World world, int i, int j, int k)
    {
        for(int l = i - 1; l <= i + 1; l++)
        {
            for(int i1 = j - 1; i1 <= j + 1; i1++)
            {
                for(int j1 = k - 1; j1 <= k + 1; j1++)
                {
                    if(Math.abs(i - l) + Math.abs(k - j1) + Math.abs(j - i1) != 0 && world.getBlockId(l, i1, j1) == Block.FIRE.id)
                    {
                        explode(world, i, j, k);
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public void explode(World world, int i, int j, int k)
    {
        onBlockDestroyedByPlayer(world, i, j, k, 0);
        world.setBlock(i, j, k, 0);
    }

    public int getDroppedItemCount(Random random)
    {
        return 0;
    }

    public void onDestroyedByExplosion(World world, int i, int j, int k)
    {
        SdkEntityNukePrimed sdkentitynukeprimed = new SdkEntityNukePrimed(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F);
        sdkentitynukeprimed.fuse = world.random.nextInt(sdkentitynukeprimed.fuse / 4) + sdkentitynukeprimed.fuse / 8;
        world.spawnEntity(sdkentitynukeprimed);
    }

    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
        if(world.isRemote)
        {
            return;
        } else
        {
            SdkEntityNukePrimed sdkentitynukeprimed = new SdkEntityNukePrimed(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F);
            world.spawnEntity(sdkentitynukeprimed);
            world.playSound(sdkentitynukeprimed, "random.fuse", 1.0F, 1.0F);
            return;
        }
    }
}
