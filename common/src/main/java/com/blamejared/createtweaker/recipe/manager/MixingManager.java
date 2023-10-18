package com.blamejared.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.CreateTweakerHelper;
import com.blamejared.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this <recipetype:create:mixing>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.MixingManager")
@Document("mods/CreateTweaker/MixingManager")
public class MixingManager implements IProcessingRecipeManager<MixingRecipe> {
    
    @Deprecated(forRemoval = true)
    public void addRecipe(String name, HeatCondition heat, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        addRecipe(name, heat, outputs, itemInputs, Arrays.stream(fluidInputs)
                .map(IFluidStack::asFluidIngredient)
                .toArray(CTFluidIngredient[]::new), duration);
    }
    
    @Deprecated(forRemoval = true)
    public void addRecipe(String name, HeatCondition heat, IFluidStack[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        addRecipe(name, heat, outputs, itemInputs, Arrays.stream(fluidInputs)
                .map(IFluidStack::asFluidIngredient)
                .toArray(CTFluidIngredient[]::new), duration);
    }
    
    /**
     * Adds a mixing recipe that outputs ItemStacks.
     *
     * @param name        The name of the recipe.
     * @param heat        The required heat of the recipe.
     * @param outputs     The output ItemStacks of the recipe.
     * @param itemInputs  The item inputs of the recipe.
     * @param fluidInputs The optional fluid inputs of the recipe.
     * @param duration    The duration of the recipe in ticks.
     *
     * @docParam name "mixed"
     * @docParam heat <constant:create:heat_condition:heated>
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> * 2) % 12]
     * @docParam itemInputs [<item:minecraft:glass> * 2]
     * @docParam fluidInputs [<fluid:minecraft:water> * 250]
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.FluidIngredient[]") CTFluidIngredient[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MixingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        for(Percentaged<IItemStack> output : outputs) {
            
            builder.output((float) output.getPercentage(), output.getData().getInternal());
        }
        
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
                    .map(CreateTweakerHelper::mapFluidIngredients)
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(heat);
        
        builder.duration(duration);
        MixingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    /**
     * Adds a mixing recipe that outputs FluidStacks.
     *
     * @param name        The name of the recipe.
     * @param heat        The required heat of the recipe.
     * @param outputs     The output FluidStacks of the recipe.
     * @param itemInputs  The item inputs of the recipe.
     * @param fluidInputs The optional fluid inputs of the recipe.
     * @param duration    The duration of the recipe in ticks.
     *
     * @docParam name "fluid_mixed"
     * @docParam heat <constant:create:heat_condition:none>
     * @docParam outputs [<fluid:minecraft:water> * 200]
     * @docParam itemInputs [<item:minecraft:glass> * 2]
     * @docParam fluidInputs [<fluid:minecraft:water> * 250]
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, IFluidStack[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.FluidIngredient[]") CTFluidIngredient[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<MixingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        
        for(IFluidStack output : outputs) {
            Services.PLATFORM.output(builder, output);
        }
        
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
                    .map(CreateTweakerHelper::mapFluidIngredients)
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(heat);
        
        builder.duration(duration);
        MixingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Deprecated(forRemoval = true)
    public void remove(IFluidStack output) {
        
        remove(output.asFluidIngredient());
    }
    
    /**
     * Removes recipes based on the output fluid.
     *
     * @param output The output fluid of the recipes to remove.
     *
     * @docParam output <fluid:minecraft:water>
     */
    @ZenCodeType.Method
    public void remove(CTFluidIngredient output) {
        
        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, recipe -> {
            List<IFluidStack> fluidResults = Services.PLATFORM.getRecipeFluidResults(recipe);
            return !fluidResults.isEmpty() && output.matches(fluidResults.get(0));
        }).describeDefaultRemoval(output));
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.MIXING;
    }
    
}
