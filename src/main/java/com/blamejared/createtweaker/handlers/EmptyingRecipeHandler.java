package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

@IRecipeHandler.For(EmptyingRecipe.class)
public class EmptyingRecipeHandler implements IProcessingRecipeHandler<EmptyingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, EmptyingRecipe recipe) {
        
        return String.format("<recipetype:create:emptying>.addRecipe(\"%s\", %s, %s, %s);",
                recipe.getId(),
                CreateTweaker.mapProcessingResult(recipe.getRollableResults().get(0)).getCommandString(),
                new MCFluidStack(recipe.getFluidResults()
                        .isEmpty() ? FluidStack.EMPTY : recipe.getResultingFluid()).getCommandString(),
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
