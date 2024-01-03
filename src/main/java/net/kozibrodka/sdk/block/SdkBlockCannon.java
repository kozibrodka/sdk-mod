package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockCannon extends TemplateBlock
{

    public SdkBlockCannon(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    public int getTextureForSide(int i)
    {
        return TextureListener.cannon;
    }

    public void onBlockPlaced(Level world, int i, int j, int k)
    {
        onAdjacentBlockUpdate(world, i, j, k, 0);
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(world.isServerSide)
        {
            return true;
        }
        if(entityplayer.inventory.getHeldItem() != null && entityplayer.inventory.getHeldItem().itemId == BlockBase.TNT.id)
        {
            int l = world.getTileMeta(i, j, k);
            if(l < 15)
            {
                entityplayer.inventory.getHeldItem().count--;
                if(entityplayer.inventory.getHeldItem().count == 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                }
                world.setTileMeta(i, j, k, l + 1);
            }
            onAdjacentBlockUpdate(world, i, j, k, 0);
            return true;
        } else
        {
            return false;
        }
    }

    public void onAdjacentBlockUpdate(Level world, int i, int j, int k, int l)
    {
        if(!world.isServerSide && world.hasRedstonePower(i, j, k))
        {
            int i1 = world.getTileMeta(i, j, k);
            if(i1 > 0)
            {
                world.setTile(i, j, k, 0);
                Explosion explosion = new Explosion(world, null, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 4F);
                world.playSound(i, j, k, "random.explode", 4F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
                for(int j1 = 0; j1 < i1; j1++)
                {
                    explosion.kaboomPhase1();
                }

                world.setTile(i, j, k, id);
                world.setTileMeta(i, j, k, 0);
            }
        }
    }
}
