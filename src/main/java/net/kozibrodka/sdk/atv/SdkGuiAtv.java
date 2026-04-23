package net.kozibrodka.sdk.atv;


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
        textRenderer.draw(atv.getName(), backgroundWidth / 2 - textRenderer.getWidth(atv.getName()) / 2, 6, 0x404040);
        textRenderer.draw("Inventory", 8, (backgroundHeight - 132) + 2, 0x404040);
    }

    protected void drawBackground(float f)
    {
        int i = minecraft.textureManager.getTextureId("/assets/sdk/stationapi/textures/item/guiAtv.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(i);
        int j = (width - backgroundWidth) / 2;
        int k = (height - backgroundHeight) / 2;
        drawTexture(j, k, 0, 0, backgroundWidth, backgroundHeight);
    }

    private SdkEntityAtv atv;
}
