package com.blamejared.createtweaker.natives.recipes;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;

@ZenRegister
@Document("mods/createtweaker/recipes/type/FillingRecipe")
@NativeTypeRegistration(value = FillingRecipe.class, zenCodeName = "mods.createtweaker.FillingRecipe")
public class ExpandFillingRecipe {
    
    //TODO Expose FluidIngredient and expose "FluidIngredient getRequiredFluid()"
}
