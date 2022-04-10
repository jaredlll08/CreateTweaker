package com.blamejared.createtweaker.natives.recipes;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/recipe/type/EmptyingRecipe")
@NativeTypeRegistration(value = EmptyingRecipe.class, zenCodeName = "mods.createtweaker.EmptyingRecipe")
public class ExpandEmptyingRecipe {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("resultingFluid")
    public static MCFluidStack getResultingFluid(EmptyingRecipe internal) {
        
        return new MCFluidStack(internal.getResultingFluid());
    }
    
}
