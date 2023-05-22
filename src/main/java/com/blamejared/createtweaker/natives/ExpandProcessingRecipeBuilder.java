package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@Document("mods/createtweaker/recipe/ProcessingRecipeBuilder")
@NativeTypeRegistration(value = ProcessingRecipeBuilder.class, zenCodeName = "mods.createtweaker.ProcessingRecipeBuilder")
public class ExpandProcessingRecipeBuilder {
    
    /**
     * Sets the item ingredients of the recipe.
     *
     * @param ingredients The item ingredients of the recipe.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> withItemIngredients(ProcessingRecipeBuilder internal, IIngredient... ingredients) {
        
        return internal.withItemIngredients(Arrays.stream(ingredients)
                .map(IIngredient::asVanillaIngredient)
                .toArray(Ingredient[]::new));
    }
    
    /**
     * Sets the single item output of the recipe.
     *
     * @param output The single item output of this recipe.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> withSingleItemOutput(ProcessingRecipeBuilder internal, IItemStack output) {
        
        return internal.withSingleItemOutput(output.getInternal());
    }
    
    /**
     * Sets the item outputs of the recipe.
     *
     * @param outputs The outputs of the recipe.
     *
     * @return This builder for further chaining.
     */
    @SafeVarargs
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> withItemOutputs(ProcessingRecipeBuilder internal, Percentaged<IItemStack>... outputs) {
        
        return internal.withItemOutputs(Arrays.stream(outputs)
                .map(stack -> new ProcessingOutput(stack.getData()
                        .getInternal(), (float) stack.getPercentage()))
                .toArray(ProcessingOutput[]::new));
    }
    
    /**
     * Sets the fluid ingredients of the recipe.
     *
     * @param ingredients The fluid ingredients of the recipe.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> withFluidIngredients(ProcessingRecipeBuilder internal, CTFluidIngredient... ingredients) {
        
        
        FluidIngredient[] fluidIngredients = Arrays.stream(ingredients)
                .map(CreateTweaker::mapFluidIngredients)
                .toArray(FluidIngredient[]::new);
        
        
        return internal.withFluidIngredients(fluidIngredients);
    }
    
    /**
     * Sets the fluid outputs of the recipe.
     *
     * @param outputs The fluid outputs of the recipe.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> withFluidOutputs(ProcessingRecipeBuilder internal, IFluidStack... outputs) {
        
        return internal.withFluidOutputs(Arrays.stream(outputs)
                .map(IFluidStack::getInternal)
                .toArray(FluidStack[]::new));
    }
    
    /**
     * Sets the duration of the recipe in ticks.
     *
     * @param ticks The duration of the recipe in ticks.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> duration(ProcessingRecipeBuilder internal, int ticks) {
        
        return internal.duration(ticks);
    }
    
    /**
     * Sets the duration of the recipe to the average processing duration, which is 100 ticks.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> averageProcessingDuration(ProcessingRecipeBuilder internal) {
        
        return internal.averageProcessingDuration();
    }
    
    /**
     * Sets the recipe to require the specific {@link HeatCondition}.
     *
     * @param condition The heat condition of the recipe.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> requiresHeat(ProcessingRecipeBuilder internal, HeatCondition condition) {
        
        return internal.requiresHeat(condition);
    }
    
    /**
     * Sets the recipe to require the given item ingredient.
     *
     * This can be chained multiple times for multiple ingredients.
     *
     * @param ingredient The item ingredient to require.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> require(ProcessingRecipeBuilder internal, IIngredient ingredient) {
        
        return internal.require(ingredient.asVanillaIngredient());
    }
    
    
    /**
     * Sets the recipe to require the given fluid ingredient.
     *
     * This can be chained multiple times for multiple ingredients.
     *
     * @param ingredient The fluid ingredient to require.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> require(ProcessingRecipeBuilder internal, CTFluidIngredient ingredient) {
        
        return internal.require(CreateTweaker.mapFluidIngredients(ingredient));
    }
    
    /**
     * Sets the recipe to output the given {@link Percentaged} item.
     *
     * @param item The item to output.
     *
     * @return This builder for further chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> output(ProcessingRecipeBuilder internal, Percentaged<IItemStack> item) {
        
        return internal.output((float) item.getPercentage(), item.getData()
                .getInternal());
    }
    
    /**
     * Sets the recipe to output the given {@link IFluidStack}.
     *
     * @param fluidStack The fluid to output.
     *
     * @return This builder for futher chaining.
     */
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder<ProcessingRecipe<Container>> output(ProcessingRecipeBuilder internal, IFluidStack fluidStack) {
        
        return internal.output(fluidStack.getInternal());
    }
    
    
}
