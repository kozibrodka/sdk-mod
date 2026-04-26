package net.kozibrodka.sdk.itemNade;


import net.kozibrodka.sdk.entityNade.SdkEntityGrenadeSticky;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemGrenadeSticky extends TemplateItem
{

    public SdkItemGrenadeSticky(Identifier i)
    {
        super(i);
    }

    @Override
    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        itemstack.count--;
        if(!world.isRemote)
        {
            world.playSound(entityplayer, "sdk:grunt", 1.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
            world.spawnEntity(new SdkEntityGrenadeSticky(world, entityplayer));
        }
        return itemstack;
    }

    public static String throwSound = "sdk:grunt";
    public static String bounceSound = "sdk:grenadebounce";
    public static String explosionSound = "random.explode";
}
