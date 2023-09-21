package com.blamejared.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Optional;
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
                IItemStack.of(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem)).getCommandString(),
                IntStream.range(0, recipe.getHeight())
                        .mapToObj(y -> IntStream.range(0, recipe.getWidth())
                                .mapToObj(x -> ingredients.get(y * recipe.getWidth() + x))
                                .map(IIngredient::fromIngredient)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super MechanicalCraftingRecipe> manager, MechanicalCraftingRecipe firstRecipe, U secondRecipe) {
        
        return Services.PLATFORM.doCraftingTableRecipesConflict(manager, firstRecipe, secondRecipe);
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super MechanicalCraftingRecipe> manager, MechanicalCraftingRecipe recipe) {
        
        final List<IIngredient> ingredients = recipe.getIngredients().stream()
                .map(IIngredient::fromIngredient)
                .toList();
        final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
                .with(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D, Pair.of(recipe.getWidth(), recipe.getHeight()))
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, ingredients)
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem)))
                .with(BuiltinRecipeComponents.Metadata.MIRROR_AXIS, recipe.acceptsMirrored() ? MirrorAxis.HORIZONTAL : MirrorAxis.NONE)
                .build();
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<MechanicalCraftingRecipe> recompose(IRecipeManager<? super MechanicalCraftingRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        
        final String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
        final Pair<Integer, Integer> size = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
        final List<IIngredient> ingredients = recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        final boolean acceptsMirrored = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.MIRROR_AXIS)
                .isMirrored();
        
        final int width = size.getFirst();
        final int height = size.getSecond();
        
        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid shape size: bounds must be positive but got " + size);
        }
        if(width * height != ingredients.size()) {
            throw new IllegalArgumentException("Invalid shape size: incompatible with ingredients, got " + size + " with " + ingredients.size());
        }
        if(output.isEmpty()) {
            throw new IllegalArgumentException("Invalid output: empty item");
        }
        
        final NonNullList<Ingredient> recipeIngredients = ingredients.stream()
                .map(IIngredient::asVanillaIngredient)
                .collect(NonNullList::create, NonNullList::add, NonNullList::addAll);
        return Optional.of(new MechanicalCraftingRecipe(name, group, width, height, recipeIngredients, output.getInternal(), acceptsMirrored));
    }
    
}
