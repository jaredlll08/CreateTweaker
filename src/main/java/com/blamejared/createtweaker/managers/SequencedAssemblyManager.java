package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.google.gson.JsonParseException;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("mods.create.SequencedAssemblyManager")
public class SequencedAssemblyManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public SequencedAssemblyRecipeBuilder builder(String name) {
        
        return new SequencedAssemblyRecipeBuilder(new ResourceLocation("crafttweaker", name));
    }
    
    @ZenCodeType.Method
    public void registerRecipe(String name, Consumer<SequencedAssemblyRecipeBuilder> recipeBuilder) {
        
        name = fixRecipeName(name);
        ResourceLocation recipeId = new ResourceLocation("crafttweaker", name);
        SequencedAssemblyRecipeBuilder t = new SequencedAssemblyRecipeBuilder(recipeId);
        recipeBuilder.accept(t);
        SequencedAssemblyRecipe recipe = t.build();
        precheck(recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }
    
    
    @ZenCodeType.Method
    public void addRecipe(SequencedAssemblyRecipeBuilder builder) {
        
        SequencedAssemblyRecipe recipe = builder.build();
        precheck(recipe);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }
    
    private void precheck(SequencedAssemblyRecipe recipe) {
        
        if(recipe.getTransitionalItem().isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `transitionTo` item is not provided or is air! transitionTo: " + recipe.getTransitionalItem());
        }
        try {
            if(recipe.getResultItem().isEmpty()) {
                throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
            }
        } catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
        }
        if(recipe.getIngredient() == null) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `inputs` is not provided! inputs: " + recipe.getIngredient());
        }
        if(recipe.getSequence().isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! No Steps have been provided!");
        }
    }
    
    @Override
    public IRecipeType<SequencedAssemblyRecipe> getRecipeType() {
        
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getType();
    }
    
    
    public SequencedAssemblyRecipeSerializer getSerializer() {
        
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getSerializer();
    }
    
}
