package com.blamejared.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/recipe/type/EmptyingRecipe")
@NativeTypeRegistration(value = EmptyingRecipe.class, zenCodeName = "mods.createtweaker.EmptyingRecipe")
public class ExpandEmptyingRecipe {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("resultingFluid")
    public static IFluidStack getResultingFluid(EmptyingRecipe internal) {
        
        return Services.PLATFORM.getRecipeFluidResults(internal).get(0);
    }
    
}
