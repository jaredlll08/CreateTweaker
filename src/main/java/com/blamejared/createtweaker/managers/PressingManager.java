package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("mods.create.PressingManager")
public class PressingManager implements IRecipeManager {
    
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<PressingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<PressingRecipe>) AllRecipeTypes.PRESSING.serializer).getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(output).map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getItemStack().getInternal(), (float) mcWeightedItemStack.getWeight())).toArray(ProcessingOutput[]::new));
        
        builder.require(input.asVanillaIngredient());
    
        builder.duration(duration);
        PressingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public IRecipeType<PressingRecipe> getRecipeType() {
        
        return AllRecipeTypes.PRESSING.getType();
    }
    
}
