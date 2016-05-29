package net.shadowfacts.infiniocean.nether;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkGenerator;
import net.shadowfacts.infiniocean.IOConfig;
import net.shadowfacts.infiniocean.InfiniOcean;

/**
 * @author shadowfacts
 */
public class ProviderNether extends WorldProviderHell {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return InfiniOcean.instance.isEnabled(worldObj) ? new ChunkProviderNether(worldObj, IOConfig.netherSpawnFortresses, worldObj.getSeed()) : super.createChunkGenerator();
	}

}
