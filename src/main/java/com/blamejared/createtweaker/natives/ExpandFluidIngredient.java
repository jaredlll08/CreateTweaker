package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("mods/createtweaker/FluidIngredient")
@NativeTypeRegistration(value = FluidIngredient.class, zenCodeName = "mods.createtweaker.FluidIngredient")
public class ExpandFluidIngredient {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredAmount")
    public static int getRequiredAmount(FluidIngredient internal) {
        
        return internal.getRequiredAmount();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("matchingFluidStacks")
    public static List<IFluidStack> getMatchingFluidStacks(FluidIngredient internal) {
        
        return internal.getMatchingFluidStacks().stream().map(MCFluidStack::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public static boolean test(FluidIngredient internal, IFluidStack t) {
        
        return internal.test(t.getInternal());
    }
    
}
