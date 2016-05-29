package net.shadowfacts.infiniocean.end;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.IChunkGenerator;
import net.shadowfacts.infiniocean.InfiniOcean;

/**
 * @author shadowfacts
 */
public class ProviderEnd extends WorldProviderEnd {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return InfiniOcean.instance.isEnabled(worldObj) ? new ChunkProviderEnd(worldObj, worldObj.getWorldInfo().isMapFeaturesEnabled(), worldObj.getSeed()) : super.createChunkGenerator();
	}

}
