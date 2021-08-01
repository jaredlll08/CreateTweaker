package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("mods.create.MillingManager")
public class MillingManager implements IProcessingRecipeManager<MillingRecipe> {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MillingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<MillingRecipe>) AllRecipeTypes.MILLING.getSerializer())
                .getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(output)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getItemStack()
                        .getInternal(), (float) mcWeightedItemStack.getWeight()))
                .toArray(ProcessingOutput[]::new));
        
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        MillingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.MILLING;
    }
    
}
