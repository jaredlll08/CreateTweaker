package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.SandPaperPolishingManager")
public class SandPaperPolishingManager implements IProcessingRecipeManager<SandPaperPolishingRecipe> {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<SandPaperPolishingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<SandPaperPolishingRecipe>) AllRecipeTypes.SANDPAPER_POLISHING.getSerializer())
                .getFactory(), resourceLocation);
        builder.output((float) output.getWeight(), output.getItemStack()
                .getInternal());
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        SandPaperPolishingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.SANDPAPER_POLISHING;
    }
    
}
