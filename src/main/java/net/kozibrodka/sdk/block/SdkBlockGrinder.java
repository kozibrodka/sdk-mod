package net.kozibrodka.sdk.block;

import net.kozibrodka.sdk.events.TextureListener;
import net.kozibrodka.sdk.grinder.SdkContainerGrinder;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

public class SdkBlockGrinder extends TemplateBlockWithEntity
{

    public SdkBlockGrinder(Identifier iid)
    {
        super(iid, Material.STONE);
    }

    public int getTextureForSide(int i)
    {
        if(i == 1)
        {
            return TextureListener.grinder;
        } else
        {
            return BlockBase.STONE.id;
        }
    }

    public void randomDisplayTick(Level world, int i, int j, int k, Random random)
    {
        SdkTileEntityGrinder sdktileentitygrinder = (SdkTileEntityGrinder)world.getTileEntity(i, j, k);
        if(sdktileentitygrinder.isActive)
        {
            world.addParticle("smoke", (float)i + (random.nextFloat() * 10F + 3F) / 16F, (float)j + 1.0F, (float)k + (random.nextFloat() * 10F + 3F) / 16F, 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean canUse(Level world, int i, int j, int k, PlayerBase entityplayer)
    {
        if(world.isServerSide)
        {
            return true;
        } else
        {
            SdkTileEntityGrinder sdktileentitygrinder = (SdkTileEntityGrinder)world.getTileEntity(i, j, k);
//            TODO: GUI done
//            SdkTools.minecraft.displayGuiScreen(new SdkGuiGrinder(entityplayer.inventory, sdktileentitygrinder));
            GuiHelper.openGUI(entityplayer, Identifier.of("sdk:openGrinder"), (InventoryBase) sdktileentitygrinder, new SdkContainerGrinder(entityplayer.inventory, (SdkTileEntityGrinder) sdktileentitygrinder));

            return true;
        }
    }

    protected TileEntityBase createTileEntity()
    {
        return new SdkTileEntityGrinder();
    }
}
