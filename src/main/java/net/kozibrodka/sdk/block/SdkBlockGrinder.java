package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk.grinder.SdkContainerGrinder;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

public class SdkBlockGrinder extends TemplateBlockWithEntity
{

    public SdkBlockGrinder(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    @Override
    public int getTexture(int i)
    {
        if(i == 1)
        {
            return TextureListener.grinder;
        } else
        {
            return Block.STONE.id;
        }
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
//        SdkTileEntityGrinder sdktileentitygrinder = (SdkTileEntityGrinder)world.getBlockEntity(i, j, k);
        if(world.getBlockMeta(i,j,k) == 1)
        {
            world.addParticle("smoke", (float)i + (random.nextFloat() * 10F + 3F) / 16F, (float)j + 1.0F, (float)k + (random.nextFloat() * 10F + 3F) / 16F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        if(world.isRemote)
        {
            return true;
        } else
        {
            SdkTileEntityGrinder sdktileentitygrinder = (SdkTileEntityGrinder)world.getBlockEntity(i, j, k);
//            TODO: GUI done
//            SdkTools.minecraft.displayGuiScreen(new SdkGuiGrinder(entityplayer.inventory, sdktileentitygrinder));
            GuiHelper.openGUI(entityplayer, Identifier.of("sdk:openGrinder"), sdktileentitygrinder, new SdkContainerGrinder(entityplayer.inventory, sdktileentitygrinder));

            return true;
        }
    }

    @Override
    protected BlockEntity createBlockEntity()
    {
        return new SdkTileEntityGrinder();
    }
}
