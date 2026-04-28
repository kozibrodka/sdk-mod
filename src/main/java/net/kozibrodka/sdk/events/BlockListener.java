package net.kozibrodka.sdk.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkGuiAtv;
import net.kozibrodka.sdk.block.*;
import net.kozibrodka.sdk.grinder.SdkGuiGrinder;
import net.kozibrodka.sdk.network.GrapplingPacket;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.kozibrodka.sdk_api.network.*;
import net.kozibrodka.sdk_api.utils.SdkMap;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.client.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;


public class BlockListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();


    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        blockGrinder = new SdkBlockGrinder(Identifier.of(MOD_ID, "blockGrinder")).setTranslationKey(MOD_ID, "blockGrinder").setHardness(3.5F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
        blockPlaque = new SdkBlockPlaque(Identifier.of(MOD_ID, "blockPlaque")).setTranslationKey(MOD_ID, "blockPlaque").setHardness(0.4F).setSoundGroup(Block.WOOD_SOUND_GROUP);
        blockNuke = new SdkBlockNuke(Identifier.of(MOD_ID, "blockNuke")).setTranslationKey(MOD_ID, "blockNuke").setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP);
        blockLighter =  new SdkBlockLighter(Identifier.of(MOD_ID, "blockLighter")).setTranslationKey(MOD_ID, "blockLighter").setHardness(3.5F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
        blockOil =  new SdkBlockOil(Identifier.of(MOD_ID, "blockOil")).setTranslationKey(MOD_ID, "blockOil").setHardness(-1F); //TODO set burnt rates?
        blockCannon =  new SdkBlockCannon(Identifier.of(MOD_ID, "blockCannon")).setTranslationKey(MOD_ID, "blockCannon").setHardness(3.5F).setSoundGroup(Block.DEFAULT_SOUND_GROUP);
//        blockRope = new SdkBlockRope(Identifier.of(MOD_ID, "blockRope")).setTranslationKey(MOD_ID, "blockRope").setHardness(-1F).setSoundGroup(Block.WOOD_SOUND_GROUP).setResistance(6000000F);
        blockRope = new SdkBlockRope(Identifier.of(MOD_ID, "blockRope")).setTranslationKey(MOD_ID, "blockRope").setHardness(4F).setSoundGroup(Block.WOOL_SOUND_GROUP).setResistance(10F);
        blockGrapplingHook =  new SdkBlockGrapplingHook(Identifier.of(MOD_ID, "blockGrapplingHook")).setTranslationKey(MOD_ID, "blockGrapplingHook").setHardness(0.0F).setSoundGroup(Block.METAL_SOUND_GROUP);

        //TODO: blockPlaque render item(look at mod dc - emerald), part of BlockOil, Inventory icons, Render Item Lightometer?, Entity Sentry Model Yaw

        SdkMap.BREAKABLE_LIST.add(blockRope.id);
    }

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        Registry.register(registry ,MOD_ID.id("openGrinder"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openGrinder, SdkTileEntityGrinder::new));
//        registry.registerValueNoMessage(Identifier.of(MOD_ID, "openATV"), BiTuple.of(this::openATV, SdkEntityAtv::new));
    }

    @Environment(EnvType.CLIENT)
    public Screen openGrinder(PlayerEntity player, Inventory inventoryBase) {
        return new SdkGuiGrinder(player.inventory, (SdkTileEntityGrinder) inventoryBase);
    }
    @Environment(EnvType.CLIENT)
    public Screen openATV(PlayerEntity player, Inventory inventoryBase) {
        return new SdkGuiAtv(player.inventory, (SdkEntityAtv) inventoryBase);
    }

    @EventListener
    public void registerPacket(PacketRegisterEvent event) {
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("grappling"), GrapplingPacket.TYPE);

    }

    //blocks
    public static Block blockRope;
    public static Block blockGrapplingHook;
    public static Block blockPlaque;
    public static Block blockGrinder;
    public static Block blockCannon;
    public static Block blockOil;
    public static Block blockLighter;
    public static Block blockNuke;
}
