package net.kozibrodka.sdk.events;

import net.kozibrodka.sdk_api.events.init.ItemCasingListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class RecipeListener {

    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();

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
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 0), " # ", " X ", "X X",    '#', ItemListener.itemGunAk47, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 1), " # ", " X ", "X X",    '#', ItemListener.itemGunMp5, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 2), " # ", " X ", "X X",    '#', ItemListener.itemGunShotgun, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 3), " # ", " X ", "X X",    '#', ItemListener.itemGunDeagle, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 4), " # ", " X ", "X X",    '#', ItemListener.itemGunRocketLauncher, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 5), " # ", " X ", "X X",    '#', ItemListener.itemGunRocketLauncherLaser, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 6), " # ", " X ", "X X",    '#', ItemListener.itemGunSniper, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 7), " # ", " X ", "X X",    '#', ItemListener.itemGunFlamethrower, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 8), " # ", " X ", "X X",    '#', ItemListener.itemGunSg552, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 9), " # ", " X ", "X X",    '#', ItemListener.itemGunMinigun, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 10), " # ", " X ", "X X",    '#', ItemListener.itemGunLaser, 'X', ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentry, 1, 11), " # ", " X ", "X X",    '#', ItemListener.itemGunM4, 'X', ItemListener.itemMetalParts);
    }

    public void addBlockRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.blockLighter,1 ),"XXX", "X#X", "XXX", Character.valueOf('X'), BlockBase.COBBLESTONE, Character.valueOf('#'), ItemBase.flintAndSteel);
        CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.blockCannon, 1),"XXX", "X#X", "XXX", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('#'), ItemBase.redstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.blockGrinder, 1),"###", "#X#", "###", Character.valueOf('#'), BlockBase.COBBLESTONE, Character.valueOf('X'), ItemBase.diamond);
        CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.blockPlaque, 9),"###", "###", "###", Character.valueOf('#'), BlockBase.WOOD);
        CraftingRegistry.addShapedRecipe(new ItemInstance(BlockListener.blockNuke, 1),"X#X", "#X#", "X#X", Character.valueOf('X'), BlockBase.TNT, Character.valueOf('#'), ItemBase.redstoneDust);
    }

    public void addGunRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemBase.ironIngot, 1), "XX", "XX", Character.valueOf('X'), ItemCasingListener.itemBulletCasing);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemBase.ironIngot, 1), "XX", "XX", Character.valueOf('X'), ItemCasingListener.itemBulletCasingShell);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletLight, 4), "X", "#", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('#'), ItemBase.gunpowder);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletMedium, 4), "X ", "##", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('#'), ItemBase.gunpowder);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletShell, 4), "X ", "#Y", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('#'), ItemBase.gunpowder, Character.valueOf('Y'), ItemBase.paper);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletRocket, 1), "###", "#X#", "XXX", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletRocketLaser, 1), "#Y#", "#X#", "XXX", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('Y'), ItemBase.redstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBulletHeavy, 4),"XX", "##", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('#'), ItemBase.gunpowder );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunAk47, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockWood, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripWood);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunMp5, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunM4, 1), "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'), ItemListener.itemMagazine,
                Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunFlamethrower, 1), "AB", "DE", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('D'), ItemBase.bucket, Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunSg552, 1),             " F ", "ABC", "DE ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('D'),
                ItemListener.itemMagazine, Character.valueOf('E'), ItemListener.itemGripMetal, Character.valueOf('F'), ItemListener.itemScope);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunSniper, 1),            " F ", "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelLong, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('E'),
                ItemListener.itemGripMetal, Character.valueOf('F'), ItemListener.itemScope );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunLaser, 1),             "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverDiamond, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('E'), ItemListener.itemGripMetal
        );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunDeagle, 1), "AB", " E", Character.valueOf('A'), ItemListener.itemBarrelShort, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('E'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunShotgun, 1),             "ABC", " E ", Character.valueOf('A'), ItemListener.itemBarrelShotgun, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockWood, Character.valueOf('E'), ItemListener.itemGripWood
        );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunRocketLauncher, 1), "AAA", "BB ", Character.valueOf('A'), ItemListener.itemBarrelFat, Character.valueOf('B'), ItemListener.itemGripMetal);
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemGunRocketLauncherLaser, 1), new ItemInstance(ItemBase.diamond), new ItemInstance(ItemListener.itemMetalParts), new ItemInstance(ItemListener.itemGunRocketLauncher));
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGunMinigun, 1),            " F ", "ABC", Character.valueOf('A'), ItemListener.itemBarrelMinigun, Character.valueOf('B'), ItemListener.itemReceiverMetal, Character.valueOf('C'), ItemListener.itemStockMetal, Character.valueOf('F'), ItemListener.itemHandleMinigun
        );
    }

    public void addNadeRecipes(RecipeRegisterEvent event){
//        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentrya, 1), );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrenade, 1), "X#X", "#X#", "X#X", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrenadeHe, 1), "X#X", "#L#", "X#X", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot, 'L', new ItemInstance(ItemBase.dyePowder, 1,4));
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrenadeStun, 1), "X#X", "#Y#", "X#X", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('Y'), ItemBase.glowstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrenadeSmoke, 1), "X#X", "#Y#", "X#X", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('Y'), ItemBase.flint);
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemGrenadeSticky, 1), new ItemInstance(ItemListener.itemGrenade), new ItemInstance(ItemBase.slimeball));
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrenadeIncendiary, 1), " X ", "#Y#", " # ", Character.valueOf('X'), ItemBase.paper, Character.valueOf('#'), BlockBase.GLASS, Character.valueOf('Y'), ItemListener.itemOil);
    }

    public void addUtilRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemTelescope, 1), "#", "X", "X", Character.valueOf('#'), ItemBase.diamond, Character.valueOf('X'), ItemBase.goldIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemParachute, 1), "###", "XYX", "YYY", Character.valueOf('#'), BlockBase.WOOL, Character.valueOf('X'), ItemBase.string, Character.valueOf('Y'), ItemBase.leather);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemScubaTank, 1),  "###", "# #", "###", Character.valueOf('#'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemNightvisionGoggles, 1), "#X#", Character.valueOf('#'), ItemBase.diamond, Character.valueOf('X'), ItemBase.string);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrapplingHook, 1), "X", "#", "#", Character.valueOf('#'), ItemListener.itemRope, Character.valueOf('X'), ItemBase.ironIngot);

        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemJetPack, 1), "###", "#X#", "###", Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('X'), ItemListener.itemOilDrop);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemJetPack, 1), "###", "#X#", "###", Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('X'), ItemListener.itemOil);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemAtv, 1), " X ", "XYX", " X ", Character.valueOf('X'), ItemListener.itemAtvWheel, Character.valueOf('Y'), ItemListener.itemAtvBody);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemWrench, 1), "X", "X", "X", Character.valueOf('X'), ItemBase.ironIngot);
    }

    public void addItemRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemLightometer, 1), " # ", "#X#", " # ", Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('X'), ItemBase.glowstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGoldCoin, 1), "#", Character.valueOf('#'), ItemBase.goldIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemBase.goldIngot, 1),"##", "##", Character.valueOf('#'), ItemListener.itemGoldCoin );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemRope, 1), "##", "##", "##", Character.valueOf('#'), ItemBase.string);

        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemMetalParts, 1), "##", "##", Character.valueOf('#'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemBase.ironIngot, 4), "#", Character.valueOf('#'), ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemOilDrop, 1), " X ", "X#X", " X ", Character.valueOf('X'), ItemBase.gunpowder, Character.valueOf('#'), ItemBase.bucket );
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemOil, 1), new ItemInstance(ItemListener.itemOilDrop), new ItemInstance(ItemBase.bucket));
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemAtvBody, 1), "ZXZ", "XYX", "ZXZ", Character.valueOf('X'), ItemBase.ironIngot, Character.valueOf('Y'), BlockBase.FURNACE, Character.valueOf('Z'), ItemBase.redstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemAtvWheel, 1), " X ", "XYX", " X ", Character.valueOf('X'), ItemBase.leather, Character.valueOf('Y'), ItemBase.ironIngot);

        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBarrelLong, 1), "XXX", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBarrelShort, 1), "XX", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemBarrelFat, 1), "XXX", "XXX", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemBarrelShotgun, 1),new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong) );
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemBarrelMinigun, 1), new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong), new ItemInstance(ItemListener.itemBarrelLong));
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGripWood, 1), "XX", " X", " X", Character.valueOf('X'), BlockBase.WOOD );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGripMetal, 1), "XX", " X", " X", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemHandleMinigun, 1), "XX", " X", "XX", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemStockWood, 1),             "XXX", " XX", Character.valueOf('X'), BlockBase.WOOD);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemStockMetal, 1),  "XXX", " XX", Character.valueOf('X'), ItemBase.ironIngot);
        CraftingRegistry.addShapelessRecipe(new ItemInstance(ItemListener.itemReceiverMetal, 1), ItemBase.ironIngot, ItemBase.ironIngot, ItemListener.itemMetalParts);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemReceiverDiamond, 1), "XX", "XX", Character.valueOf('X'), ItemBase.diamond);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemMagazine, 1),"X", "X", Character.valueOf('X'), ItemBase.ironIngot );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemScope, 1), "X#X", Character.valueOf('X'), ItemBase.diamond, Character.valueOf('#'), ItemBase.ironIngot);
//        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemRopeaaa, 1), );


    }
}
