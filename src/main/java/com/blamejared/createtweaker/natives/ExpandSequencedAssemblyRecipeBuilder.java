package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@ZenRegister
@Document("mods/createtweaker/SequencedAssemblyRecipeBuilder")
@NativeTypeRegistration(value = SequencedAssemblyRecipeBuilder.class, zenCodeName = "mods.createtweaker.SequencedAssemblyRecipeBuilder")
public class ExpandSequencedAssemblyRecipeBuilder {
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, ProcessingRecipeBuilder.ProcessingRecipeFactory factory, Function<ProcessingRecipeBuilder, ProcessingRecipeBuilder> builder) {
        
        try {
            return internal.addStep(factory, builder::apply);
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, ProcessingRecipeBuilder.ProcessingRecipeFactory factory) {
        
        try {
            return internal.addStep(factory, UnaryOperator.identity());
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder require(SequencedAssemblyRecipeBuilder internal, IIngredient ingredient) {
        
        return internal.require(ingredient.asVanillaIngredient());
    }
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder transitionTo(SequencedAssemblyRecipeBuilder internal, Item item) {
        
        return internal.transitionTo(item);
    }
    
    @ZenCodeType.Method // Could be made @OptionalFloat, but I just want to be safe
    public static SequencedAssemblyRecipeBuilder addOutput(SequencedAssemblyRecipeBuilder internal, IItemStack output) {
        
        return internal.addOutput(output.getInternal(), 1.0f);
    }
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder addOutput(SequencedAssemblyRecipeBuilder internal, IItemStack output, float weight) {
        
        return internal.addOutput(output.getInternal(), weight);
    }
    
    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder loops(SequencedAssemblyRecipeBuilder internal, int loops) {
        
        return internal.loops(loops);
    }
    
}
