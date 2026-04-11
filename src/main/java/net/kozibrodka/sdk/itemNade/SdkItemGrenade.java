package net.kozibrodka.sdk.itemNade;


import net.kozibrodka.sdk.entityNade.SdkEntityGrenade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemGrenade extends TemplateItem
{

    public SdkItemGrenade(Identifier i)
    {
        super(i);
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        itemstack.count--;
        world.playSound(entityplayer, "sdk:grunt", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
        if(!world.isRemote)
        {
            world.spawnEntity(new SdkEntityGrenade(world, entityplayer));
        }
        return itemstack;
    }
}
