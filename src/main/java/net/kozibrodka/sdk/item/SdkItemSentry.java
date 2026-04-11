
package net.kozibrodka.sdk.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.entitySentry.SdkEntitySentry;
import net.kozibrodka.sdk.events.EntityListener;
import net.kozibrodka.sdk.events.TextureListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class SdkItemSentry extends TemplateItem
{

    public SdkItemSentry(Identifier i)
    {
        super(i);
        setHasSubtypes(true);
    }

    public int getPlacementMetadata(int i)
    {
        return i;
    }

//    @Environment(EnvType.CLIENT)
//    public int getTexturePosition(int i) {
//        switch(i){
//            case 0: return TextureListener.grappling;
//            case 1: return TextureListener.grappling;
//            case 2: return TextureListener.empty;
//            default: return 11;
//        }
//    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        float f = 1.0F;
        float f1 = entityplayer.prevPitch + (entityplayer.pitch - entityplayer.prevPitch) * f;
        float f2 = entityplayer.prevYaw + (entityplayer.yaw - entityplayer.prevYaw) * f;
        double d = entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double)f;
        double d1 = (entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double)f + 1.6200000000000001D) - (double)entityplayer.standingEyeHeight;
        double d2 = entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double)f;
        Vec3d vec3d = Vec3d.createCached(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3d vec3d1 = vec3d.add((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, true);
        if(movingobjectposition == null)
        {
            return itemstack;
        }
        if(movingobjectposition.type == HitResultType.BLOCK && movingobjectposition.side == 1)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            int l = world.getBlockId(i, j + 1, k);
            int i1 = world.getBlockId(i, j + 2, k);
            if(l == 0 && i1 == 0)
            {
                if(!world.isRemote)
                {
                    try
                    {
                        Constructor constructor = EntityListener.sentryEntityClasses[itemstack.getDamage()].getConstructor(new Class[] {
                            World.class
                        });
                        SdkEntitySentry sdkentitysentry = (SdkEntitySentry)constructor.newInstance(new Object[] {
                            world
                        });
                        sdkentitysentry.setOwner(entityplayer.name);
                        sdkentitysentry.setPositionAndAnglesKeepPrevAngles((double)i + 0.5D, (double)j + 1.0D, (double)k + 0.5D, 0.0F, 0.0F);
                        world.spawnEntity(sdkentitysentry);
                    }
                    catch(Exception exception)
                    {
//                        ModLoader.getLogger().throwing("SdkBlockSentry", "onBlockPlaced", nosuchmethodexception);
//                        SdkTools.ThrowException("An impossible error has occured!", nosuchmethodexception);
                        return itemstack;
                    }
                }
                itemstack.count--;
            }
        }
        return itemstack;
    }

    public String getTranslationKey(ItemStack itemstack)
    {
        return (new StringBuilder()).append(super.getTranslationKey()).append(".").append(itemstack.getDamage()).toString();
    }
}
