package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.fan.HauntingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(HauntingRecipe.class)
public class HauntingRecipeHandler implements IRecipeHandler<HauntingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, HauntingRecipe recipe) {
        
        return String.format("<recipetype:create:haunting>.addRecipe(\"%s\", [%s], %s);",
                recipe.getId(),
                recipe.getRollableResults().stream().map(CreateTweaker::mapProcessingResult).map(Percentaged::getCommandString).collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getIngredients().get(0))
                        .getCommandString()
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, HauntingRecipe>> replaceIngredients(IRecipeManager manager, HauntingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<HauntingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<HauntingRecipe>) AllRecipeTypes.HAUNTING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults());
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
