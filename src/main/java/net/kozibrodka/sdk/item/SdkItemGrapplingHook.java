package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.events.HookListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;



public class SdkItemGrapplingHook extends TemplateItemBase {

    public SdkItemGrapplingHook(Identifier identifier) {
        super(identifier);
        maxStackSize = 1;
    }

    public boolean isRendered3d()
    {
        return true;
    }

    public boolean shouldSpinWhenRendering()
    {
        return true;
    }

    public ItemInstance use(ItemInstance itemstack, Level world, PlayerBase entityplayer)
    {
        if(HookListener.grapplingHooks.get(entityplayer) != null)
        {
            int i = ((SdkEntityGrapplingHook) HookListener.grapplingHooks.get(entityplayer)).catchFish();
            entityplayer.swingItem();
        } else
        {
            world.playSoundAtEntity(entityplayer, "sdk.grunt", 1.0F, 1.0F / (itemRand.nextFloat() * 0.1F + 0.95F));
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(new SdkEntityGrapplingHook(world, entityplayer));
            }
            entityplayer.swingItem();
        }
        return itemstack;
    }
}
