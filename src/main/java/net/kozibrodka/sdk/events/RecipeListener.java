package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk_api.events.CasingListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class RecipeListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        addSentryRecipes(event);
        addBlockRecipes(event);
        addGunRecipes(event);
        addNadeRecipes(event);
        addUtilRecipes(event);
        addItemRecipes(event);
    }

    public void addSentryRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 0), " # ", " X ", "X X",    '#', ItemListener.itemGunAk47, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 1), " # ", " X ", "X X",    '#', ItemListener.itemGunMp5, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 2), " # ", " X ", "X X",    '#', ItemListener.itemGunShotgun, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 3), " # ", " X ", "X X",    '#', ItemListener.itemGunDeagle, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 4), " # ", " X ", "X X",    '#', ItemListener.itemGunRocketLauncher, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 5), " # ", " X ", "X X",    '#', ItemListener.itemGunRocketLauncherLaser, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 6), " # ", " X ", "X X",    '#', ItemListener.itemGunSniper, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 7), " # ", " X ", "X X",    '#', ItemListener.itemGunFlamethrower, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 8), " # ", " X ", "X X",    '#', ItemListener.itemGunSg552, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 9), " # ", " X ", "X X",    '#', ItemListener.itemGunMinigun, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 10), " # ", " X ", "X X",    '#', ItemListener.itemGunLaser, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemSentry, 1, 11), " # ", " X ", "X X",    '#', ItemListener.itemGunM4, 'X', ItemListener.itemMetalParts);
    }

    public void addBlockRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.blockLighter,1 ),"XXX", "X#X", "XXX", Character.valueOf('X'), Block.COBBLESTONE, Character.valueOf('#'), Item.FLINT_AND_STEEL);
        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.blockCannon, 1),"XXX", "X#X", "XXX", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.REDSTONE);
        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.blockGrinder, 1),"###", "#X#", "###", Character.valueOf('#'), Block.COBBLESTONE, Character.valueOf('X'), Item.DIAMOND);
        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.blockPlaque, 9),"###", "###", "###", Character.valueOf('#'), Block.PLANKS);
        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.blockNuke, 1),"X#X", "#X#", "X#X", Character.valueOf('X'), Block.TNT, Character.valueOf('#'), Item.REDSTONE);
    }

    public void addGunRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemStack(Item.IRON_INGOT, 1), "XX", "XX", Character.valueOf('X'), CasingListener.itemBulletCasing);
        CraftingRegistry.addShapedRecipe(new ItemStack(Item.IRON_INGOT, 1), "XX", "XX", Character.valueOf('X'), CasingListener.itemBulletCasingShell);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletLight, 4), "X", "#", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.GUNPOWDER);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletMedium, 4), "X ", "##", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.GUNPOWDER);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletShell, 4), "X ", "#Y", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.GUNPOWDER, Character.valueOf('Y'), Item.PAPER);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletRocket, 1), "###", "#X#", "XXX", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletRocketLaser, 1), "#Y#", "#X#", "XXX", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('Y'), Item.REDSTONE);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBulletHeavy, 4),"XX", "##", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('#'), Item.GUNPOWDER );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunAk47, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockWood, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripWood);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunMp5, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunM4, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunFlamethrower, 1), "AB", "DE", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('D'), Item.BUCKET, Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunSg552, 1),             " F ", "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'),
                ItemListener.itemMagazine, Character.valueOf('E'), ItemListener.itemGripMetal, Character.valueOf('F'), ItemListener.itemScope);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunSniper, 1),            " F ", "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('E'),
                ItemListener.itemGripMetal, Character.valueOf('F'), ItemListener.itemScope );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunLaser, 1),             "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverDiamond, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('E'), ItemListener.itemGripMetal
        );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunDeagle, 1), "AB", " E", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunShotgun, 1),             "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelShotgun, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockWood, Character.valueOf('E'), ItemListener.itemGripWood
        );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunRocketLauncher, 1), "AAA", "BB ", Character.valueOf('A'), ItemListener.itemBarrelFat, Character.valueOf('B'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemGunRocketLauncherLaser, 1), new ItemStack(Item.DIAMOND), new ItemStack(ItemListener.itemMetalParts), new ItemStack(ItemListener.itemGunRocketLauncher));
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGunMinigun, 1),            " F ", "ABC", Character.valueOf('A'), ItemListener.itemBarrelMinigun, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('F'), ItemListener.itemHandleMinigun
        );
    }

    public void addNadeRecipes(RecipeRegisterEvent event){
//        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentrya, 1), );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrenade, 1), "X#X", "#X#", "X#X", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrenadeHe, 1), "X#X", "#L#", "X#X", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT, 'L', new ItemStack(Item.DYE, 1,4));
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrenadeStun, 1), "X#X", "#Y#", "X#X", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('Y'), Item.GLOWSTONE_DUST);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrenadeSmoke, 1), "X#X", "#Y#", "X#X", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('Y'), Item.FLINT);
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemGrenadeSticky, 1), new ItemStack(ItemListener.itemGrenade), new ItemStack(Item.SLIMEBALL));
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrenadeIncendiary, 1), " X ", "#Y#", " # ", Character.valueOf('X'), Item.PAPER, Character.valueOf('#'), Block.GLASS, Character.valueOf('Y'), ItemListener.itemOil);
    }

    public void addUtilRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemTelescope, 1), "#", "X", "X", Character.valueOf('#'), Item.DIAMOND, Character.valueOf('X'), Item.GOLD_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemParachute, 1), "###", "XYX", "YYY", Character.valueOf('#'), Block.WOOL, Character.valueOf('X'), Item.STRING, Character.valueOf('Y'), Item.LEATHER);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemScubaTank, 1),  "###", "# #", "###", Character.valueOf('#'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemNightvisionGoggles, 1), "#X#", Character.valueOf('#'), Item.DIAMOND, Character.valueOf('X'), Item.STRING);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGrapplingHook, 1), "X", "#", "#", Character.valueOf('#'), ItemListener.itemRope, Character.valueOf('X'), Item.IRON_INGOT);

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemJetPack, 1), "###", "#X#", "###", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), ItemListener.itemOilDrop);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemJetPack, 1), "###", "#X#", "###", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), ItemListener.itemOil);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemAtv, 1), " X ", "XYX", " X ", Character.valueOf('X'), ItemListener.itemAtvWheel, Character.valueOf('Y'), ItemListener.itemAtvBody);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemWrench, 1), "X", "X", "X", Character.valueOf('X'), Item.IRON_INGOT);
    }

    public void addItemRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemLightometer, 1), " # ", "#X#", " # ", Character.valueOf('#'), Item.IRON_INGOT, Character.valueOf('X'), Item.GLOWSTONE_DUST);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGoldCoin, 1), "#", Character.valueOf('#'), Item.GOLD_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(Item.GOLD_INGOT, 1),"##", "##", Character.valueOf('#'), ItemListener.itemGoldCoin );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemRope, 1), "##", "##", "##", Character.valueOf('#'), Item.STRING);

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemMetalParts, 1), "##", "##", Character.valueOf('#'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(Item.IRON_INGOT, 4), "#", Character.valueOf('#'), ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemOilDrop, 1), " X ", "X#X", " X ", Character.valueOf('X'), Item.GUNPOWDER, Character.valueOf('#'), Item.BUCKET );
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemOil, 1), new ItemStack(ItemListener.itemOilDrop), new ItemStack(Item.BUCKET));
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemAtvBody, 1), "ZXZ", "XYX", "ZXZ", Character.valueOf('X'), Item.IRON_INGOT, Character.valueOf('Y'), Block.FURNACE, Character.valueOf('Z'), Item.REDSTONE);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemAtvWheel, 1), " X ", "XYX", " X ", Character.valueOf('X'), Item.LEATHER, Character.valueOf('Y'), Item.IRON_INGOT);

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBarrelLong, 1), "XXX", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBarrelShort, 1), "XX", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemBarrelFat, 1), "XXX", "XXX", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemBarrelShotgun, 1),new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong) );
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemBarrelMinigun, 1), new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong), new ItemStack(ItemListener.itemBarrelLong));
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGripWood, 1), "XX", " X", " X", Character.valueOf('X'), Block.PLANKS );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemGripMetal, 1), "XX", " X", " X", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemHandleMinigun, 1), "XX", " X", "XX", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemStockWood, 1),             "XXX", " XX", Character.valueOf('X'), Block.PLANKS);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemStockMetal, 1),  "XXX", " XX", Character.valueOf('X'), Item.IRON_INGOT);
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.itemReceiverMetal, 1), Item.IRON_INGOT, Item.IRON_INGOT, ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemReceiverDiamond, 1), "XX", "XX", Character.valueOf('X'), Item.DIAMOND);
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemMagazine, 1),"X", "X", Character.valueOf('X'), Item.IRON_INGOT );
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.itemScope, 1), "X#X", Character.valueOf('X'), Item.DIAMOND, Character.valueOf('#'), Item.IRON_INGOT);
//        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemRopeaaa, 1), );


    }
}
