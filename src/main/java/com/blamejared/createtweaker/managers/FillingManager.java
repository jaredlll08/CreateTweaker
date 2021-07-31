package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.FillingManager")
public class FillingManager implements IProcessingRecipeManager<FillingRecipe> {
    
    @ZenCodeType.Method
    public ProcessingRecipeBuilder.ProcessingRecipeFactory factory() {
        
        return processingRecipeParams -> getSerializer().getFactory().create(processingRecipeParams);
    }
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient inputContainer, IFluidStack inputFluid, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<FillingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<FillingRecipe>) AllRecipeTypes.FILLING.serializer)
                .getFactory(), resourceLocation);
        builder.output(output.getInternal());
        builder.require(inputContainer.asVanillaIngredient())
                .require(FluidIngredient.fromFluidStack(inputFluid.getInternal()));
        
        builder.duration(duration);
        FillingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.FILLING;
    }
    
}
