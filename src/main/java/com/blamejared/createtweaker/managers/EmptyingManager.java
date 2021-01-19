package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.EmptyingManager")
public class EmptyingManager implements IRecipeManager {
    
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack outputItem, IFluidStack outputFluid, IIngredient inputContainer) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<EmptyingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<EmptyingRecipe>) AllRecipeTypes.EMPTYING.serializer).getFactory(), resourceLocation);
        builder.output(outputItem.getInternal()).output(outputFluid.getInternal());
        builder.require(inputContainer.asVanillaIngredient());
    
        EmptyingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public IRecipeType<EmptyingRecipe> getRecipeType() {
        
        return AllRecipeTypes.EMPTYING.getType();
    }
    
}
