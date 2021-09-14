package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.content.contraptions.components.crafter.MechanicalCraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@IRecipeHandler.For(MechanicalCraftingRecipe.class)
public class MechanicalCraftingRecipeHandler implements IRecipeHandler<MechanicalCraftingRecipe> {
    
    @Override
    public String dumpToCommandString(IRecipeManager iRecipeManager, MechanicalCraftingRecipe recipe) {
    
        final NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return String.format(
                "<recipetype:create:mechanical_crafting>.addRecipe(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getResultItem()).getCommandString(),
                IntStream.range(0, recipe.getRecipeHeight())
                        .mapToObj(y -> IntStream.range(0, recipe.getRecipeWidth())
                                .mapToObj(x -> ingredients.get(y * recipe.getRecipeWidth() + x))
                                .map(IIngredient::fromIngredient)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, MechanicalCraftingRecipe>> replaceIngredients(IRecipeManager manager, MechanicalCraftingRecipe recipe, List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new MechanicalCraftingRecipe(id, recipe.getGroup(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), newIngredients, recipe.getResultItem(), recipe.acceptsMirrored())
        );
        
    }
    
}
