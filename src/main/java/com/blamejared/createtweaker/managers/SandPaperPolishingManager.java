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
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:sandpaper_polishing>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.SandPaperPolishingManager")
@Document("mods/createtweaker/SandPaperPolishingManager")
public class SandPaperPolishingManager implements IProcessingRecipeManager<SandPaperPolishingRecipe> {
    
    /**
     * Adds a sand paper polishing recipe.
     *
     * @param name     The name of the recipe.
     * @param output   The output of the recipe.
     * @param input    The input of the recipe.
     * @param duration The duration of the recipe (default 100 ticks).
     *
     * @docParam name "polished"
     * @docParam output <item:minecraft:diamond> % 50
     * @docParam input <item:minecraft:dirt> * 5
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<SandPaperPolishingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.output((float) output.getPercentage(), output.getData().getInternal());
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        SandPaperPolishingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.SANDPAPER_POLISHING;
    }
    
}
