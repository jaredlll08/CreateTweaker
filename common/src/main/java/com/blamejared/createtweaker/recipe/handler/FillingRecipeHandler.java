package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.Recipe;

@IRecipeHandler.For(FillingRecipe.class)
public class FillingRecipeHandler implements IProcessingRecipeHandler<FillingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, FillingRecipe recipe) {
        
        return String.format("<recipetype:create:filling>.addRecipe(\"%s\", %s, %s, %s);",
                recipe.getId(),
                CreateTweakerHelper.mapProcessingResult(recipe.getRollableResults().get(0)).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0))
                        .getCommandString(),
                (recipe.getFluidIngredients().isEmpty()
                        ? IFluidStack.empty()
                        : Services.PLATFORM.getMatchingFluidStacks(recipe.getRequiredFluid()).get(0)).getCommandString()
        );
        
    }
    
    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {
        
        return recipe instanceof FillingRecipe;
    }
    
    @Override
    public ProcessingRecipeBuilder.ProcessingRecipeFactory<FillingRecipe> factory() {
        
        return FillingRecipe::new;
    }
    
}
