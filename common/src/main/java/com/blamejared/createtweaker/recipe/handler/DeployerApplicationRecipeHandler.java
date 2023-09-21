package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.stream.Collectors;

@IRecipeHandler.For(DeployerApplicationRecipe.class)
public class DeployerApplicationRecipeHandler implements IProcessingRecipeHandler<DeployerApplicationRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, DeployerApplicationRecipe recipe) {
        
        return String.format("<recipetype:create:deploying>.addRecipe(\"%s\", %s, %s, [%s]);",
                recipe.getId(),
                IIngredient.fromIngredient(recipe.getProcessedItem())
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getRequiredHeldItem())
                        .getCommandString(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", "))
        );
        
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof DeployerApplicationRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<DeployerApplicationRecipe> factory() {
        
        return DeployerApplicationRecipe::new;
    }
    
}
