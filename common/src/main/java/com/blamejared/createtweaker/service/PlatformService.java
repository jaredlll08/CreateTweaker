package com.blamejared.createtweaker.service;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public interface PlatformService {
    
    long getRequiredAmount(FluidIngredient ingredient);
    <T extends ProcessingRecipe<?>> boolean doFluidIngredientsConflict(T first, T second);
    
    boolean testFluidIngredient(FluidIngredient ingredient, IFluidStack stack);
    
    
    <T extends ProcessingRecipe<? extends Container>> ProcessingRecipeBuilder<T> withFluidOutputs(ProcessingRecipeBuilder<T> builder, List<IFluidStack> fluidOutputs);
    
    ProcessingRecipeBuilder<ProcessingRecipe<Container>> output(ProcessingRecipeBuilder<?> builder, IFluidStack output);
    
    ProcessingRecipeBuilder<ProcessingRecipe<Container>> withFluidOutputs(ProcessingRecipeBuilder<ProcessingRecipe<Container>> builder, IFluidStack... outputs);
    
    List<IFluidStack> getRecipeFluidResults(ProcessingRecipe<?> recipe);
    
    List<IFluidStack> getMatchingFluidStacks(FluidIngredient ingredient);
    
    FluidIngredient fromFluidStack(IFluidStack stack);
    
    FluidIngredient fromTag(TagKey<Fluid> tag, int amount);
    
    CTFluidIngredient mapFluidIngredientsToCT(FluidIngredient ingredient);
    
    
}
