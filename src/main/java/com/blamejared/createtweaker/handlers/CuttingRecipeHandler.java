package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(CuttingRecipe.class)
public class CuttingRecipeHandler implements IRecipeHandler<CuttingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, CuttingRecipe recipe) {
        
        return String.format("<recipetype:create:cutting>.addRecipe(\"%s\", [%s], %s, %s);",
                recipe.getId(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweaker::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString(),
                recipe.getProcessingDuration()
        );
        
    }
    
    @Override
    public Optional<Function<ResourceLocation, CuttingRecipe>> replaceIngredients(IRecipeManager manager, CuttingRecipe recipe, List<IReplacementRule> rules) {
    
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<CuttingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<CuttingRecipe>) AllRecipeTypes.CUTTING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults().toArray(ProcessingOutput[]::new));
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
