package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:mechanical_crafting>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.MechanicalCrafterManager")
@Document("mods/createtweaker/MechanicalCrafterManager")
public class MechanicalCrafterManager implements IRecipeManager<MechanicalCraftingRecipe> {
    
    /**
     * Adds a recipe to the Mechanical Crafter.
     *
     * @param name        The name of the recipe.
     * @param output      The output of the recipe.
     * @param ingredients The ingredients of the recipe.
     *
     * @docParam name "mechanized"
     * @docParam output <item:minecraft:diamond>
     * @docParam ingredients [[<item:minecraft:dirt>, <item:minecraft:air>, <item:minecraft:dirt>], [<item:minecraft:air>, <item:minecraft:dirt>, <item:minecraft:air>]]
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient[][] ingredients) {
        
        addInternal(name, output, ingredients, false);
    }
    
    /**
     * Adds a mirrored recipe to the Mechanical Crafter.
     *
     * @param name        The name of the recipe.
     * @param output      The output of the recipe.
     * @param ingredients The ingredients of the recipe.
     *
     * @docParam name "mirrorized"
     * @docParam output <item:minecraft:glass>
     * @docParam ingredients [[<item:minecraft:diamond>, <item:minecraft:air>, <item:minecraft:diamond>], [<item:minecraft:air>, <item:minecraft:diamond>, <item:minecraft:air>]]
     */
    @ZenCodeType.Method
    public void addMirroredRecipe(String name, IItemStack output, IIngredient[][] ingredients) {
        
        addInternal(name, output, ingredients, true);
    }
    
    private void addInternal(String name, IItemStack output, IIngredient[][] ingredients, boolean mirrored) {
        
        name = fixRecipeName(name);
        int width = ingredients[0].length;
        for(IIngredient[] value : ingredients) {
            if(value.length != width) {
                throw new IllegalArgumentException("Create Mechanical Crafter IIngredient array needs to have the same length for all entries (the arrays need to contain the exact same amount of ingredients, use <item:minecraft:air> to pad the arrays out!)");
            }
        }
        
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        NonNullList<Ingredient> ingredientList = NonNullList.create();
        
        for(IIngredient[] iIngredients : ingredients) {
            for(IIngredient iIngredient : iIngredients) {
                ingredientList.add(iIngredient.asVanillaIngredient());
            }
        }
        
        NonNullList<Ingredient> list = NonNullList.of(Ingredient.EMPTY, ingredientList
                .toArray(new Ingredient[0]));
        MechanicalCraftingRecipe recipe = new MechanicalCraftingRecipe(resourceLocation, "", width, ingredients.length, list, output
                .getInternal(), mirrored);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Override
    public RecipeType<MechanicalCraftingRecipe> getRecipeType() {
        
        return AllRecipeTypes.MECHANICAL_CRAFTING.getType();
    }
    
}
