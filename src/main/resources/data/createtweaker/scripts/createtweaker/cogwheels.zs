#loader contenttweaker

import mods.contenttweaker.block.BlockBuilder;
import mods.cogweeltweaker.block.cogwheel.CoTWheelBuilder;


new BlockBuilder()
	.withType<CoTWheelBuilder>()
	// .noTemplate()
    .build("brass_cog");

new BlockBuilder()
	.withType<CoTWheelBuilder>()
	// .noTemplate()
    .build("andesite_cog");