package com.blamejared.createtweaker.managers;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:item_application>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.ItemApplicationManager")
@Document("mods/createtweaker/ItemApplicationManager")
public class ItemApplicationManager implements IProcessingRecipeManager<ItemApplicationRecipe> {
    
    /**
     * Adds a new item application recipe.
     *
     * @param name         The name of the recipe.
     * @param outputs      The recipe outputs
     * @param block        The block to be applied on
     * @param heldItem     The item that needs to be held
     * @param keepHeldItem Should the item be consumed or not
     *
     * @docParam name "name"
     * @docParam outputs [<item:minecraft:dirt> % 50, <item:minecraft:diamond>]
     * @docParam block <item:minecraft:diamond_block>
     * @docParam heldItem <item:minecraft:emerald>
     * @docParam keepHeldItem true
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient block, IIngredient heldItem, @ZenCodeType.OptionalBoolean boolean keepHeldItem) {
        
        registerRecipe(name, recipeBuilder -> {
            recipeBuilder.require(block.asVanillaIngredient());
            recipeBuilder.require(heldItem.asVanillaIngredient());
            if(keepHeldItem) {
                recipeBuilder.toolNotConsumed();
            }
            for(Percentaged<IItemStack> mcWeightedItemStack : outputs) {
                recipeBuilder.output((float) mcWeightedItemStack.getPercentage(), mcWeightedItemStack.getData()
                        .getInternal());
            }
        });
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.ITEM_APPLICATION;
    }
    
}
