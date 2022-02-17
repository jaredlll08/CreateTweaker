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
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * @docParam this <recipetype:create:crushing>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.CrushingManager")
@Document("mods/createtweaker/CrushingManager")
public class CrushingManager implements IProcessingRecipeManager<CrushingRecipe> {
    
    /**
     * Adds a recipe to the Crusher.
     *
     * @param name     The name of the recipe.
     * @param output   The Percentaged IItemStack outputs.
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe in ticks (defaults to 100).
     *
     * @docParam name "crushed"
     * @docParam output [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> % 12) * 2]
     * @docParam input <item:minecraft:glass>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CrushingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(output)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getData()
                        .getInternal(), (float) mcWeightedItemStack.getPercentage()))
                .toArray(ProcessingOutput[]::new));
        
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        CrushingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.CRUSHING;
    }
    
}
