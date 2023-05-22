package com.blamejared.createtweaker.managers.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * @docParam this <recipetype:create:compacting>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.IProcessingRecipeManager")
@Document("mods/createtweaker/IProcessingRecipeManager")
public interface IProcessingRecipeManager<T extends ProcessingRecipe<?>> extends IRecipeManager<T> {

    default ProcessingRecipeSerializer<T> getSerializer() {

        return getCreateRecipeType().getSerializer();
    }

    /**
     * Registers a recipe using a builder approach.
     *
     * @param name          The name of the recipe.
     * @param recipeBuilder The recipe builder.
     */
    @ZenCodeType.Method
    default void registerRecipe(String name, Consumer<ProcessingRecipeBuilder<T>> recipeBuilder) {

        name = fixRecipeName(name);
        ResourceLocation recipeId = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<T> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), recipeId);
        recipeBuilder.accept(builder);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, builder.build(), ""));
    }


    AllRecipeTypes getCreateRecipeType();

    @Override
    default RecipeType<T> getRecipeType() {

        return getCreateRecipeType().getType();
    }


}
