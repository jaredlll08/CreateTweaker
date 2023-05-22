package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For(SandPaperPolishingRecipe.class)
public class SandPaperPolishingRecipeHandler implements IRecipeHandler<SandPaperPolishingRecipe> {
    
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
    public Optional<Function<ResourceLocation, SandPaperPolishingRecipe>> replaceIngredients(IRecipeManager manager, SandPaperPolishingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<SandPaperPolishingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<SandPaperPolishingRecipe>) AllRecipeTypes.SANDPAPER_POLISHING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults().toArray(ProcessingOutput[]::new));
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
    }
    
}
