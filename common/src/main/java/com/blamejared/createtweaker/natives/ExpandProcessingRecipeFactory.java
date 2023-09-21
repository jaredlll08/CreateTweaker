package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;

@ZenRegister
@Document("mods/createtweaker/recipe/ProcessingRecipeFactory")
@NativeTypeRegistration(value = ProcessingRecipeBuilder.ProcessingRecipeFactory.class, zenCodeName = "mods.createtweaker.ProcessingRecipeFactory")
public class ExpandProcessingRecipeFactory {

}
