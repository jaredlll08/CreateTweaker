package com.blamejared.createtweaker;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.mixin.FluidTagIngredientAccessor;
import com.google.common.base.Suppliers;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Mod("createtweaker")
public class CreateTweaker {
    
    public static final Supplier<Map<Class<?>, ProcessingRecipeBuilder.ProcessingRecipeFactory<?>>> CLASS_TO_FACTORY = Suppliers.memoize(() -> {
        HashMap<Class<?>, ProcessingRecipeBuilder.ProcessingRecipeFactory<?>> map = new HashMap<>();
        map.put(CuttingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.CUTTING.getSerializer()).getFactory());
        map.put(DeployerApplicationRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.DEPLOYING.getSerializer()).getFactory());
        map.put(FillingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.FILLING.getSerializer()).getFactory());
        map.put(PressingRecipe.class, ((ProcessingRecipeSerializer<?>) AllRecipeTypes.PRESSING.getSerializer()).getFactory());
        return map;
    });
    
    public CreateTweaker() {
        
    }
    
    public static FluidIngredient mapFluidIngredients(CTFluidIngredient ingredient) {
        
        return ingredient
                .mapTo(FluidIngredient::fromFluidStack, FluidIngredient::fromTag, stream -> {
                    throw new IllegalArgumentException("Unable to use a compound ingredient for Create!");
                });
    }
    
    public static CTFluidIngredient mapFluidIngredientsToCT(FluidIngredient ingredient) {
        
        if(ingredient instanceof FluidIngredient.FluidTagIngredient fti) {
            KnownTag<Fluid> tag = CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registry.FLUID_REGISTRY)
                    .tag(((FluidTagIngredientAccessor) fti).createtweaker$getTag());
            return new CTFluidIngredient.FluidTagWithAmountIngredient(tag.asTagWithAmount());
        }
        Optional<CTFluidIngredient> reduce = ingredient.getMatchingFluidStacks()
                .stream()
                .map(IFluidStack::of)
                .map(IFluidStack::asFluidIngredient)
                .reduce(CTFluidIngredient::asCompound);
        return reduce.orElseThrow();
    }
    
    public static Percentaged<IItemStack> mapProcessingResult(ProcessingOutput result) {
        
        return new MCItemStack(result.getStack()).percent(result.getChance() * 100);
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
