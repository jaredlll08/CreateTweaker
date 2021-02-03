package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.create.EmptyingManager")
public class EmptyingManager implements IRecipeManager {
    
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack outputItem, IFluidStack outputFluid, IIngredient inputContainer, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<EmptyingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<EmptyingRecipe>) AllRecipeTypes.EMPTYING.serializer).getFactory(), resourceLocation);
        builder.output(outputItem.getInternal()).output(outputFluid.getInternal());
        builder.require(inputContainer.asVanillaIngredient());
    
        builder.duration(duration);
        EmptyingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IFluidStack output) {
        
        CraftTweakerAPI.apply(new ActionRecipeBase(this) {
            @Override
            public void apply() {
                
                List<ResourceLocation> toRemove = new ArrayList<>();
                for(ResourceLocation location : getManager().getRecipes().keySet()) {
                    IRecipe<?> iRecipe = getManager().getRecipes().get(location);
                    if(iRecipe instanceof EmptyingRecipe) {
                        EmptyingRecipe recipe = (EmptyingRecipe) iRecipe;
                        if(recipe.getFluidResults().isEmpty()) {
                            continue;
                        }
                        if(output.getInternal().isFluidEqual(recipe.getResultingFluid())) {
                            toRemove.add(location);
                        }
                    }
                }
                toRemove.forEach(getManager().getRecipes()::remove);
                
            }
            
            @Override
            public String describe() {
                
                return "Removing \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                        .getRecipeType()) + "\" recipes with output: " + output + "\"";
            }
        });
    }
    
    @Override
    public IRecipeType<EmptyingRecipe> getRecipeType() {
        
        return AllRecipeTypes.EMPTYING.getType();
    }
    
}
