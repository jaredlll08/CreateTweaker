package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("mods.create.CrushingManager")
public class CrushingManager implements IRecipeManager {
    
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] output, IIngredient input) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CrushingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<CrushingRecipe>) AllRecipeTypes.CRUSHING.serializer).getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(output).map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getItemStack().getInternal(), (float) mcWeightedItemStack.getWeight())).toArray(ProcessingOutput[]::new));
        
        builder.require(input.asVanillaIngredient());
        
        CrushingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public IRecipeType<CrushingRecipe> getRecipeType() {
        
        return AllRecipeTypes.CRUSHING.getType();
    }
    
}
