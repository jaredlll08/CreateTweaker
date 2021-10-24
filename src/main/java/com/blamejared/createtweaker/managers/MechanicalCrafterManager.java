package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.crafter.MechanicalCraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.MechanicalCrafterManager")
public class MechanicalCrafterManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient[][] ingredients) {
    
        addInternal(name, output, ingredients, false);
    }
    
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
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<MechanicalCraftingRecipe> getRecipeType() {
        
        return AllRecipeTypes.MECHANICAL_CRAFTING.getType();
    }
    
}
