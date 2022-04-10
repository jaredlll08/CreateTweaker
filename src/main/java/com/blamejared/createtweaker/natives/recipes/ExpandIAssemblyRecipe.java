package com.blamejared.createtweaker.natives.recipes;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.contraptions.itemAssembly.IAssemblyRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/createtweaker/recipe/type/IAssemblyRecipe")
@NativeTypeRegistration(value = IAssemblyRecipe.class, zenCodeName = "mods.createtweaker.IAssemblyRecipe")
public class ExpandIAssemblyRecipe {
    
    /**
     * Does this recipe support being used in assembly processing.
     *
     * @return True if it is supported. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("supportsAssembly")
    public static boolean supportsAssembly(IAssemblyRecipe internal) {
        
        return internal.supportsAssembly();
    }
    
}
