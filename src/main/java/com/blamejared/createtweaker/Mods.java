package com.blamejared.createtweaker;

import net.minecraftforge.fml.ModList;

import java.util.function.Supplier;

/**
 * For compatibility with and without another mod present, we have to define load conditions of the specific code
 */
public enum Mods {
	CONTENTTWEAKER;

	/**
	 * @return a boolean of whether the mod is loaded or not
	 */
	public boolean isLoaded() {
		return ModList.get().isLoaded(asId());
	}

	public String asId() {
		return name().toLowerCase();
	}

	/**
	 * Simple hook to run code if a mod is installed
	 * @param toRun will be run if the mod is loaded
	 */
	public void runIfInstalled(Supplier<Runnable> toRun) {
		if (isLoaded())
			toRun.get().run();
	}
}
