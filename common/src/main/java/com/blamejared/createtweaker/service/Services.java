package com.blamejared.createtweaker.service;

import com.blamejared.createtweaker.CreateTweakerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ServiceLoader;

public class Services {
    
    private static final Logger LOGGER = LogManager.getLogger(CreateTweakerConstants.MOD_NAME + "-Services");
    
    public static final PlatformService PLATFORM = Services.load();
    
    // A little of cursed code, just as a little treat
    @SafeVarargs
    public static <T> T load(T... arr) {
        
        Class<T> clazz = (Class<T>) arr.getClass().getComponentType();
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
    
}
