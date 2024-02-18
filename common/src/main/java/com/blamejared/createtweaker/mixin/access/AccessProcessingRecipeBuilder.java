package com.blamejared.createtweaker.mixin.access;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ProcessingRecipeBuilder.class, remap = false)
public interface AccessProcessingRecipeBuilder {
    
    @Accessor(remap = false, value = "params")
    ProcessingRecipeBuilder.ProcessingRecipeParams createtweaker$getParams();
    
}
