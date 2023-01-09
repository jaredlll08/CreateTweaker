package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinForgeRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.blamejared.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@IRecipeHandler.For(CompactingRecipe.class)
public class CompactingRecipeHandler implements IProcessingRecipeHandler<CompactingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, CompactingRecipe recipe) {
        
        Either<Stream<Percentaged<IItemStack>>, IFluidStack> result;
        
        if(!recipe.getFluidResults().isEmpty()) {
            result = Either.right(new MCFluidStack(recipe.getFluidResults().get(0)));
        } else {
            result = Either.left(recipe.getRollableResults()
                    .stream()
                    .map(CreateTweaker::mapProcessingResult));
        }
        
        
        return String.format("<recipetype:create:compacting>.addRecipe(\"%s\", <constant:create:heat_condition:%s>, [%s], [%s], [%s]);",
                recipe.getId(),
                recipe.getRequiredHeat().name().toLowerCase(Locale.ENGLISH),
                result.map(results -> results.map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")), IFluidStack::getCommandString),
                recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ")),
                recipe.getFluidResults()
                        .stream()
                        .map(MCFluidStack::new)
                        .map(MCFluidStack::getCommandString)
                        .collect(Collectors.joining(", "))
        );
        
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof CompactingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<CompactingRecipe> factory() {
        
        return CompactingRecipe::new;
    }
    
}
