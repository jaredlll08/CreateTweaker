package com.blamejared.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.blamejared.createtweaker.service.Services;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

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
    public void addRecipe(String name, Percentaged<IItemStack> outputItem, IFluidStack outputFluid, IIngredient inputContainer, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<EmptyingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.output((float) outputItem.getPercentage(), outputItem.getData().getInternal());
        Services.PLATFORM.output(builder, outputFluid);
        builder.require(inputContainer.asVanillaIngredient());
        
        builder.duration(duration);
        EmptyingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }
    
    @Deprecated(forRemoval = true)
    public void remove(IFluidStack output) {
        
        remove(output.asFluidIngredient());
    }
    
    /**
     * Removes emptying recipes based on the output FluidStack.
     *
     * @param output The output FluidStack of the recipe.
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
        
        return AllRecipeTypes.EMPTYING;
    }
    
}
