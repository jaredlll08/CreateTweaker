package com.blamejared.createtweaker;

import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;

@CraftTweakerPlugin("createtweaker:main")
public class CreateTweakerCTPlugin implements ICraftTweakerPlugin {
    
    @Override
    public void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {
        
        handler.registerRecipeComponent(CreateTweakerRecipeComponents.Input.HEAT);
    }
    
}
