package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For(EmptyingRecipe.class)
public class EmptyingRecipeHandler implements IRecipeHandler<EmptyingRecipe> {
    
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
    public Optional<Function<ResourceLocation, EmptyingRecipe>> replaceIngredients(IRecipeManager manager, EmptyingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<EmptyingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<EmptyingRecipe>) AllRecipeTypes.EMPTYING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults().toArray(ProcessingOutput[]::new));
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
