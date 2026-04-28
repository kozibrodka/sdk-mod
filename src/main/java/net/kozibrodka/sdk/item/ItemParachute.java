package net.kozibrodka.sdk.item;

import net.kozibrodka.sdk.entity.SdkEntityParachute;
import net.kozibrodka.sdk.events.ItemListener;
import net.kozibrodka.sdk_api.network.AlphaSoundPacket;
import net.kozibrodka.sdk_api.utils.SdkParachuteItem;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.sdk_api.utils.SdkTools;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;

import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;


public class ItemParachute extends TemplateArmorItem implements ArmorTextureProvider, SdkParachuteItem {
    public ItemParachute(Identifier i)
    {
        super(i, -1, 0, 1);
        setMaxDamage(7);
//        setMaxDamage(0);
    }

    @Override
    public Identifier getTexture(ArmorItem armour) {
        return ItemListener.MOD_ID.id("parachute");
    }

    @Override
    public void useParachute(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if(!SdkTools.onGroundOrInWater(world, entityplayer))
        {
            itemstack.damage(1, entityplayer);
            if(itemstack.count == 0) /// TEN KOD NIE DZIAŁA....
            {
                boolean flag = false;
                int i = 0;
                do
                {
                    if(i >= entityplayer.inventory.armor.length)
                    {
                        break;
                    }
                    if(entityplayer.inventory.armor[i] == itemstack)
                    {
                        entityplayer.inventory.armor[i] = null;
                        flag = true;
                        break;
                    }
                    i++;
                } while(true);
                if(!flag)
                {
                    int j = 0;
                    do
                    {
                        if(j >= entityplayer.inventory.main.length)
                        {
                            break;
                        }
                        if(entityplayer.inventory.main[j] == itemstack)
                        {
                            entityplayer.inventory.main[j] = null;
                            boolean flag1 = true;
                            break;
                        }
                        j++;
                    } while(true);
                }
            }
            if(SdkEnvTool.isEnvServ()){
                PacketHelper.sendToAllTracking(entityplayer, new AlphaSoundPacket("sdk:parachute", entityplayer.id, 0.5F, 1.0F / (SdkTools.random.nextFloat() * 0.1F + 0.95F)));
            }
            if(!world.isRemote){
                world.playSound(entityplayer, "sdk:parachute", 0.5F, 1.0F / (SdkTools.random.nextFloat() * 0.1F + 0.95F));

            }
            world.spawnEntity(new SdkEntityParachute(world, entityplayer));
        }
    }
}
