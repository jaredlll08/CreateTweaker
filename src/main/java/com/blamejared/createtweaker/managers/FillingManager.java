package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <recipetype:create:filling>
 */
@ZenRegister
@ZenCodeType.Name("mods.create.FillingManager")
public class FillingManager implements IProcessingRecipeManager<FillingRecipe> {
    
    /**
     * Adds a filling recipe.
     *
     * @param name           The name of the recipe.
     * @param output         The output item of the recipe.
     * @param inputContainer The input container of the recipe (what is being filled).
     * @param inputFluid     The input fluid of the recipe.
     * @param duration       The duration of the recipe in ticks (defaults to 100).
     *
     * @docParam name "emptier"
     * @docParam output <item:minecraft:diamond>
     * @docParam inputContainer <item:minecraft:dirt>
     * @docParam inputFluid <fluid:minecraft:water>
     * @docParam duration 200
     */
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient inputContainer, IFluidStack inputFluid, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<FillingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.output(output.getInternal());
        builder.require(inputContainer.asVanillaIngredient())
                .require(FluidIngredient.fromFluidStack(inputFluid.getInternal()));
        
        builder.duration(duration);
        FillingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.FILLING;
    }
    
}
