package com.blamejared.createtweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.simibubi.create.content.contraptions.components.crafter.MechanicalCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

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
                StringUtil.quoteAndEscape(recipe.getId()),
                new MCItemStack(recipe.getResultItem()).getCommandString(),
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
