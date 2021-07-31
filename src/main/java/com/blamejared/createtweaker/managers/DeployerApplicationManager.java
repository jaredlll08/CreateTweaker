package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.DeployerApplicationManager")
public class DeployerApplicationManager implements IProcessingRecipeManager<DeployerApplicationRecipe> {
    
    @ZenCodeType.Method
    public ProcessingRecipeBuilder.ProcessingRecipeFactory factory() {
        
        return processingRecipeParams -> getSerializer().getFactory().create(processingRecipeParams);
    }
    
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient processedItem, IIngredient heldItem, MCWeightedItemStack[] outputs) {
        
        if(outputs.length > 2) {
            throw new IllegalArgumentException(String.format("Deployer recipe has more outputs (%s) than supported (2)!", outputs.length));
        }
        registerRecipe(name, recipeBuilder -> {
            recipeBuilder.require(processedItem.asVanillaIngredient());
            recipeBuilder.require(heldItem.asVanillaIngredient());
            for(MCWeightedItemStack stack : outputs) {
                recipeBuilder.output((float) stack.getWeight(), stack.getItemStack().getInternal());
            }
        });
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.DEPLOYING;
    }
    
}
