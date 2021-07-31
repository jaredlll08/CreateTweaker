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
        t.build(iFinishedRecipe -> {
            try {
                SequencedAssemblyRecipe recipe = getSerializer().read(new ResourceLocation("crafttweaker", iFinishedRecipe
                        .getID()
                        .getPath()), iFinishedRecipe.getRecipeJson());
                if(recipe.getSequence().isEmpty()) {
                    throw new IllegalArgumentException("Cannot add Sequenced Assembly Recipe with no steps!");
                }
                
                CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
            } catch(JsonParseException e) {
                throw new RuntimeException("Error while adding Sequenced Assembly Recipe! This is most likely caused by using in unsupported recipe type in a step!", e);
            } catch(Exception e) {
                throw new RuntimeException("Error while adding Sequenced Assembly Recipe! Make sure that `transitionTo`, `outputs` and `inputs` is set!", e);
            }
        });
    }
    
    
    @ZenCodeType.Method
    public void addRecipe(SequencedAssemblyRecipeBuilder builder) {
        
        builder.build(iFinishedRecipe -> {
            try {
                SequencedAssemblyRecipe recipe = getSerializer().read(new ResourceLocation("crafttweaker", iFinishedRecipe
                        .getID()
                        .getPath()), iFinishedRecipe.getRecipeJson());
                if(recipe.getSequence().isEmpty()) {
                    throw new IllegalArgumentException("Cannot add Sequenced Assembly Recipe with no steps!");
                }
                
                CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
            } catch(JsonParseException e) {
                throw new RuntimeException("Error while adding Sequenced Assembly Recipe! This is most likely caused by using in unsupported recipe type in a step!", e);
            } catch(Exception e) {
                throw new RuntimeException("Error while adding Sequenced Assembly Recipe! Make sure that `transitionTo`, `outputs` and `inputs` is set!", e);
            }
        });
    }
    
    @Override
    public IRecipeType<SequencedAssemblyRecipe> getRecipeType() {
        
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getType();
    }
    
    
    public SequencedAssemblyRecipeSerializer getSerializer() {
        
        return (SequencedAssemblyRecipeSerializer) AllRecipeTypes.SEQUENCED_ASSEMBLY.serializer;
    }
    
}
