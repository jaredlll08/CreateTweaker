package com.blamejared.createtweaker;

import net.minecraft.resources.ResourceLocation;

public class CreateTweakerConstants {
    public static final String MOD_ID = "createtweaker";
    public static final String MOD_NAME = "CreateTweaker";
    
    
    public static ResourceLocation rl(String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
}
