package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.foundation.item.SmartInventory;

@ZenRegister
@Document("mods/CreateTweaker/SmartInventory")
@NativeTypeRegistration(value = SmartInventory.class, zenCodeName = "mods.createtweaker.SmartInventory")
public class ExpandSmartInventory {

}
