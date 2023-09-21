package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("mods/createtweaker/FluidIngredient")
@NativeTypeRegistration(value = FluidIngredient.class, zenCodeName = "mods.createtweaker.FluidIngredient")
public class ExpandFluidIngredient {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredAmount")
    public static long getRequiredAmount(FluidIngredient internal) {
        
        return Services.PLATFORM.getRequiredAmount(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("matchingFluidStacks")
    public static List<IFluidStack> getMatchingFluidStacks(FluidIngredient internal) {
        
        return Services.PLATFORM.getMatchingFluidStacks(internal);
    }
    
    @ZenCodeType.Method
    public static boolean test(FluidIngredient internal, IFluidStack t) {
        
        return Services.PLATFORM.testFluidIngredient(internal, t);
    }
    
}
