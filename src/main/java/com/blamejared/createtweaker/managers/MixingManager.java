package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.create.MixingManager")
public class MixingManager implements IProcessingRecipeManager<MixingRecipe> {
    
    @ZenCodeType.Method
    public void addRecipe(String name, String heat, IItemStack output, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MixingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<MixingRecipe>) AllRecipeTypes.MIXING.getSerializer())
                .getFactory(), resourceLocation);
        builder.output(output.getInternal());
        
        List<Ingredient> ingredients = new ArrayList<>();
        Arrays.stream(itemInputs).forEach(iIngredientWithAmount -> {
            for(int i = 0; i < iIngredientWithAmount.getAmount(); i++) {
                ingredients.add(iIngredientWithAmount.getIngredient()
                        .asVanillaIngredient());
            }
        });
        builder.withItemIngredients(ingredients.toArray(new Ingredient[0]));
        if(fluidInputs != null && fluidInputs.length != 0) {
            builder.withFluidIngredients(Arrays.stream(fluidInputs)
                    .map(iFluidStack -> FluidIngredient.fromFluidStack(iFluidStack
                            .getInternal()))
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(Arrays.stream(HeatCondition.values())
                .filter(heatEnum -> heat.equalsIgnoreCase(heatEnum.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid heat: \"" + heat + "\" Provided! Valid options are: " + Arrays
                        .toString(HeatCondition.values()))));
        
        builder.duration(duration);
        MixingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @ZenCodeType.Method
    public void addRecipe(String name, String heat, IFluidStack output, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MixingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<MixingRecipe>) AllRecipeTypes.MIXING.getSerializer())
                .getFactory(), resourceLocation);
        builder.output(output.getInternal());
        List<Ingredient> ingredients = new ArrayList<>();
        Arrays.stream(itemInputs).forEach(iIngredientWithAmount -> {
            for(int i = 0; i < iIngredientWithAmount.getAmount(); i++) {
                ingredients.add(iIngredientWithAmount.getIngredient()
                        .asVanillaIngredient());
            }
        });
        builder.withItemIngredients(ingredients.toArray(new Ingredient[0]));
        if(fluidInputs != null) {
            builder.withFluidIngredients(Arrays.stream(fluidInputs)
                    .map(iFluidStack -> FluidIngredient.fromFluidStack(iFluidStack
                            .getInternal()))
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(Arrays.stream(HeatCondition.values())
                .filter(heatEnum -> heat.equalsIgnoreCase(heatEnum.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid heat: \"" + heat + "\" Provided! Valid options are: " + Arrays
                        .toString(HeatCondition.values()))));
        
        builder.duration(duration);
        MixingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IFluidStack output) {
        
        CraftTweakerAPI.apply(new ActionRecipeBase(this) {
            @Override
            public void apply() {
                
                List<ResourceLocation> toRemove = new ArrayList<>();
                for(ResourceLocation location : getManager().getRecipes()
                        .keySet()) {
                    IRecipe<?> iRecipe = getManager().getRecipes()
                            .get(location);
                    if(iRecipe instanceof MixingRecipe) {
                        MixingRecipe recipe = (MixingRecipe) iRecipe;
                        if(recipe.getFluidResults().isEmpty()) {
                            continue;
                        }
                        FluidStack fluidStack = recipe.getFluidResults().get(0);
                        if(output.getInternal().isFluidEqual(fluidStack)) {
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
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.MIXING;
    }
    
}
