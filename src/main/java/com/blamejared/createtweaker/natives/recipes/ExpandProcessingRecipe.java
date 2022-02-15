package com.blamejared.createtweaker.natives.recipes;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("mods/createtweaker/recipes/type/ProcessingRecipe")
@NativeTypeRegistration(value = ProcessingRecipe.class, zenCodeName = "mods.createtweaker.ProcessingRecipe")
public class ExpandProcessingRecipe {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidIngredients")
    public static List<FluidIngredient> getFluidIngredients(ProcessingRecipe internal) {
        
        return internal.getFluidIngredients();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rollableResults")
    public static List<ProcessingOutput> getRollableResults(ProcessingRecipe internal) {
        
        return internal.getRollableResults();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidResults")
    public static List<FluidStack> getFluidResults(ProcessingRecipe internal) {
        
        return internal.getFluidResults();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rollableResultsAsItemStacks")
    public static List<ItemStack> getRollableResultsAsItemStacks(ProcessingRecipe internal) {
        
        return internal.getRollableResultsAsItemStacks();
    }
    
    @ZenCodeType.Method
    public static List<ItemStack> rollResults(ProcessingRecipe internal) {
        
        return internal.rollResults();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("processingDuration")
    public static int getProcessingDuration(ProcessingRecipe internal) {
        
        return internal.getProcessingDuration();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredHeat")
    public static HeatCondition getRequiredHeat(ProcessingRecipe internal) {
        
        return internal.getRequiredHeat();
    }
    
    
}
