package com.blamejared.createtweaker.contenttweaker.cogwheel;

import com.blamejared.createtweaker.contenttweaker.TileEntities;
import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;
import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * Content Tweakers cogwheel blocks aren't part of the AllBlocks.COGWHEEL or AllBlocks.LARGE_COGWHEEL definition in Create, so we have to wrap the tile entity
 */
public class CoTWheelTileEntity extends SimpleKineticTileEntity {
	public static final Set<Block> validBlocks = new HashSet<>();

	public CoTWheelTileEntity() {
		super(TileEntities.COT_WHEEL_TILE_ENTITY.get());
	}
}
