package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
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
                new MCItemStackMutable(recipe.getResultItem()).getCommandString(),
                new MCFluidStackMutable(recipe.getFluidResults()
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
                    builder.withItemOutputs(recipe.getRollableResults());
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
