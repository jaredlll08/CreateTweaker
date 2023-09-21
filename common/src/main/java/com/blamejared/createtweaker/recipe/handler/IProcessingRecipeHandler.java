package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.blamejared.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
import java.util.stream.Collectors;

public interface IProcessingRecipeHandler<T extends ProcessingRecipe<?>> extends IRecipeHandler<T> {
    
    default <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super T> manager, final T first, final U secondRecipe) {
        
        if(isGoodRecipe(secondRecipe)) {
            final T second = (T) secondRecipe;
            if(first.getIngredients().size() != second.getIngredients().size() || !first.getRequiredHeat()
                    .equals(second.getRequiredHeat())) {
                return false;
            }
            return IngredientUtil.doIngredientsConflict(first.getIngredients(), second.getIngredients()) && Services.PLATFORM.doFluidIngredientsConflict(first, second);
            
        }
        
        return false;
    }
    
    default Optional<IDecomposedRecipe> decompose(IRecipeManager<? super T> manager, T recipe) {
        
        return Optional.of(IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .toList())
                .with(BuiltinRecipeComponents.Output.CHANCED_ITEMS, recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .toList())
                .with(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS, recipe.getFluidIngredients()
                        .stream()
                        .map(CreateTweakerHelper::mapFluidIngredientsToCT)
                        .toList())
                .with(BuiltinRecipeComponents.Output.FLUIDS, Services.PLATFORM.getRecipeFluidResults(recipe)
                        .stream()
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
        builder.withItemOutputs(recipe.getOrThrow(BuiltinRecipeComponents.Output.CHANCED_ITEMS)
                .stream()
                .map(CreateTweakerHelper::mapPercentagedToProcessingOutput)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withFluidIngredients(recipe.getOrThrow(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS)
                .stream()
                .map(CreateTweakerHelper::mapFluidIngredients)
                .collect(Collectors.toCollection(NonNullList::create)));
        Services.PLATFORM.withFluidOutputs(builder, recipe.getOrThrow(BuiltinRecipeComponents.Output.FLUIDS));
        builder.duration(recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME));
        builder.requiresHeat(recipe.getOrThrowSingle(CreateTweakerRecipeComponents.Input.HEAT));
        return Optional.of(builder.build());
    }
    
    boolean isGoodRecipe(Recipe<?> recipe);
    
    ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory();
    
}
