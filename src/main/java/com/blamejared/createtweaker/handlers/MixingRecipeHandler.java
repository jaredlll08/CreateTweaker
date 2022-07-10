package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.CreateTweaker;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@IRecipeHandler.For(MixingRecipe.class)
public class MixingRecipeHandler implements IRecipeHandler<MixingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, MixingRecipe recipe) {
        
        Either<Stream<Percentaged<IItemStack>>, IFluidStack> result;
        
        if(!recipe.getFluidResults().isEmpty()) {
            result = Either.right(new MCFluidStack(recipe.getFluidResults().get(0)));
        } else {
            result = Either.left(recipe.getRollableResults()
                    .stream()
                    .map(CreateTweaker::mapProcessingResult));
        }
        
        return String.format("<recipetype:create:mixing>.addRecipe(\"%s\", <constant:create:heat_condition:%s>, [%s], [%s], [%s], [%s]);",
                recipe.getId(),
                recipe.getRequiredHeat().name().toLowerCase(Locale.ENGLISH),
                result.map(results -> results.map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")), IFluidStack::getCommandString),
                recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ")),
                recipe.getFluidIngredients()
                        .stream()
                        .flatMap(fluidIngredient -> fluidIngredient.getMatchingFluidStacks().stream())
                        .map(fluidStack -> new MCFluidStack(fluidStack).getCommandString())
                        .collect(Collectors.joining(", ")),
                recipe.getFluidResults()
                        .stream()
                        .map(MCFluidStack::new)
                        .map(MCFluidStack::getCommandString)
                        .collect(Collectors.joining(", "))
        );
        
    }
    
    @Override
    public Optional<Function<ResourceLocation, MixingRecipe>> replaceIngredients(IRecipeManager manager, MixingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> {
                    ProcessingRecipeBuilder<MixingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<MixingRecipe>) AllRecipeTypes.MIXING.getSerializer()).getFactory(), id);
                    builder.withItemOutputs(recipe.getRollableResults().toArray(ProcessingOutput[]::new));
                    builder.withItemIngredients(newIngredients);
                    builder.withFluidIngredients(recipe.getFluidIngredients());
                    builder.requiresHeat(recipe.getRequiredHeat());
                    builder.duration(recipe.getProcessingDuration());
                    return builder.build();
                });
        
    }
    
}
