package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

@ZenRegister
@Document("mods/createtweaker/ProcessingRecipeFactory")
@NativeTypeRegistration(value = ProcessingRecipeBuilder.ProcessingRecipeFactory.class, zenCodeName = "mods.createtweaker.ProcessingRecipeFactory")
public class ExpandProcessingRecipeFactory {

}
