package com.blamejared.createtweaker;

import com.blamejared.createtweaker.contenttweaker.TileEntities;
import com.blamejared.createtweaker.contenttweaker.cogwheel.CoTWheelTileEntity;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticBlockModel;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CreateTweaker.MODID)
public class EventListener {
	private EventListener() {
	}

	/**
	 * Our content tweakers cogwheels need to register a tile entity renderer to work properly.
	 * Instancing is what makes spinny thing render on GPU, we need to register that too.
	 *
	 * @param event will be ignored but is required for the event handler to register correctly
	 */
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent event) {
		CreateTweaker.LOGGER.info("renderers registering");
		ClientRegistry.bindTileEntityRenderer(TileEntities.COT_WHEEL_TILE_ENTITY.get(), KineticTileEntityRenderer::new);
		InstancedTileRenderRegistry.instance.register(TileEntities.COT_WHEEL_TILE_ENTITY.get(), SingleRotatingInstance::new);
	}

	/**
	 * Create applies brackets to cogwheels by baked model, so we have to hook into that and register our blocks as bracketable here
	 *
	 * @param event will be ignored but is required for the event handler to register correctly
	 */
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onModelBake(ModelBakeEvent event) {
		CoTWheelTileEntity.validBlocks.forEach(block -> CreateClient.getCustomBlockModels().register(block.delegate, BracketedKineticBlockModel::new));
	}
}
