package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;

@ZenRegister
@Document("mods/CreateTweaker/fabric/ItemStackHandlerContainer")
@NativeTypeRegistration(value = ItemStackHandlerContainer.class, zenCodeName = "mods.createtweaker.fabric.ItemStackHandlerContainer")
public class ExpandItemStackHandlerContainer {

}
