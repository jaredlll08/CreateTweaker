package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/ProcessingOutput")
@NativeTypeRegistration(value = ProcessingOutput.class, zenCodeName = "mods.createtweaker.ProcessingOutput")
public class ExpandProcessingOutput {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(ProcessingOutput internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getStack());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("chance")
    public static float getChance(ProcessingOutput internal) {
        
        return internal.getChance();
    }
    
    @ZenCodeType.Method
    public static IItemStack rollOutput(ProcessingOutput internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.rollOutput());
    }
    
}
