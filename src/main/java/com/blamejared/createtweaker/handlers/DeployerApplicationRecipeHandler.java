package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
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
                        .map(CreateTweaker::mapProcessingResult)
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
