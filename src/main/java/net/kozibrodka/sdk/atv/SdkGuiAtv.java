package net.kozibrodka.sdk.atv;



import net.kozibrodka.sdk.mixin.ScreenBaseAccessor;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;


public class SdkGuiAtv extends ContainerBase
{

    public SdkGuiAtv(PlayerInventory inventoryplayer, SdkEntityAtv sdkentityatv)
    {
        super(new SdkContainerAtv(inventoryplayer, sdkentityatv));
        atv = sdkentityatv;
    }

    protected void renderForeground()
    {
        ((ScreenBaseAccessor)this).getTextManager().drawText(atv.getContainerName(), containerWidth / 2 - ((ScreenBaseAccessor)this).getTextManager().getTextWidth(atv.getContainerName()) / 2, 6, 0x404040);
        ((ScreenBaseAccessor)this).getTextManager().drawText("Inventory", 8, (containerHeight - 132) + 2, 0x404040);
    }

    protected void renderContainerBackground(float f)
    {
        int i = SdkTools.minecraft.textureManager.getTextureId("/assets/sdk/stationapi/textures/item/guiAtv.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        SdkTools.minecraft.textureManager.bindTexture(i);
        int j = (width - containerWidth) / 2;
        int k = (height - containerHeight) / 2;
        blit(j, k, 0, 0, containerWidth, containerHeight);
    }

    private SdkEntityAtv atv;
}
