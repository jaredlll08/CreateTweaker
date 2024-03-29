package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(PressingRecipe.class)
public class PressingRecipeHandler implements IProcessingRecipeHandler<PressingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, PressingRecipe recipe) {
        
        return String.format("<recipetype:create:pressing>.addRecipe(\"%s\", [%s], %s);",
                recipe.getId(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getIngredients().get(0))
                        .getCommandString()
        );
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof PressingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<PressingRecipe> factory() {
        
        return PressingRecipe::new;
    }
    
}
