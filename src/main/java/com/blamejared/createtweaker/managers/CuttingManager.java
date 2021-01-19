package com.blamejared.createtweaker.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.CuttingManager")
public class CuttingManager implements IRecipeManager {
    
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient input) {
        
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        ProcessingRecipeBuilder<CuttingRecipe> builder = new ProcessingRecipeBuilder<>(((ProcessingRecipeSerializer<CuttingRecipe>) AllRecipeTypes.CUTTING.serializer).getFactory(), resourceLocation);
        builder.output(output.getInternal());
        builder.require(input.asVanillaIngredient());
        
        CuttingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
        
    }
    
    @Override
    public IRecipeType<CuttingRecipe> getRecipeType() {
        
        return AllRecipeTypes.CUTTING.getType();
    }
    
}
