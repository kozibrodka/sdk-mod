
package net.kozibrodka.sdk.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

import net.kozibrodka.sdk.events.ItemListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.util.maths.MathHelper;


public class SdkTextureLightometerFX extends TextureBinder
{

    public SdkTextureLightometerFX(Minecraft minecraft)
    {
        super(ItemListener.itemLightometer.getTexturePosition(0));
        watchIconImageData = new int[256];
        dialImageData = new int[256];
        mc = minecraft;
        renderMode = 1;
        try
        {
            BufferedImage bufferedimage = ImageIO.read(Objects.requireNonNull((Minecraft.class).getResource("/assets/sdk/stationapi/textures/item/itemLightometer.png")));
            bufferedimage.getRGB(0, 0, 16, 16, watchIconImageData, 0, 16);
            bufferedimage = ImageIO.read(Objects.requireNonNull((Minecraft.class).getResource("/assets/sdk/stationapi/textures/item/miscLightometer.png")));
            bufferedimage.getRGB(0, 0, 16, 16, dialImageData, 0, 16);
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void update()
    {
        double d = 0.0D;
        if(mc.level != null && mc.player != null)
        {
            int i = MathHelper.floor(mc.player.x);
            int j = MathHelper.floor(mc.player.y);
            int k = MathHelper.floor(mc.player.z);
            int l = mc.level.placeTile(i, j - 1, k);
            float f = getRotationValue(l);
            d = -f * 3.141593F * 2.0F;
        }
        double d1;
        for(d1 = d - field_4222_j; d1 < -3.1415926535897931D; d1 += 6.2831853071795862D) { }
        for(; d1 >= 3.1415926535897931D; d1 -= 6.2831853071795862D) { }
        if(d1 < -1D)
        {
            d1 = -1D;
        }
        if(d1 > 1.0D)
        {
            d1 = 1.0D;
        }
        field_4221_k += d1 * 0.10000000000000001D;
        field_4221_k *= 0.80000000000000004D;
        field_4222_j += field_4221_k;
        double d2 = Math.sin(field_4222_j);
        double d3 = Math.cos(field_4222_j);
        for(int i1 = 0; i1 < 256; i1++)
        {
            int j1 = watchIconImageData[i1] >> 24 & 0xff;
            int k1 = watchIconImageData[i1] >> 16 & 0xff;
            int l1 = watchIconImageData[i1] >> 8 & 0xff;
            int i2 = watchIconImageData[i1] >> 0 & 0xff;
            if(k1 == i2 && l1 == 0 && i2 > 0)
            {
                double d4 = -((double)(i1 % 16) / 15D - 0.5D);
                double d5 = (double)(i1 / 16) / 15D - 0.5D;
                int i3 = k1;
                int j3 = (int)((d4 * d3 + d5 * d2 + 0.5D) * 16D);
                int k3 = (int)(((d5 * d3 - d4 * d2) + 0.5D) * 16D);
                int l3 = (j3 & 0xf) + (k3 & 0xf) * 16;
                j1 = dialImageData[l3] >> 24 & 0xff;
                k1 = ((dialImageData[l3] >> 16 & 0xff) * i3) / 255;
                l1 = ((dialImageData[l3] >> 8 & 0xff) * i3) / 255;
                i2 = ((dialImageData[l3] >> 0 & 0xff) * i3) / 255;
            }
            if(render3d)
            {
                int j2 = (k1 * 30 + l1 * 59 + i2 * 11) / 100;
                int k2 = (k1 * 30 + l1 * 70) / 100;
                int l2 = (k1 * 30 + i2 * 70) / 100;
                k1 = j2;
                l1 = k2;
                i2 = l2;
            }
            grid[i1 * 4 + 0] = (byte)k1;
            grid[i1 * 4 + 1] = (byte)l1;
            grid[i1 * 4 + 2] = (byte)i2;
            grid[i1 * 4 + 3] = (byte)j1;
        }

    }

    private float getRotationValue(int i)
    {
        float f;
        if(i > 7)
        {
            f = 0.0F;
        } else
        {
            f = 0.5F;
        }
        float f1 = f;
        f = 1.0F - (float)((Math.cos((double)f * 3.1415926535897931D) + 1.0D) / 2D);
        f = f1 + (f - f1) / 3F;
        return f;
    }

    private Minecraft mc;
    private int watchIconImageData[];
    private int dialImageData[];
    private double field_4222_j;
    private double field_4221_k;
}
