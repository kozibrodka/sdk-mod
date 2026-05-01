package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk_api.network.SdkPacketHelper;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkBlockCannon extends TemplateBlock
{

    public SdkBlockCannon(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    @Override
    public int getTexture(int i)
    {
        return TextureListener.cannon;
    }

    @Override
    public void onPlaced(World world, int i, int j, int k)
    {
        neighborUpdate(world, i, j, k, 0);
    }

    @Override
    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        }
        if(entityplayer.inventory.getSelectedItem() != null && entityplayer.inventory.getSelectedItem().itemId == Block.TNT.id)
        {
            int l = world.getBlockMeta(i, j, k);
            if(l < 15)
            {
                entityplayer.inventory.getSelectedItem().count--;
                if(entityplayer.inventory.getSelectedItem().count == 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                }
                world.setBlockMeta(i, j, k, l + 1);
            }
            neighborUpdate(world, i, j, k, 0);
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void neighborUpdate(World world, int i, int j, int k, int l)
    {
        if(!world.isRemote && world.isPowered(i, j, k))
        {
            int i1 = world.getBlockMeta(i, j, k);
            if(i1 > 0)
            {
                world.setBlock(i, j, k, 0);
                Explosion explosion = new Explosion(world, null, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, 4F);
                world.playSound(i, j, k, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
                if(SdkEnvTool.isEnvServ()){ //TODO x+0.5D ?? ...
                    SdkPacketHelper.sendSoundAll(world,i,j,k,"random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
                }
                //TODO api coords packet all
                for(int j1 = 0; j1 < i1; j1++)
                {
                    explosion.explode();
                }

                world.setBlock(i, j, k, id);
                world.setBlockMeta(i, j, k, 0);
            }
        }
    }
}
