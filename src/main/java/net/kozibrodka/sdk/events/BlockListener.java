package net.kozibrodka.sdk.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk.atv.SdkEntityAtv;
import net.kozibrodka.sdk.atv.SdkGuiAtv;
import net.kozibrodka.sdk.block.*;
import net.kozibrodka.sdk.grinder.SdkGuiGrinder;
import net.kozibrodka.sdk.tileEntity.SdkTileEntityGrinder;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Null;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class BlockListener {
    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();


    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        blockGrinder = (TemplateBlockWithEntity) new SdkBlockGrinder(Identifier.of(MOD_ID, "blockGrinder")).setTranslationKey(MOD_ID, "blockGrinder").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockPlaque = (TemplateBlockWithEntity) new SdkBlockPlaque(Identifier.of(MOD_ID, "blockPlaque")).setTranslationKey(MOD_ID, "blockPlaque").setHardness(0.4F).setSounds(BlockBase.WOOD_SOUNDS);
        blockNuke = (TemplateBlock) new SdkBlockNuke(Identifier.of(MOD_ID, "blockNuke")).setTranslationKey(MOD_ID, "blockNuke").setHardness(0.0F).setSounds(BlockBase.GRASS_SOUNDS);
        blockLighter = (TemplateBlock) new SdkBlockLighter(Identifier.of(MOD_ID, "blockLighter")).setTranslationKey(MOD_ID, "blockLighter").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockOil = (TemplateBlock) new SdkBlockOil(Identifier.of(MOD_ID, "blockOil")).setTranslationKey(MOD_ID, "blockOil").setHardness(-1F); //TODO set burnt rates?
        blockCannon = (TemplateBlock) new SdkBlockCannon(Identifier.of(MOD_ID, "blockCannon")).setTranslationKey(MOD_ID, "blockCannon").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockRope = (TemplateBlockWithEntity) new SdkBlockRope(Identifier.of(MOD_ID, "blockRope")).setTranslationKey(MOD_ID, "blockRope").setHardness(-1F).setSounds(BlockBase.WOOD_SOUNDS).setBlastResistance(6000000F);
        blockGrapplingHook = (TemplateBlock) new SdkBlockGrapplingHook(Identifier.of(MOD_ID, "blockGrapplingHook")).setTranslationKey(MOD_ID, "blockGrapplingHook").setHardness(0.0F).setSounds(BlockBase.METAL_SOUNDS);

        //TODO: blockPlaque render item(look at mod dc - emerald), part of BlockOil, Inventory icons, Render Item Lightometer?, Entity Sentry Model Yaw

    }

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "openGrinder"), BiTuple.of(this::openGrinder, SdkTileEntityGrinder::new));
//        registry.registerValueNoMessage(Identifier.of(MOD_ID, "openATV"), BiTuple.of(this::openATV, SdkEntityAtv::new));
    }

    @Environment(EnvType.CLIENT)
    public ScreenBase openGrinder(PlayerBase player, InventoryBase inventoryBase) {
        return new SdkGuiGrinder(player.inventory, (SdkTileEntityGrinder) inventoryBase);
    }
    @Environment(EnvType.CLIENT)
    public ScreenBase openATV(PlayerBase player, InventoryBase inventoryBase) {
        return new SdkGuiAtv(player.inventory, (SdkEntityAtv) inventoryBase);
    }

    //blocks
    public static TemplateBlockWithEntity blockRope;
    public static TemplateBlock blockGrapplingHook;
    public static TemplateBlockWithEntity blockPlaque;
    public static TemplateBlockWithEntity blockGrinder;
    public static TemplateBlock blockCannon;
    public static TemplateBlock blockOil;
    public static TemplateBlock blockLighter;
    public static TemplateBlock blockNuke;
}
