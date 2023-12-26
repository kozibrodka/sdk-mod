package net.kozibrodka.sdk.grinder;

import net.kozibrodka.sdk.mixin.ScreenBaseAccessor;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class SdkGuiGrinder extends ContainerBase
{

    public SdkGuiGrinder(PlayerInventory inventoryplayer, SdkTileEntityGrinder sdktileentitygrinder)
    {
        super(new SdkContainerGrinder(inventoryplayer, sdktileentitygrinder));
        grinder = sdktileentitygrinder;
    }

    protected void renderForeground()
    {
        ((ScreenBaseAccessor)this).getTextManager().drawText("Grinder", containerWidth / 2 - ((ScreenBaseAccessor)this).getTextManager().getTextWidth("Grinder") / 2, 6, 0x404040);
        ((ScreenBaseAccessor)this).getTextManager().drawText("Inventory", 8, (containerHeight - 96) + 2, 0x404040);
    }

    protected void renderContainerBackground(float f)
    {
        int i = SdkTools.minecraft.textureManager.getTextureId("/assets/sdk/stationapi/textures/item/guiGrinder.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        SdkTools.minecraft.textureManager.bindTexture(i);
        int j = (width - containerWidth) / 2;
        int k = (height - containerHeight) / 2;
        blit(j, k, 0, 0, containerWidth, containerHeight);
        if(grinder.isBurning())
        {
            int l = grinder.getBurnTimeRemainingScaled(12);
            blit(j + 56, (k + 36 + 12) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = grinder.getCookProgressScaled(24);
        blit(j + 79, k + 34, 176, 14, i1 + 1, 16);
    }

    private SdkTileEntityGrinder grinder;
}
