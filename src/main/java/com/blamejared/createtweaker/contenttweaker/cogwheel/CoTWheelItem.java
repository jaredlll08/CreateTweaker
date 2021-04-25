package com.blamejared.createtweaker.contenttweaker.cogwheel;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.ResourceType;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.contenttweaker.api.resources.WriteableResourceTemplate;
import com.simibubi.create.content.contraptions.relays.elementary.CogwheelBlockItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

public class CoTWheelItem extends CogwheelBlockItem implements IIsCotItem {
	/**
	 * Create a new custom cogwheel item.
	 *
	 * @param blockIn The {@link CoTWheelBlock} reference which block this item is attached to
	 */
	public CoTWheelItem(CoTWheelBlock blockIn) {
		super(blockIn, blockIn.builder.getBlockBuilder().getItemProperties());
		this.setRegistryName(blockIn.getRegistryNameNonNull());
	}

	/**
	 * If templates are enabled, generates the item model templates from content tweakers native BlockItem models
	 *
	 * @return empty {@link java.util.List} if templates are disabled, a singleton list of an item model otherwise
	 */
	@Nonnull
	@Override
	public Collection<WriteableResource> getResourcePackResources() {
		return ((CoTWheelBlock) getBlock()).builder.hasNoTemplate() ? Collections.emptyList() :
			Collections.singleton(new WriteableResourceTemplate(ResourceType.ASSETS, getRegistryNameNonNull(),
				"models", "item").withTemplate(ResourceType.ASSETS,
				new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_block")).setLocationProperty(getRegistryNameNonNull()));
	}

	/**
	 * Items don't need data pack entries.
	 *
	 * @return an empty {@link java.util.List}
	 */
	@Nonnull
	@Override
	public Collection<WriteableResource> getDataPackResources() {
		return Collections.emptyList();
	}
}
