package com.blamejared.createtweaker;

import com.blamejared.createtweaker.contenttweaker.ContenttweakerCompat;
import com.blamejared.createtweaker.contenttweaker.TileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateTweaker.MODID)
public class CreateTweaker {
	// Specify this as a constant so we can reuse it in all the places
	public static final String MODID = "createtweaker";

	// A logger is just useful in general
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public CreateTweaker() {
		// If Content tweaker is installed, we can generate the content tweaker specific example scripts. Which requires that event listener to be registered
		Mods.CONTENTTWEAKER.runIfInstalled(() -> () -> ContenttweakerCompat.registerEvents(MinecraftForge.EVENT_BUS));

		// The tile entities are not specific to content tweaker, we can register those immediately
		TileEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
