package net.shadowfacts.infiniocean.overworld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkGenerator;
import net.shadowfacts.infiniocean.InfiniOcean;

/**
 * @author shadowfacts
 */
public class ProviderOverworld extends WorldProviderSurface {

	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return InfiniOcean.instance.isEnabled(worldObj) ? true : super.canCoordinateBeSpawn(x, z);
	}

	@Override
	public BlockPos getRandomizedSpawnPoint() {
		return InfiniOcean.instance.isEnabled(worldObj) ? worldObj.getTopSolidOrLiquidBlock(new BlockPos(worldObj.getSpawnPoint())) : super.getRandomizedSpawnPoint();
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return InfiniOcean.instance.isEnabled(worldObj) ? new ChunkProviderOverworld(worldObj) : super.createChunkGenerator();
	}

}
