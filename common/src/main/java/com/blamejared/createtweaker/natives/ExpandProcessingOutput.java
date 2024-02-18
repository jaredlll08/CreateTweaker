package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

@ZenRegister
@Document("mods/CreateTweaker/ProcessingOutput")
@NativeTypeRegistration(value = ProcessingOutput.class, zenCodeName = "mods.createtweaker.ProcessingOutput", constructors = {
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "stack", examples = "<item:minecraft:dirt>"),
                @NativeConstructor.ConstructorParameter(type = float.class, name = "chance", examples = "1.0")
        })
})
public class ExpandProcessingOutput {
    
    // To match ProcessingOutput.r
    public static final Random random = new Random();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(ProcessingOutput internal) {
        
        return IItemStack.of(internal.getStack());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("chance")
    public static float getChance(ProcessingOutput internal) {
        
        return internal.getChance();
    }
    
    @ZenCodeType.Getter("random")
    public static Random random(ProcessingOutput internal) {
        
        return random;
    }
    
    @ZenCodeType.Method
    public static IItemStack rollOutput(ProcessingOutput internal) {
        
        return IItemStack.of(internal.rollOutput());
    }
    
}
