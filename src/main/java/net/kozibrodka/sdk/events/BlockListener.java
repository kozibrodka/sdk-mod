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
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Null;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class BlockListener {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();


    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        blockGrinder = (TemplateBlockWithEntity) new SdkBlockGrinder(Identifier.of(MOD_ID, "blockGrinder")).setTranslationKey(MOD_ID, "blockGrinder").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockPlaque = (TemplateBlockWithEntity) new SdkBlockPlaque(Identifier.of(MOD_ID, "blockPlaque")).setTranslationKey(MOD_ID, "blockPlaque").setHardness(0.4F).setSounds(BlockBase.WOOD_SOUNDS);
        blockNuke = (TemplateBlockBase) new SdkBlockNuke(Identifier.of(MOD_ID, "blockNuke")).setTranslationKey(MOD_ID, "blockNuke").setHardness(0.0F).setSounds(BlockBase.GRASS_SOUNDS);
        blockLighter = (TemplateBlockBase) new SdkBlockLighter(Identifier.of(MOD_ID, "blockLighter")).setTranslationKey(MOD_ID, "blockLighter").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockOil = (TemplateBlockBase) new SdkBlockOil(Identifier.of(MOD_ID, "blockOil")).setTranslationKey(MOD_ID, "blockOil").setHardness(-1F); //TODO set burnt rates?
        blockCannon = (TemplateBlockBase) new SdkBlockCannon(Identifier.of(MOD_ID, "blockCannon")).setTranslationKey(MOD_ID, "blockCannon").setHardness(3.5F).setSounds(BlockBase.STONE_SOUNDS);
        blockRope = (TemplateBlockWithEntity) new SdkBlockRope(Identifier.of(MOD_ID, "blockRope")).setTranslationKey(MOD_ID, "blockRope").setHardness(-1F).setSounds(BlockBase.WOOD_SOUNDS).setBlastResistance(6000000F);
        blockGrapplingHook = (TemplateBlockBase) new SdkBlockGrapplingHook(Identifier.of(MOD_ID, "blockGrapplingHook")).setTranslationKey(MOD_ID, "blockGrapplingHook").setHardness(0.0F).setSounds(BlockBase.METAL_SOUNDS);
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
    public static TemplateBlockBase blockGrapplingHook;
    public static TemplateBlockWithEntity blockPlaque;
    public static TemplateBlockWithEntity blockGrinder;
    public static TemplateBlockBase blockCannon;
    public static TemplateBlockBase blockOil;
    public static TemplateBlockBase blockLighter;
    public static TemplateBlockBase blockNuke;
}
