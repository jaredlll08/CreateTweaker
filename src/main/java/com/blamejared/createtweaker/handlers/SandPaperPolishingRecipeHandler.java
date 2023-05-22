package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

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
