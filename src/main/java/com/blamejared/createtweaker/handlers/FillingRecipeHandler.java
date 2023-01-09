package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

@IRecipeHandler.For(FillingRecipe.class)
public class FillingRecipeHandler implements IProcessingRecipeHandler<FillingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, FillingRecipe recipe) {
        
        return String.format("<recipetype:create:filling>.addRecipe(\"%s\", %s, %s, %s);",
                recipe.getId(),
                CreateTweaker.mapProcessingResult(recipe.getRollableResults().get(0)).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0))
                        .getCommandString(),
                new MCFluidStack(recipe.getFluidIngredients()
                        .isEmpty() ? FluidStack.EMPTY : recipe.getRequiredFluid()
                        .getMatchingFluidStacks()
                        .get(0)).getCommandString()
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
