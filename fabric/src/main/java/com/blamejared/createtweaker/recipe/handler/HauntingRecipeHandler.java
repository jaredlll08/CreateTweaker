package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(HauntingRecipe.class)
public class HauntingRecipeHandler implements IProcessingRecipeHandler<HauntingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, HauntingRecipe recipe) {
        
        return String.format("<recipetype:create:haunting>.addRecipe(\"%s\", [%s], %s);",
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
        
        return recipe instanceof HauntingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<HauntingRecipe> factory() {
        
        return HauntingRecipe::new;
    }
    
}
