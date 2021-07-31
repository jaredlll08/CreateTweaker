package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.CuttingManager")
public class CuttingManager implements IProcessingRecipeManager<CuttingRecipe> {
    
    @ZenCodeType.Method
    public ProcessingRecipeBuilder.ProcessingRecipeFactory factory() {
        
        return processingRecipeParams -> getSerializer().getFactory().create(processingRecipeParams);
    }
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CuttingRecipe> builder = new ProcessingRecipeBuilder<>(getSerializer().getFactory(), resourceLocation);
        builder.output(output.getInternal());
        builder.require(input.asVanillaIngredient());
        
        builder.duration(duration);
        CuttingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public AllRecipeTypes getCreateRecipeType() {
        
        return AllRecipeTypes.CUTTING;
    }
    
}
