package com.blamejared.createtweaker.contenttweaker.cogwheel;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.blocks.BlockTypeBuilder;
import com.blamejared.contenttweaker.blocks.BlockBuilder;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@ZenRegister(modDeps = {"contenttweaker"})
@ZenCodeType.Name("mods.cogweeltweaker.block.cogwheel.CoTWheelBuilder")
public class CoTWheelBuilder extends BlockTypeBuilder {
	private boolean large;
	private boolean noTemplate;
	@Nullable
	private ResourceLocation topTexture;
	@Nullable
	private ResourceLocation legacyTexture;

	public CoTWheelBuilder(BlockBuilder blockBuilder) {
		super(blockBuilder);
	}

	/**
	 * @return true if the cogwheel was specified to use a legacy model, false otherwise
	 */
	public boolean isLegacyModel() {
		return topTexture != null && legacyTexture != null;
	}

	/**
	 * Builds and registers the custom cogwheel
	 *
	 * @param resourceLocation is the id the custom cogwheel should receive
	 */
	@Override
	public void build(ResourceLocation resourceLocation) {
		VanillaFactory.queueBlockForRegistration(new CoTWheelBlock(this, resourceLocation));
	}

	/**
	 * @return true if the cogwheel is large, false otherwise
	 */
	public boolean isLarge() {
		return large;
	}

	/**
	 * @return true if templates were disabled, false otherwise
	 */
	public boolean hasNoTemplate() {
		return noTemplate;
	}

	/**
	 * @return the {@link ResourceLocation} of the top texture; Legacy models only
	 */
	@Nullable
	public ResourceLocation getTopTexture() {
		return topTexture;
	}

	/**
	 * @return the {@link ResourceLocation} of the main texture of the cogwheel; Legacy model only
	 */
	@Nullable
	public ResourceLocation getLegacyTexture() {
		return legacyTexture;
	}

	/**
	 * Specifies the size of a cogwheel. Small if not called default.
	 *
	 * @param large true -> large; false -> small
	 * @return the Builder to continue modifying the cogwheel
	 */
	@ZenCodeType.Method
	public CoTWheelBuilder withLarge(boolean large) {
		this.large = large;
		return this;
	}

	/**
	 * Disable resource pack template generation
	 *
	 * @return the Builder to continue modifying the cogwheel
	 */
	@ZenCodeType.Method
	public CoTWheelBuilder noTemplate() {
		this.noTemplate = true;
		return this;
	}

	/**
	 * Specifies a cogwheel to use a legacy model in templating
	 *
	 * @param legacyTexture the texture location to use for the main body
	 * @param topTexture    the texture location to use for the top of the cogwheel
	 * @return the Builder to continue modifying the cogwheel
	 */
	@ZenCodeType.Method
	public CoTWheelBuilder withLegacyTexture(String legacyTexture, String topTexture) {
		this.topTexture = new ResourceLocation(topTexture);
		this.legacyTexture = new ResourceLocation(legacyTexture);
		return this;
	}

	/**
	 * @param legacyTexture is the texture location of the legacy model. For the top texture.
	 *                      Adds _top in the end for the top texture, which works for logs and stripped logs
	 * @return the Builder to continue modifying the cogwheel
	 */
	@ZenCodeType.Method
	public CoTWheelBuilder withLegacyTexture(String legacyTexture) {
		return withLegacyTexture(legacyTexture, legacyTexture + "_top");
	}
}
