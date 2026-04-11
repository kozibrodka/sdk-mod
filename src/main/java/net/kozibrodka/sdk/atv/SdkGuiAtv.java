package net.kozibrodka.sdk.atv;



import net.kozibrodka.sdk.mixin.ScreenBaseAccessor;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;


public class SdkGuiAtv extends HandledScreen
{

    public SdkGuiAtv(PlayerInventory inventoryplayer, SdkEntityAtv sdkentityatv)
    {
        super(new SdkContainerAtv(inventoryplayer, sdkentityatv));
        atv = sdkentityatv;
    }

    protected void drawForeground()
    {
        ((ScreenBaseAccessor)this).getTextManager().draw(atv.getName(), backgroundWidth / 2 - ((ScreenBaseAccessor)this).getTextManager().getWidth(atv.getName()) / 2, 6, 0x404040);
        ((ScreenBaseAccessor)this).getTextManager().draw("Inventory", 8, (backgroundHeight - 132) + 2, 0x404040);
    }

    protected void drawBackground(float f)
    {
        int i = SdkTools.minecraft.textureManager.getTextureId("/assets/sdk/stationapi/textures/item/guiAtv.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        SdkTools.minecraft.textureManager.bindTexture(i);
        int j = (width - backgroundWidth) / 2;
        int k = (height - backgroundHeight) / 2;
        drawTexture(j, k, 0, 0, backgroundWidth, backgroundHeight);
    }

    private SdkEntityAtv atv;
}
