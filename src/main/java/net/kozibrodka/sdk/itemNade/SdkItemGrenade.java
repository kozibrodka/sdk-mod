package net.kozibrodka.sdk.itemNade;


import net.kozibrodka.sdk.entityNade.SdkEntityGrenade;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class SdkItemGrenade extends TemplateItemBase
{

    public SdkItemGrenade(Identifier i)
    {
        super(i);
    }

    public ItemInstance use(ItemInstance itemstack, Level world, PlayerBase entityplayer)
    {
        itemstack.count--;
        world.playSound(entityplayer, "sdk:grunt", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
        if(!world.isServerSide)
        {
            world.spawnEntity(new SdkEntityGrenade(world, entityplayer));
        }
        return itemstack;
    }
}
