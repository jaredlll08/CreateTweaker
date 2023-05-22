package com.blamejared.createtweaker.natives.recipes;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/recipe/type/ItemApplicationRecipe")
@NativeTypeRegistration(value = ItemApplicationRecipe.class, zenCodeName = "mods.createtweaker.ItemApplicationRecipe")
public class ExpandItemApplicationRecipe {
    
    
    /**
     * Should the recipe keep the held item?
     *
     * @return True if the recipe keeps the held item. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldKeepHeldItem")
    public static boolean shouldKeepHeldItem(ItemApplicationRecipe internal) {
        
        return internal.shouldKeepHeldItem();
    }
    
    /**
     * Gets the required held item.
     *
     * @return The required held item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredHeldItem")
    public static IIngredient getRequiredHeldItem(ItemApplicationRecipe internal) {
        
        return IIngredient.fromIngredient(internal.getRequiredHeldItem());
    }
    
    /**
     * Gets the processed Item.
     *
     * @return The processed Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("processedItem")
    public static IIngredient getProcessedItem(ItemApplicationRecipe internal) {
        
        return IIngredient.fromIngredient(internal.getProcessedItem());
    }
    
}
