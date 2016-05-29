package net.shadowfacts.infiniocean.end;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenSpikes;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author shadowfacts
 */
public class ChunkProviderEnd extends net.minecraft.world.gen.ChunkProviderEnd {

	private static final LoadingCache<Long, WorldGenSpikes.EndSpike[]> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5l, TimeUnit.MINUTES).build(new SpikeCacheLoader());

	private World world;
	private WorldGenSpikes genSpikes = new WorldGenSpikes();

	public ChunkProviderEnd(World world, boolean mapFeaturesEnabledIn, long seed) {
		super(world, mapFeaturesEnabledIn, seed);
		this.world = world;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		Chunk chunk = new Chunk(world, new ChunkPrimer(), x, z);

		int endId = Biome.getIdForBiome(Biome.REGISTRY.getObject(new ResourceLocation("minecraft", "sky")));
		Arrays.fill(chunk.getBiomeArray(), (byte)endId);

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {

		WorldGenSpikes.EndSpike[] spikes = getSpikesForWorld(world);
		for (WorldGenSpikes.EndSpike spike : spikes) {
			if (spike.doesStartInChunk(new BlockPos(x * 16, 0,  z * 16))) {
				genSpikes.setSpike(spike);
				genSpikes.generate(world, world.rand, new BlockPos(spike.getCenterX(), 45, spike.getCenterZ()));
			}
		}

//		if (x > -5 && x < 5 && z > -5 && z < 5 && world.rand.nextInt(5) == 0) {
//			BlockPos pos = new BlockPos(
//					x * 16 + world.rand.nextInt(16) + 8,
//					world.provider.getAverageGroundLevel(),
//					z * 16 + world.rand.nextInt(16) + 8
//			);
//			genSpikes.generate(world, world.rand, pos);
//		}

		if (x == 0 && z == 0) {
			EntityDragon dragon = new EntityDragon(world);
			dragon.setLocationAndAngles(0, 128, 0, world.rand.nextFloat() * 360f, 0);
		}
	}

	private static WorldGenSpikes.EndSpike[] getSpikesForWorld(World world) {
		Random random = new Random(world.getSeed());
		long l = random.nextLong() & 65535l;
		return SPIKE_CACHE.getUnchecked(l);
	}

	private static class SpikeCacheLoader extends CacheLoader<Long, WorldGenSpikes.EndSpike[]> {
		private SpikeCacheLoader() {

		}

		@Override
		public WorldGenSpikes.EndSpike[] load(Long key) throws Exception {
			List<Integer> list = new ArrayList<>(ContiguousSet.create(Range.closedOpen(0, 10), DiscreteDomain.integers()));
			Collections.shuffle(list, new Random(key));
			WorldGenSpikes.EndSpike[] spikes = new WorldGenSpikes.EndSpike[10];

			for (int i = 0; i < 10; i++) {
				int j = (int)(42.0D * Math.cos(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
				int k = (int)(42.0D * Math.sin(2.0D * (-Math.PI + (Math.PI / 10D) * (double)i)));
				int l = list.get(i);
				int i1 = 2 + l / 3;
				int j1 = 76 + l * 3;
				boolean flag = l == 1 || l == 2;
				spikes[i] = new WorldGenSpikes.EndSpike(j, k, i1, j1, flag);
			}

			return spikes;
		}
	}

}
