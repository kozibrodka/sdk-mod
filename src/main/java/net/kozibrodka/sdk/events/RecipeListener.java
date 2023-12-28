package net.kozibrodka.sdk.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class RecipeListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

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

    }

    public void addNadeRecipes(RecipeRegisterEvent event){
//        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemSentrya, 1), );
    }

    public void addUtilRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemTelescope, 1), "#", "X", "X", Character.valueOf('#'), ItemBase.diamond, Character.valueOf('X'), ItemBase.goldIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemParachute, 1), "###", "XYX", "YYY", Character.valueOf('#'), BlockBase.WOOL, Character.valueOf('X'), ItemBase.string, Character.valueOf('Y'), ItemBase.leather);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemScubaTank, 1),  "###", "# #", "###", Character.valueOf('#'), ItemBase.ironIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemNightvisionGoggles, 1), "#X#", Character.valueOf('#'), ItemBase.diamond, Character.valueOf('X'), ItemBase.string);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGrapplingHook, 1), "X", "#", "#", Character.valueOf('#'), ItemListener.itemRope, Character.valueOf('X'), ItemBase.ironIngot);
    }

    public void addItemRecipes(RecipeRegisterEvent event){
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemLightometer, 1), " # ", "#X#", " # ", Character.valueOf('#'), ItemBase.ironIngot, Character.valueOf('X'), ItemBase.glowstoneDust);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemGoldCoin, 1), "#", Character.valueOf('#'), ItemBase.goldIngot);
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemBase.goldIngot, 1),"##", "##", Character.valueOf('#'), ItemListener.itemGoldCoin );
        CraftingRegistry.addShapedRecipe(new ItemInstance(ItemListener.itemRope, 1), "##", "##", "##", Character.valueOf('#'), ItemBase.string);

    }
}
