package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For(FillingRecipe.class)
public class FillingRecipeHandler implements IRecipeHandler<FillingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, FillingRecipe recipe) {
        
        return String.format("<recipetype:create:filling>.addRecipe(\"%s\", %s, %s, %s);",
                recipe.getId(),
                new MCItemStackMutable(recipe.getResultItem()).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString(),
                new MCFluidStackMutable(recipe.getFluidIngredients()
                        .isEmpty() ? FluidStack.EMPTY : recipe.getRequiredFluid()
                        .getMatchingFluidStacks()
                        .get(0)).getCommandString()
        );
        
    }
    
    @Override
    public Optional<Function<ResourceLocation, FillingRecipe>> replaceIngredients(IRecipeManager manager, FillingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<FillingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<FillingRecipe>) AllRecipeTypes.FILLING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults());
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
