package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.entity.SdkEntityNukePrimed;
import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.block.BlockBase;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockNuke extends TemplateBlock
{

    public SdkBlockNuke(Identifier iid)
    {
        super(iid, Material.TNT);
        setTicksRandomly(true);
    }

    public int getTextureForSide(int i)
    {
        if(i == 0)
        {
            return BlockBase.TNT.getTextureForSide(0);
        }
        if(i == 1)
        {
            return BlockBase.TNT.getTextureForSide(1);
        } else
        {
            return TextureListener.nuke;
        }
    }

    public int getTickrate()
    {
        return 40;
    }

    public void onBlockPlaced(Level world, int i, int j, int k)
    {
        world.method_216(i, j, k, id, getTickrate());
    }

    public void onScheduledTick(Level world, int i, int j, int k, Random random)
    {
        if(!checkExplode(world, i, j, k))
        {
            world.method_216(i, j, k, id, getTickrate());
        }
    }

    public void onAdjacentBlockUpdate(Level world, int i, int j, int k, int l)
    {
        if(l > 0 && BlockBase.BY_ID[l].getEmitsRedstonePower() && world.hasRedstonePower(i, j, k))
        {
            explode(world, i, j, k);
        }
    }

    public boolean checkExplode(Level world, int i, int j, int k)
    {
        for(int l = i - 1; l <= i + 1; l++)
        {
            for(int i1 = j - 1; i1 <= j + 1; i1++)
            {
                for(int j1 = k - 1; j1 <= k + 1; j1++)
                {
                    if(Math.abs(i - l) + Math.abs(k - j1) + Math.abs(j - i1) != 0 && world.getTileId(l, i1, j1) == BlockBase.FIRE.id)
                    {
                        explode(world, i, j, k);
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public void explode(Level world, int i, int j, int k)
    {
        onBlockDestroyedByPlayer(world, i, j, k, 0);
        world.setTile(i, j, k, 0);
    }

    public int getDropCount(Random random)
    {
        return 0;
    }

    public void onDestroyedByExplosion(Level world, int i, int j, int k)
    {
        SdkEntityNukePrimed sdkentitynukeprimed = new SdkEntityNukePrimed(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F);
        sdkentitynukeprimed.fuse = world.rand.nextInt(sdkentitynukeprimed.fuse / 4) + sdkentitynukeprimed.fuse / 8;
        world.spawnEntity(sdkentitynukeprimed);
    }

    public void onBlockDestroyedByPlayer(Level world, int i, int j, int k, int l)
    {
        if(world.isServerSide)
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
