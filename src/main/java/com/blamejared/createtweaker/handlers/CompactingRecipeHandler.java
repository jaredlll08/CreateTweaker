package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(CompactingRecipe.class)
public class CompactingRecipeHandler implements IRecipeHandler<CompactingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, CompactingRecipe recipe) {
        
        Either<MCItemStackMutable, MCFluidStackMutable> result;
        
        if(!recipe.getFluidResults().isEmpty()) {
            result = Either.right(new MCFluidStackMutable(recipe.getFluidResults().get(0)));
        } else {
            result = Either.left(new MCItemStackMutable(recipe.getResultItem()));
        }
        
        String output = String.format("<recipetype:create:compacting>.addRecipe(\"%s\", \"%s\", %s, [%s]",
                recipe.getId(),
                recipe.getRequiredHeat().name(),
                result.map(MCItemStackMutable::getCommandString, MCFluidStackMutable::getCommandString),
                recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", "))
        );
        
        if(!recipe.getFluidResults().isEmpty()) {
            output += String.format(", [%s]", recipe.getFluidResults()
                    .stream()
                    .map(MCFluidStackMutable::new)
                    .map(MCFluidStackMutable::getCommandString)
                    .collect(Collectors.joining(", ")));
        }
        
        return output + ");";
        
    }
    
    @Override
    public Optional<Function<ResourceLocation, CompactingRecipe>> replaceIngredients(IRecipeManager manager, CompactingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<CompactingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<CompactingRecipe>) AllRecipeTypes.COMPACTING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults());
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
        
    }
    
}
