package com.blamejared.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/recipe/type/FillingRecipe")
@NativeTypeRegistration(value = FillingRecipe.class, zenCodeName = "mods.createtweaker.FillingRecipe")
public class ExpandFillingRecipe {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredFluid")
    public static FluidIngredient getRequiredFluid(FillingRecipe internal) {
        
        return internal.getRequiredFluid();
    }
    
}
