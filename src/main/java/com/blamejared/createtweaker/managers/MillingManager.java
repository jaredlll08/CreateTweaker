package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * @docParam this <recipetype:create:milling>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.MillingManager")
@Document("mods/createtweaker/MillingManager")
public class MillingManager implements IProcessingRecipeManager<MillingRecipe> {
    
    /**
     * Adds a milling recipe.
     *
     * @param name     The name of the recipe.
     * @param outputs  The outputs of the recipe
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe (default 100 ticks).
     *
     * @docParam name "milled"
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> * 2) % 12]
     * @docParam input <item:minecraft:dirt>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MillingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(outputs)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getData()
                        .getInternal(), (float) mcWeightedItemStack.getPercentage()))
                .toArray(ProcessingOutput[]::new));
        
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        MillingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.MILLING;
    }
    
}
