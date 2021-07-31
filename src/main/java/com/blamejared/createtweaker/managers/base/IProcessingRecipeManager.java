package com.blamejared.createtweaker.managers.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("mods.create.ICreateRecipeManager")
public interface IProcessingRecipeManager<T extends ProcessingRecipe<?>> extends IRecipeManager {
    
    
    default ProcessingRecipeSerializer<T> getSerializer() {
        
        return ((ProcessingRecipeSerializer<T>) getCreateRecipeType().serializer);
    }
    
    @ZenCodeType.Method
    default void registerRecipe(String name, Consumer<ProcessingRecipeBuilder<T>> recipeBuilder) {
        
        name = fixRecipeName(name);
        ResourceLocation recipeId = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<T> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), recipeId);
        recipeBuilder.accept(builder);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, builder.build(), ""));
    }
    
    AllRecipeTypes getCreateRecipeType();
    
    @Override
    default IRecipeType<T> getRecipeType() {
        
        return getCreateRecipeType().getType();
    }
    
    
}
