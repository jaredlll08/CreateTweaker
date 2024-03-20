package com.blamejared.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;

@ZenRegister
@NativeTypeRegistration(value = HeatCondition.class, zenCodeName = "mods.createtweaker.HeatCondition")
@Document("mods/CreateTweaker/recipe/HeatCondition")
@BracketEnum("create:heat_condition")
public class ExpandHeatCondition {
    
    /**
     * Gets the translation key of this heat condition.
     *
     * @return The translation key of this heat condition.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("translationKey")
    public static String getTranslationKey(HeatCondition internal) {
        
        return internal.getTranslationKey();
    }
    
    /**
     * Gets the color of this heat condition.
     *
     * @return The color of this heat condition.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static int getColor(HeatCondition internal) {
        
        return internal.getColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(HeatCondition internal) {
        
        return "<constant:create:heat_condition:" + internal.name().toLowerCase(Locale.ROOT) + ">";
    }
    
}
