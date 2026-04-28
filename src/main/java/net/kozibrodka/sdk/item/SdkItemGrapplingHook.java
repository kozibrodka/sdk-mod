package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.entity.SdkEntityGrapplingHook;
import net.kozibrodka.sdk.events.HookListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemGrapplingHook extends TemplateItem {

    public SdkItemGrapplingHook(Identifier identifier) {
        super(identifier);
        maxCount = 1;
    }

    @Override
    public boolean isHandheld()
    {
        return true;
    }

    @Override
    public boolean isHandheldRod() {return true;}

    @Override
    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        if(!world.isRemote) {
            if (HookListener.grapplingHooks.get(entityplayer) != null) {
                int i = ((SdkEntityGrapplingHook) HookListener.grapplingHooks.get(entityplayer)).catchFish();
            } else {
                world.playSound(entityplayer, "sdk:grunt", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
                    world.spawnEntity(new SdkEntityGrapplingHook(world, entityplayer));
            }
        }
        entityplayer.swingHand();
        return itemstack;
    }
}
