package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this <recipetype:create:compacting>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.CompactingManager")
public class CompactingManager implements IProcessingRecipeManager<CompactingRecipe> {
    
    /**
     * Adds a recipe to the Compactor that outputs ItemStacks.
     *
     * @param name        The name of the recipe.
     * @param heat        The required heat of the recipe.
     * @param outputs     The output ItemStacks of the recipe.
     * @param itemInputs  The item inputs of the recipe.
     * @param fluidInputs The optional fluid inputs of the recipe.
     * @param duration    The duration of the recipe in ticks.
     *
     * @docParam name "compacted"
     * @docParam heat <constant:create:heat_condition:heated>
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> % 12) * 2]
     * @docParam itemInputs [<item:minecraft:glass> * 2]
     * @docParam fluidInputs [<fluid:minecraft:water> * 250]
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CompactingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        for(Percentaged<IItemStack> output : outputs) {
            
            builder.output((float) output.getPercentage(), output.getData()
                    .getInternal());
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
                    .map(iFluidStack -> FluidIngredient.fromFluidStack(iFluidStack.getInternal()))
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(heat);
        
        builder.duration(duration);
        CompactingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    /**
     * Adds a recipe to the Compactor that outputs FluidStacks.
     *
     * @param name        The name of the recipe.
     * @param heat        The required heat of the recipe.
     * @param outputs     The output FluidStacks of the recipe.
     * @param itemInputs  The item inputs of the recipe.
     * @param fluidInputs The optional fluid inputs of the recipe.
     * @param duration    The duration of the recipe in ticks.
     *
     * @docParam name "fluid_compacted"
     * @docParam heat <constant:create:heat_condition:none>
     * @docParam outputs [<fluid:minecraft:water> * 200]
     * @docParam itemInputs [<item:minecraft:glass> * 2]
     * @docParam fluidInputs [<fluid:minecraft:water> * 250]
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, IFluidStack[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CompactingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        
        for(IFluidStack output : outputs) {
            builder.output(output.getInternal());
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
                    .map(iFluidStack -> FluidIngredient.fromFluidStack(iFluidStack.getInternal()))
                    .toArray(FluidIngredient[]::new));
        }
        builder.requiresHeat(heat);
        
        builder.duration(duration);
        CompactingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    /**
     * Removes a recipe based on the output {@link IFluidStack}.
     *
     * @param output The output FluidStack
     *
     * @docParam output <fluid:minecraft:water>
     */
    @ZenCodeType.Method
    public void remove(IFluidStack output) {
        
        CraftTweakerAPI.apply(new ActionRecipeBase<>(this) {
            @Override
            public void apply() {
                
                List<ResourceLocation> toRemove = new ArrayList<>();
                for(ResourceLocation location : getManager().getRecipes()
                        .keySet()) {
                    CompactingRecipe recipe = getManager().getRecipes()
                            .get(location);
                    if(recipe.getFluidResults().isEmpty()) {
                        continue;
                    }
                    FluidStack fluidStack = recipe.getFluidResults().get(0);
                    if(output.getInternal().isFluidEqual(fluidStack)) {
                        toRemove.add(location);
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
        
        return AllRecipeTypes.COMPACTING;
    }
    
}
