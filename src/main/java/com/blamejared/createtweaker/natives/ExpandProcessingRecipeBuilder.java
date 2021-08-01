package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@Document("mods/createtweaker/ProcessingRecipeBuilder")
@NativeTypeRegistration(value = ProcessingRecipeBuilder.class, zenCodeName = "mods.createtweaker.ProcessingRecipeBuilder")
public class ExpandProcessingRecipeBuilder {
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withItemIngredients(ProcessingRecipeBuilder internal, IIngredient... ingredients) {
        
        return internal.withItemIngredients(Arrays.stream(ingredients)
                .map(IIngredient::asVanillaIngredient)
                .toArray(Ingredient[]::new));
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withSingleItemOutput(ProcessingRecipeBuilder internal, IItemStack output) {
        
        return internal.withSingleItemOutput(output.getInternal());
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withItemOutputs(ProcessingRecipeBuilder internal, MCWeightedItemStack... outputs) {
        
        return internal.withItemOutputs(Arrays.stream(outputs)
                .map(stack -> new ProcessingOutput(stack.getItemStack()
                        .getInternal(), (float) stack.getWeight()))
                .toArray(ProcessingOutput[]::new));
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withFluidIngredients(ProcessingRecipeBuilder internal, CTFluidIngredient... ingredients) {
        
        
        FluidIngredient[] fluidIngredients = Arrays.stream(ingredients)
                .map(CreateTweaker::mapFluidIngredients)
                .toArray(FluidIngredient[]::new);
        
        
        return internal.withFluidIngredients(fluidIngredients);
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withFluidOutputs(ProcessingRecipeBuilder internal, MCFluidStack... outputs) {
        
        return internal.withFluidOutputs(Arrays.stream(outputs)
                .map(MCFluidStack::getInternal)
                .toArray(FluidStack[]::new));
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder duration(ProcessingRecipeBuilder internal, int ticks) {
        
        return internal.duration(ticks);
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder averageProcessingDuration(ProcessingRecipeBuilder internal) {
        
        return internal.averageProcessingDuration();
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder requiresHeat(ProcessingRecipeBuilder internal, HeatCondition condition) {
        
        return internal.requiresHeat(condition);
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder require(ProcessingRecipeBuilder internal, IIngredient ingredient) {
        
        return internal.require(ingredient.asVanillaIngredient());
    }
    
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder require(ProcessingRecipeBuilder internal, CTFluidIngredient ingredient) {
        
        return internal.require(CreateTweaker.mapFluidIngredients(ingredient));
    }
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder output(ProcessingRecipeBuilder internal, MCWeightedItemStack item) {
        
        return internal.output((float) item.getWeight(), item.getItemStack()
                .getInternal());
    }
    
    
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder output(ProcessingRecipeBuilder internal, MCFluidStack fluidStack) {
        
        return internal.output(fluidStack.getInternal());
    }
    
    
}
