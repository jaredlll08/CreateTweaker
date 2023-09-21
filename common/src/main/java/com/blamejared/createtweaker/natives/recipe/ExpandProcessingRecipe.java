package com.blamejared.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "unchecked"})
@ZenRegister
@Document("mods/createtweaker/recipe/type/ProcessingRecipe")
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
    public static List<IFluidStack> getFluidResults(ProcessingRecipe internal) {
        
        return Services.PLATFORM.getRecipeFluidResults(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rollableResultsAsItemStacks")
    public static List<IItemStack> getRollableResultsAsItemStacks(ProcessingRecipe internal) {
        
        List<ItemStack> rollableResultsAsItemStacks = internal.getRollableResultsAsItemStacks();
        return rollableResultsAsItemStacks.stream()
                .map(IItemStack::of)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public static List<IItemStack> rollResults(ProcessingRecipe internal) {
        
        List<ItemStack> list = internal.rollResults();
        return list.stream().map(IItemStack::of).collect(Collectors.toList());
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
