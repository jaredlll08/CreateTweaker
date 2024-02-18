package com.blamejared.createtweaker.recipe.fun;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.createtweaker.recipe.fun.DeployerRecipeFunction")
@Document("mods/CreateTweaker/recipe/fun/DeployerRecipeFunction")
public interface DeployerRecipeFunction {
    
    @ZenCodeType.Method
    List<ProcessingOutput> process(List<ProcessingOutput> usualOut);
    
}