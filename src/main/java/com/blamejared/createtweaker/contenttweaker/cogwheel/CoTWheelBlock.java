package com.blamejared.createtweaker.contenttweaker.cogwheel;

import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.createtweaker.CreateTweaker;
import com.simibubi.create.content.contraptions.relays.elementary.CogWheelBlock;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoTWheelBlock extends CogWheelBlock implements IIsCoTBlock {
	final CoTWheelBuilder builder;
	private final LazyValue<IIsCotItem> item;

	public CoTWheelBlock(CoTWheelBuilder builder, ResourceLocation location) {
		super(builder.isLarge(), builder.getBlockBuilder().getBlockProperties());
		this.setRegistryName(location);
		this.builder = builder;
		this.item = new LazyValue<>(() -> new CoTWheelItem(this));
		CoTWheelTileEntity.validBlocks.add(this);
	}

	/**
	 * @return the Item attached to this block
	 */
	@Nonnull
	@Override
	public IIsCotItem getItem() {
		return item.getValue();
	}


	/**
	 * Generates assets if templates are enabled.
	 * <p>
	 * Assets include a {@link BlockState} definition, a block model definition, and a default texture.
	 * <p>
	 * If templates are disabled, returns an empty {@link java.util.List}
	 * <p>
	 * If legacy model is enabled, generates a template following old 0.2.4 and earlier models before Bumble_Danis texture touch-up.
	 * Legacy models are useful for lazy people who want some wooden cog models quickly with minimal effort
	 *
	 * @return the collection of resource file templates to write
	 */
	@Nonnull
	@Override
	public Collection<WriteableResource> getResourcePackResources() {
		final Collection<WriteableResource> out = new ArrayList<>();
		if (builder.hasNoTemplate())
			return out;

		final ResourceLocation location = getRegistryNameNonNull();
		if (!builder.isLegacyModel())
			out.add(WriteableResourceImage.noImage(ImageType.BLOCK, location));

		if (builder.isLegacyModel()) {
			out.add(new WriteableResourceTemplate(ResourceType.ASSETS,
				location, "models", "block").withTemplate(ResourceType.ASSETS,
				new ResourceLocation(CreateTweaker.MODID, isLargeCog() ? "models/block/block_legacy_large_cogwheel"
					: "models/block/block_legacy_cogwheel"))
				.setProperty("NAMESPACE", builder.getLegacyTexture().getNamespace())
				.setProperty("PATH", builder.getLegacyTexture().getPath())
				.setProperty("NAMESPACE_TOP", builder.getTopTexture().getNamespace())
				.setProperty("PATH_TOP", builder.getTopTexture().getPath()));

		} else {
			out.add(new WriteableResourceTemplate(ResourceType.ASSETS,
				location, "models", "block").withTemplate(ResourceType.ASSETS,
				new ResourceLocation(CreateTweaker.MODID, isLargeCog() ? "models/block/block_large_cogwheel" : "models/block/block_cogwheel")).setLocationProperty(location));
		}
		out.add(new WriteableResourceTemplate(ResourceType.ASSETS,
			location, "blockstates").withTemplate(ResourceType.ASSETS,
			new ResourceLocation(CreateTweaker.MODID, "blockstates/block_cogwheel")).setLocationProperty(location));

		return out;
	}


	/**
	 * Generates loot table templates for the custom cogwheel blocks if templates are enabled
	 *
	 * @return a {@link java.util.List}. Empty if templates are disabled, singleton of a content tweakers native loot table template otherwise
	 */
	@Nonnull
	@Override
	public Collection<WriteableResource> getDataPackResources() {
		return builder.hasNoTemplate() ? Collections.emptyList() : Collections.singleton(new WriteableResourceLootTableItem(getRegistryNameNonNull()));
	}

	/**
	 * Not overriding this will complain in log about invalid tile entities and result in really odd behaviour.
	 *
	 * @param state is the {@link BlockState} for which the tile entity is being generated (ignored)
	 * @param world is the world in which the tile entity is being created (ignored)
	 * @return a new instance of {@link CoTWheelTileEntity}
	 */
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new CoTWheelTileEntity();
	}
}
