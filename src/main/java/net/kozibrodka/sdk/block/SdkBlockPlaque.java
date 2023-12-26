package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.tileEntity.SdkTileEntityPlaque;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

public class SdkBlockPlaque extends TemplateBlockWithEntity
{

    public SdkBlockPlaque(Identifier iid)
    {
        super(iid, Material.WOOD);
//        renderType = k;
    }

    protected TileEntityBase createTileEntity()
    {
        return new SdkTileEntityPlaque();
    }

    public Box getCollisionShape(Level world, int i, int j, int k)
    {
        int l = world.getTileMeta(i, j, k);
        float f = 0.125F;
        if(l == 2)
        {
            setBoundingBox(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
        if(l == 3)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        if(l == 4)
        {
            setBoundingBox(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if(l == 5)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
        return super.getCollisionShape(world, i, j, k);
    }

    public Box getOutlineShape(Level world, int i, int j, int k)
    {
        int l = world.getTileMeta(i, j, k);
        float f = 0.125F;
        if(l == 2)
        {
            setBoundingBox(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
        if(l == 3)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        if(l == 4)
        {
            setBoundingBox(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if(l == 5)
        {
            setBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
        return super.getOutlineShape(world, i, j, k);
    }

    public boolean isFullOpaque()
    {
        return false;
    }

    public boolean isFullCube()
    {
        return false;
    }

    public int getRenderType()
    {
        return renderType;
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(world.isServerSide)
        {
            return true;
        } else
        {
            SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)world.getTileEntity(i, j, k);
            return sdktileentityplaque.activated(world, entityplayer);
        }
    }

    public void onBlockRemoved(Level world, int i, int j, int k)
    {
        if(world.isServerSide)
        {
            return;
        } else
        {
            SdkTileEntityPlaque sdktileentityplaque = (SdkTileEntityPlaque)world.getTileEntity(i, j, k);
            sdktileentityplaque.removed(world);
            return;
        }
    }

    public boolean canPlaceAt(Level world, int i, int j, int k)
    {
        if(world.isFullOpaque(i - 1, j, k))
        {
            return true;
        }
        if(world.isFullOpaque(i + 1, j, k))
        {
            return true;
        }
        if(world.isFullOpaque(i, j, k - 1))
        {
            return true;
        } else
        {
            return world.isFullOpaque(i, j, k + 1);
        }
    }

    public void onBlockPlaced(Level world, int i, int j, int k, int l)
    {
        int i1 = world.getTileMeta(i, j, k);
        if((i1 == 0 || l == 2) && world.isFullOpaque(i, j, k + 1))
        {
            i1 = 2;
        }
        if((i1 == 0 || l == 3) && world.isFullOpaque(i, j, k - 1))
        {
            i1 = 3;
        }
        if((i1 == 0 || l == 4) && world.isFullOpaque(i + 1, j, k))
        {
            i1 = 4;
        }
        if((i1 == 0 || l == 5) && world.isFullOpaque(i - 1, j, k))
        {
            i1 = 5;
        }
        world.setTileMeta(i, j, k, i1);
    }

    public void onAdjacentBlockUpdate(Level world, int i, int j, int k, int l)
    {
        int i1 = world.getTileMeta(i, j, k);
        boolean flag = false;
        if(i1 == 2 && world.isFullOpaque(i, j, k + 1))
        {
            flag = true;
        }
        if(i1 == 3 && world.isFullOpaque(i, j, k - 1))
        {
            flag = true;
        }
        if(i1 == 4 && world.isFullOpaque(i + 1, j, k))
        {
            flag = true;
        }
        if(i1 == 5 && world.isFullOpaque(i - 1, j, k))
        {
            flag = true;
        }
        if(!flag)
        {
            drop(world, i, j, k, i1);
            world.setTile(i, j, k, 0);
        }
        super.onAdjacentBlockUpdate(world, i, j, k, l);
    }

    public int getDropCount(Random random)
    {
        return 1;
    }

    private int renderType;
}
