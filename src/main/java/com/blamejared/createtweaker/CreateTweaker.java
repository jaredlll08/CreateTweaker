package com.blamejared.createtweaker;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraftforge.fml.common.Mod;

@Mod("createtweaker")
public class CreateTweaker {
    
    public CreateTweaker() {
        
    }
    
    public static FluidIngredient mapFluidIngredients(CTFluidIngredient ingredient) {
        
        return ingredient
                .mapTo(FluidIngredient::fromFluidStack, (fluidITag, integer) -> FluidIngredient
                        .fromTag((ITag.INamedTag<Fluid>) fluidITag, integer), stream -> {
                    throw new IllegalArgumentException("Unable to use a compound ingredient for Create!");
                });
    }
    
    
    public static MCWeightedItemStack mapMutableProcessingResult(ProcessingOutput result) {
        
        return new MCWeightedItemStack(new MCItemStackMutable(result.getStack()), result.getChance());
    }
    
}
