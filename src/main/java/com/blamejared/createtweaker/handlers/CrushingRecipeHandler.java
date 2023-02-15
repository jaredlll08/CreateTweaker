package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(CrushingRecipe.class)
public class CrushingRecipeHandler implements IProcessingRecipeHandler<CrushingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, CrushingRecipe recipe) {
        
        return String.format("<recipetype:create:crushing>.addRecipe(\"%s\", [%s], %s, %s);",
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
        
        return recipe instanceof CrushingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<CrushingRecipe> factory() {
        
        return CrushingRecipe::new;
    }
    
    @Override
    public Optional<CrushingRecipe> recompose(IRecipeManager<? super CrushingRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        
        return Optional.empty();
    }
    
}
