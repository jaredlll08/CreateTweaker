package com.blamejared.createtweaker.contenttweaker;

import com.blamejared.crafttweaker.impl.commands.script_examples.ExampleCollectionEvent;
import com.blamejared.createtweaker.CreateTweaker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class ContenttweakerCompat {
	private ContenttweakerCompat() {
	}

	/**
	 * Register content tweaker specific event listeners
	 *
	 * @param eventBus the bus to which the event listeners are attached
	 */
	public static void registerEvents(IEventBus eventBus) {
		eventBus.addListener(ContenttweakerCompat::onExampleCollection);
	}

	/**
	 * To give the user an example of how to make custom create components, we generate example scripts
	 *
	 * @param event is the {@link ExampleCollectionEvent} to which we attach our example scripts
	 */
	public static void onExampleCollection(ExampleCollectionEvent event) {
		event.addResource(new ResourceLocation(CreateTweaker.MODID, CreateTweaker.MODID + "/cogwheels"));
	}
}
