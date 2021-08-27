package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(DeployerApplicationRecipe.class)
public class DeployerApplicationRecipeHandler implements IRecipeHandler<DeployerApplicationRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, DeployerApplicationRecipe recipe) {
        
        return String.format("<recipetype:create:deploying>.addRecipe(\"%s\", %s, %s, [%s]);",
                recipe.getId(),
                IIngredient.fromIngredient(recipe.getProcessedItem()).getCommandString(),
                IIngredient.fromIngredient(recipe.getRequiredHeldItem()).getCommandString(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweaker::mapMutableProcessingResult)
                        .map(MCWeightedItemStack::getCommandString)
                        .collect(Collectors.joining(", "))
        );
        
    }
    
    @Override
    public Optional<Function<ResourceLocation, DeployerApplicationRecipe>> replaceIngredients(IRecipeManager manager, DeployerApplicationRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<DeployerApplicationRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<DeployerApplicationRecipe>) AllRecipeTypes.DEPLOYING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults());
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
