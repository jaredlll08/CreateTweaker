package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

@IRecipeHandler.For(EmptyingRecipe.class)
public class EmptyingRecipeHandler implements IProcessingRecipeHandler<EmptyingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, EmptyingRecipe recipe) {
        
        List<IFluidStack> fluidResults = Services.PLATFORM.getRecipeFluidResults(recipe);
        return String.format("<recipetype:create:emptying>.addRecipe(\"%s\", %s, %s, %s);",
                recipe.getId(),
                CreateTweakerHelper.mapProcessingResult(recipe.getRollableResults().get(0)).getCommandString(),
                (fluidResults.isEmpty() ? IFluidStack.empty() : fluidResults.get(0)).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString()
        );
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof EmptyingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<EmptyingRecipe> factory() {
        
        return EmptyingRecipe::new;
    }
    
}
