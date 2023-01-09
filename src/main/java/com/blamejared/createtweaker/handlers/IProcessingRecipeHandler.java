package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinForgeRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.createtweaker.CreateTweaker;
import com.blamejared.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;
import java.util.stream.Collectors;

public interface IProcessingRecipeHandler<T extends ProcessingRecipe<? extends Container>> extends IRecipeHandler<T> {
    
    default <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super T> manager, final T first, final U secondRecipe) {
        
        if(isGoodRecipe(secondRecipe)) {
            final T second = (T) secondRecipe;
            if(first.getIngredients().size() != second.getIngredients().size() || !first.getRequiredHeat()
                    .equals(second.getRequiredHeat())) {
                return false;
            }
            return IngredientUtil.doIngredientsConflict(first.getIngredients(), second.getIngredients()) && IngredientUtil.doIngredientsConflict(first.getFluidIngredients(),
                    second.getFluidIngredients(),
                    FluidIngredient.EMPTY::equals,
                    fluidIngredient -> fluidIngredient.getMatchingFluidStacks().toArray(FluidStack[]::new),
                    FluidStack::containsFluid);
            
        }
        
        return false;
    }
    
    default Optional<IDecomposedRecipe> decompose(IRecipeManager<? super T> manager, T recipe) {
        
        return Optional.of(IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .toList())
                .with(BuiltinRecipeComponents.Output.CHANCED_ITEMS_SINGLE, recipe.getRollableResults()
                        .stream()
                        .map(CreateTweaker::mapProcessingResult)
                        .toList())
                .with(BuiltinForgeRecipeComponents.Input.FLUID_INGREDIENTS, recipe.getFluidIngredients()
                        .stream()
                        .map(CreateTweaker::mapFluidIngredientsToCT)
                        .toList())
                .with(BuiltinForgeRecipeComponents.Output.FLUIDS, recipe.getFluidResults()
                        .stream()
                        .map(IFluidStack::of)
                        .toList())
                .with(BuiltinRecipeComponents.Processing.TIME, recipe.getProcessingDuration())
                .with(CreateTweakerRecipeComponents.Input.HEAT, recipe.getRequiredHeat())
                .build());
    }
    
    default Optional<T> recompose(IRecipeManager<? super T> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        
        ProcessingRecipeBuilder<T> builder = new ProcessingRecipeBuilder<>(factory(), name);
        builder.withItemIngredients(recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS)
                .stream()
                .map(IIngredient::asVanillaIngredient)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withItemOutputs(recipe.getOrThrow(BuiltinRecipeComponents.Output.CHANCED_ITEMS_SINGLE)
                .stream()
                .map(CreateTweaker::mapPercentagedToProcessingOutput)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withFluidIngredients(recipe.getOrThrow(BuiltinForgeRecipeComponents.Input.FLUID_INGREDIENTS)
                .stream()
                .map(CreateTweaker::mapFluidIngredients)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withFluidOutputs(recipe.getOrThrow(BuiltinForgeRecipeComponents.Output.FLUIDS)
                .stream()
                .map(IFluidStack::getInternal)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.duration(recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME));
        builder.requiresHeat(recipe.getOrThrowSingle(CreateTweakerRecipeComponents.Input.HEAT));
        return Optional.of(builder.build());
    }
    
    boolean isGoodRecipe(Recipe<?> recipe);
    
    ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory();
    
}
