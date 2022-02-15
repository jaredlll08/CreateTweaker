package com.blamejared.createtweaker.managers;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:deploying>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.DeployerApplicationManager")
@Document("mods/createtweaker/DeployerApplicationManager")
public class DeployerApplicationManager implements IProcessingRecipeManager<DeployerApplicationRecipe> {
    
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient processedItem, IIngredient heldItem, Percentaged<IItemStack>[] outputs) {
        
        if(outputs.length > 2) {
            throw new IllegalArgumentException(String.format("Deployer recipe has more outputs (%s) than supported (2)!", outputs.length));
        }
        registerRecipe(name, recipeBuilder -> {
            recipeBuilder.require(processedItem.asVanillaIngredient());
            recipeBuilder.require(heldItem.asVanillaIngredient());
            for(Percentaged<IItemStack> stack : outputs) {
                recipeBuilder.output((float) stack.getPercentage(), stack.getData()
                        .getInternal());
            }
        });
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.DEPLOYING;
    }
    
}
