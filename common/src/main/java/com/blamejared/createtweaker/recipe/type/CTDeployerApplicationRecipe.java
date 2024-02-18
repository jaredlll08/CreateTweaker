package com.blamejared.createtweaker.recipe.type;

import com.blamejared.createtweaker.mixin.access.AccessProcessingRecipeBuilder;
import com.blamejared.createtweaker.recipe.fun.DeployerRecipeFunction;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class CTDeployerApplicationRecipe extends DeployerApplicationRecipe {
    
    private final DeployerApplicationRecipe internal;
    private final DeployerRecipeFunction function;
    
    public CTDeployerApplicationRecipe(ProcessingRecipeBuilder<DeployerApplicationRecipe> internalBuilder, DeployerRecipeFunction function) {
        
        super(((AccessProcessingRecipeBuilder) internalBuilder).createtweaker$getParams());
        this.internal = internalBuilder.build();
        this.function = function;
    }
    
    @Override
    public ResourceLocation getId() {
        
        return internal.getId();
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        // We shouldn't need our own serializer, none of our stuff is serializable
        return internal.getSerializer();
    }
    
    @Override
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        
        return super.rollResults(function.process(rollableResults));
    }
    
}
