package com.blamejared.createtweaker.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.google.gson.reflect.TypeToken;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class CreateTweakerRecipeComponents {
    
    public static final class Input {
        
        public static final IRecipeComponent<HeatCondition> HEAT = IRecipeComponent.simple(
                new ResourceLocation("createtweaker", "input/heat"),
                new TypeToken<>() {},
                Objects::equals
        );
        
        private Input() {}
        
    }
    
    public static final class Metadata {
        
        public static final IRecipeComponent<HeatCondition> HEAT = IRecipeComponent.simple(
                new ResourceLocation("createtweaker", "input/heat"),
                new TypeToken<>() {},
                Objects::equals
        );
        
        private Metadata() {}
        
    }
    
}
