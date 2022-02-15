package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.EmptyingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @docParam this <recipetype:create:emptying>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.EmptyingManager")
@Document("mods/createtweaker/EmptyingManager")
public class EmptyingManager implements IProcessingRecipeManager<EmptyingRecipe> {
    
    /**
     * Adds an emptying recipe.
     *
     * @param name           The name of the recipe.
     * @param outputItem     The output item of the recipe.
     * @param outputFluid    The output fluid of the recipe.
     * @param inputContainer The input container of the recipe (what is being filled).
     * @param duration       The duration of the recipe in ticks (defaults to 100).
     *
     * @docParam name "emptier"
     * @docParam outputItem <item:minecraft:diamond>
     * @docParam outputFluid <fluid:minecraft:water>
     * @docParam inputContainer <item:minecraft:dirt>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack outputItem, IFluidStack outputFluid, IIngredient inputContainer, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<EmptyingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.output(outputItem.getInternal())
                .output(outputFluid.getInternal());
        builder.require(inputContainer.asVanillaIngredient());
        
        builder.duration(duration);
        EmptyingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
        
    }
    
    /**
     * Removes emptying recipes based on the output FluidStack.
     *
     * @param output The output FluidStack of the recipe.
     *
     * @docParam output <fluid:minecraft:water>
     */
    @ZenCodeType.Method
    public void remove(IFluidStack output) {
        
        CraftTweakerAPI.apply(new ActionRecipeBase<>(this) {
            @Override
            public void apply() {
                
                List<ResourceLocation> toRemove = new ArrayList<>();
                for(ResourceLocation location : getManager().getRecipes().keySet()) {
                    EmptyingRecipe recipe = getManager().getRecipes().get(location);
                    if(recipe.getFluidResults().isEmpty()) {
                        continue;
                    }
                    if(output.getInternal().isFluidEqual(recipe.getResultingFluid())) {
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
        
        return AllRecipeTypes.EMPTYING;
    }
    
}
