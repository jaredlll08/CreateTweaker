package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@ZenRegister
@Document("mods/createtweaker/recipe/SequencedAssemblyRecipeBuilder")
@NativeTypeRegistration(value = SequencedAssemblyRecipeBuilder.class, zenCodeName = "mods.createtweaker.SequencedAssemblyRecipeBuilder")
public class ExpandSequencedAssemblyRecipeBuilder {
    
    /**
     * Adds a step to the recipe.
     *
     * @param builder The recipe builder to allow configuration of the recipe.
     * @param <T>     The type of recipe to add a step for.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static <T extends ProcessingRecipe<?>> SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, Class<T> clazz, Function<ProcessingRecipeBuilder<T>, ProcessingRecipeBuilder<T>> builder) {
        
        try {
            return internal.addStep(CreateTweaker.getFactoryForClass(clazz), builder::apply);
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }
    
    /**
     * Adds a step to the recipe.
     *
     * @param <T> The type of recipe to add a step for.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static <T extends ProcessingRecipe<?>> SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, Class<T> clazz) {
        
        try {
            return internal.addStep(CreateTweaker.getFactoryForClass(clazz), UnaryOperator.identity());
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }
    
    /**
     * Sets that the recipe requires the given ingredient.
     *
     * @param ingredient The ingredient to require.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder require(SequencedAssemblyRecipeBuilder internal, IIngredient ingredient) {
        
        return internal.require(ingredient.asVanillaIngredient());
    }
    
    /**
     * Sets the transition item of the sequence.
     *
     * @param item The item to transition to.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder transitionTo(SequencedAssemblyRecipeBuilder internal, Item item) {
        
        return internal.transitionTo(item);
    }
    
    /**
     * Adds an output to the recipe.
     *
     * @param output The item output.
     * @param weight The weight of the output.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder addOutput(SequencedAssemblyRecipeBuilder internal, IItemStack output, float weight) {
        
        return internal.addOutput(output.getInternal(), weight);
    }
    
    /**
     * Sets the amount of loops the recipe has.
     *
     * @param loops The amount of loops the recipe has.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder loops(SequencedAssemblyRecipeBuilder internal, int loops) {
        
        return internal.loops(loops);
    }
    
}
