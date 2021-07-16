package com.blamejared.createtweaker.contenttweaker;

import com.blamejared.createtweaker.CreateTweaker;
import com.blamejared.createtweaker.contenttweaker.cogwheel.CoTWheelTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Create stuff is almost only tile entities.
 * However, these TEs can't be attached to our blocks with how create registers them, so we have to wrap and register them separately
 */
public class TileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CreateTweaker.MODID);
	public static final RegistryObject<TileEntityType<CoTWheelTileEntity>> COT_WHEEL_TILE_ENTITY = register("cogwheel",
		() -> TileEntityType.Builder.create(CoTWheelTileEntity::new, CoTWheelTileEntity.validBlocks.toArray(new Block[0])));

	private TileEntities() {
	}

	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String id, Supplier<TileEntityType.Builder<T>> typeBuilder) {
		return TILE_ENTITIES.register(id, () -> typeBuilder.get().build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id)));
	}


	/**
	 * @param modEventBus the event bus to attach our deferred registry to
	 */
	public static void register(IEventBus modEventBus) {
		TILE_ENTITIES.register(modEventBus);
	}
}
