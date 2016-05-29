package net.shadowfacts.infiniocean;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.shadowfacts.infiniocean.overworld.ChunkProviderOverworld;

/**
 * @author shadowfacts
 */
public class OceanWorldType extends WorldType {

	public OceanWorldType() {
		super("ocean");
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkProviderOverworld(world);
	}

	@Override
	public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
		return 1;
	}

}
