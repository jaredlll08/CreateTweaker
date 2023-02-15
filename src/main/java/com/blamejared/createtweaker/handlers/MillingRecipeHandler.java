package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(MillingRecipe.class)
public class MillingRecipeHandler implements IProcessingRecipeHandler<MillingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, MillingRecipe recipe) {
        
        return String.format("<recipetype:create:milling>.addRecipe(\"%s\", [%s], %s, %s);",
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
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof MillingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<MillingRecipe> factory() {
        
        return MillingRecipe::new;
    }
    
}
