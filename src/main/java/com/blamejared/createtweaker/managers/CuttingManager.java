package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:cutting>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.CuttingManager")
public class CuttingManager implements IProcessingRecipeManager<CuttingRecipe> {
    
    /**
     * Adds a recipe to the Cutter.
     *
     * @param name     The name of the recipe.
     * @param output   The Percentaged IItemStack output.
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe in ticks (defaults to 100).
     *
     * @docParam name "crushed"
     * @docParam output <item:minecraft:diamond> % 50
     * @docParam input <item:minecraft:glass>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        addRecipe(name, new Percentaged[] {output}, input, duration);
    }
    
    /**
     * Adds a recipe to the Cutter.
     *
     * @param name     The name of the recipe.
     * @param outputs  The Percentaged IItemStack outputs.
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe in ticks (defaults to 100).
     *
     * @docParam name "crushed"
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> % 12) * 2]
     * @docParam input <item:minecraft:glass>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CuttingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        for(Percentaged<IItemStack> mcWeightedItemStack : outputs) {
            builder.output((float) mcWeightedItemStack.getPercentage(), mcWeightedItemStack.getData()
                    .getInternal());
        }
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        CuttingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.CUTTING;
    }
    
}
