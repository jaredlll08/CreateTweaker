package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

@IRecipeHandler.For(SandPaperPolishingRecipe.class)
public class SandPaperPolishingRecipeHandler implements IProcessingRecipeHandler<SandPaperPolishingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, SandPaperPolishingRecipe recipe) {
        
        return String.format("<recipetype:create:sandpaper_polishing>.addRecipe(\"%s\", %s, %s);",
                recipe.getId(),
                CreateTweaker.mapProcessingResult(recipe.getRollableResults().get(0)).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0))
                        .getCommandString()
        );
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof SandPaperPolishingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<SandPaperPolishingRecipe> factory() {
        
        return SandPaperPolishingRecipe::new;
    }
    
}
