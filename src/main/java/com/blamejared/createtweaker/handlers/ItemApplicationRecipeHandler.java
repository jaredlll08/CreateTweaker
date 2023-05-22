package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(ManualApplicationRecipe.class)
public class ItemApplicationRecipeHandler implements IProcessingRecipeHandler<ManualApplicationRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, ManualApplicationRecipe recipe) {
        
        return String.format("<recipetype:create:item_application>.addRecipe(\"%s\", [%s], %s, %s);",
                recipe.getId(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweaker::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getProcessedItem())
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getRequiredHeldItem())
                        .getCommandString()
        
        );
        
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof ManualApplicationRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<ManualApplicationRecipe> factory() {
        
        return ManualApplicationRecipe::new;
    }
    
}
