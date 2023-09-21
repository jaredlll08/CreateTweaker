package com.blamejared.createtweaker;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.service.Services;
import com.google.common.base.Suppliers;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CreateTweakerHelper {
    
    public static final Supplier<Map<Class<?>, ProcessingRecipeBuilder.ProcessingRecipeFactory<?>>> CLASS_TO_FACTORY = Suppliers.memoize(() -> {
        HashMap<Class<?>, ProcessingRecipeBuilder.ProcessingRecipeFactory<?>> map = new HashMap<>();
        map.put(CuttingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.CUTTING.getSerializer()).getFactory());
        map.put(DeployerApplicationRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.DEPLOYING.getSerializer()).getFactory());
        map.put(FillingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.FILLING.getSerializer()).getFactory());
        map.put(PressingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.PRESSING.getSerializer()).getFactory());
        return map;
    });
    
    public static FluidIngredient mapFluidIngredients(CTFluidIngredient ingredient) {
        
        return ingredient.mapTo(Services.PLATFORM::fromFluidStack, Services.PLATFORM::fromTag, stream -> {
            throw new IllegalArgumentException("Unable to use a compound ingredient for Create!");
        });
    }
    
    public static CTFluidIngredient mapFluidIngredientsToCT(FluidIngredient ingredient) {
        
        return Services.PLATFORM.mapFluidIngredientsToCT(ingredient);
    }
    
    public static Percentaged<IItemStack> mapProcessingResult(ProcessingOutput result) {
        
        return IItemStack.of(result.getStack()).percent(result.getChance() * 100);
    }
    
    public static ProcessingOutput mapPercentagedToProcessingOutput(Percentaged<IItemStack> stack) {
        
        return new ProcessingOutput(stack.getData().getInternal(), (float) stack.getPercentage());
    }
    
    public static <T extends ProcessingRecipe<?>> ProcessingRecipeBuilder.ProcessingRecipeFactory<T> getFactoryForClass(Class<T> clazz) {
        
        if(!CLASS_TO_FACTORY.get().containsKey(clazz)) {
            throw new IllegalArgumentException("Unable to use non Assembly recipe: '%s' in Sequenced Assembly Recipe!".formatted(clazz.getName()));
        }
        return (ProcessingRecipeBuilder.ProcessingRecipeFactory<T>) CLASS_TO_FACTORY.get().get(clazz);
    }
    
}
