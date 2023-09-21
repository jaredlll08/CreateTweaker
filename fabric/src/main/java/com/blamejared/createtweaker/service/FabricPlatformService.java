package com.blamejared.createtweaker.service;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.impl.fluid.SimpleFluidStack;
import com.blamejared.createtweaker.mixin.access.AccessFluidTagIngredient;
import com.google.auto.service.AutoService;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.material.Fluid;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AutoService(PlatformService.class)
public class FabricPlatformService implements PlatformService {
    
    @Override
    public long getRequiredAmount(FluidIngredient ingredient) {
        
        return ingredient.getRequiredAmount();
    }
    
    @Override
    public <T extends ProcessingRecipe<?>> boolean doFluidIngredientsConflict(T first, T second) {
        
        return IngredientUtil.doIngredientsConflict(first.getFluidIngredients(),
                second.getFluidIngredients(),
                FluidIngredient.EMPTY::equals,
                fluidIngredient -> fluidIngredient.getMatchingFluidStacks().toArray(FluidStack[]::new),
                (fluidStack, fluidStack2) -> fluidStack.isFluidEqual(fluidStack2) && fluidStack.getAmount() >= fluidStack2.getAmount());
    }
    
    @Override
    public boolean testFluidIngredient(FluidIngredient ingredient, IFluidStack stack) {
        
        return ingredient.test(mapSFSToFS(stack.getInternal()));
    }
    
    @Override
    public <T extends ProcessingRecipe<? extends Container>> ProcessingRecipeBuilder<T> withFluidOutputs(ProcessingRecipeBuilder<T> builder, List<IFluidStack> fluidOutputs) {
        
        builder.withFluidOutputs(fluidOutputs
                .stream()
                .map(IFluidStack::<SimpleFluidStack>getInternal)
                .map(this::mapSFSToFS)
                .collect(Collectors.toCollection(NonNullList::create)));
        return builder;
    }
    
    @Override
    public ProcessingRecipeBuilder<ProcessingRecipe<Container>> output(ProcessingRecipeBuilder<?> builder, IFluidStack output) {
        
        return GenericUtil.uncheck(builder.output(mapSFSToFS(output.getInternal())));
    }
    
    @Override
    public ProcessingRecipeBuilder<ProcessingRecipe<Container>> withFluidOutputs(ProcessingRecipeBuilder<ProcessingRecipe<Container>> builder, IFluidStack... outputs) {
        
        return builder.withFluidOutputs(Arrays.stream(outputs)
                .map(iFluidStack -> mapSFSToFS(iFluidStack.getInternal()))
                .toArray(FluidStack[]::new));
    }
    
    @Override
    public List<IFluidStack> getRecipeFluidResults(ProcessingRecipe<?> recipe) {
        
        return recipe.getFluidResults()
                .stream()
                .map(this::mapFStoSFS)
                .map(IFluidStack::of)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<IFluidStack> getMatchingFluidStacks(FluidIngredient ingredient) {
        
        return ingredient.getMatchingFluidStacks()
                .stream()
                .map(this::mapFStoSFS)
                .map(IFluidStack::of)
                .collect(Collectors.toList());
    }
    
    
    public SimpleFluidStack mapFStoSFS(FluidStack stack) {
        
        return new SimpleFluidStack(stack.getFluid(), stack.getAmount(), stack.getTag());
    }
    
    public FluidStack mapSFSToFS(SimpleFluidStack stack) {
        
        return new FluidStack(stack.fluid(), stack.amount(), stack.tag());
    }
    
    @Override
    public FluidIngredient fromFluidStack(IFluidStack stack) {
        
        SimpleFluidStack internal = stack.getInternal();
        return FluidIngredient.fromFluidStack(new FluidStack(internal.fluid(), internal.amount(), internal.tag()));
    }
    
    @Override
    public FluidIngredient fromTag(TagKey<Fluid> tag, int amount) {
        
        return FluidIngredient.fromTag(tag, amount);
    }
    
    @Override
    public CTFluidIngredient mapFluidIngredientsToCT(FluidIngredient ingredient) {
        
        if(ingredient instanceof FluidIngredient.FluidTagIngredient fti) {
            KnownTag<Fluid> tag = CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registries.FLUID)
                    .tag(((AccessFluidTagIngredient) fti).createtweaker$getTag());
            return new CTFluidIngredient.FluidTagWithAmountIngredient(tag.asTagWithAmount());
        }
        Optional<CTFluidIngredient> reduce = ingredient.getMatchingFluidStacks()
                .stream()
                .map(IFluidStack::of)
                .map(IFluidStack::asFluidIngredient)
                .reduce(CTFluidIngredient::asCompound);
        return reduce.orElseThrow();
    }
    
}
