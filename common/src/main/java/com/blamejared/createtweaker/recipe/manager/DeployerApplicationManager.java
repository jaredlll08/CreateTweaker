package com.blamejared.createtweaker.recipe.manager;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:deploying>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.DeployerApplicationManager")
@Document("mods/CreateTweaker/DeployerApplicationManager")
public class DeployerApplicationManager implements IProcessingRecipeManager<DeployerApplicationRecipe> {
    
    /**
     * Adds a new deployer application recipe.
     *
     * @param name          The name of the recipe
     * @param processedItem The item to be deployed onto
     * @param heldItem      The item to deploy with
     * @param outputs       The output of the recipe
     * @param keepHeldItem  Should the held item be consumed
     *
     * @docParam name "name"
     * @docParam processedItem <item:minecraft:air>
     * @docParam heldItem <item:minecraft:diamond>
     * @docParam outputs [<item:minecraft:dirt> % 50]
     * @docParam keepHeldItem true
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient processedItem, IIngredient heldItem, Percentaged<IItemStack>[] outputs, @ZenCodeType.OptionalBoolean boolean keepHeldItem) {
        
        if(outputs.length > 2) {
            throw new IllegalArgumentException(String.format("Deployer recipe has more outputs (%s) than supported (2)!", outputs.length));
        }
        registerRecipe(name, recipeBuilder -> {
            recipeBuilder.require(processedItem.asVanillaIngredient());
            recipeBuilder.require(heldItem.asVanillaIngredient());
            if(keepHeldItem) {
                recipeBuilder.toolNotConsumed();
            }
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
