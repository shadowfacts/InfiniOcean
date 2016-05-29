package net.shadowfacts.infiniocean.overworld;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.shadowfacts.infiniocean.IOConfig;
import net.shadowfacts.infiniocean.InfiniOcean;
import net.shadowfacts.shadowmc.structure.Structure;
import net.shadowfacts.shadowmc.structure.StructureManager;

import java.util.Arrays;

/**
 * @author shadowfacts
 */
public class ChunkProviderOverworld extends ChunkProviderFlat {

	private World world;

	public ChunkProviderOverworld(World world) {
		super(world, world.getSeed(), false, null);
		this.world = world;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();

		for (int y = 0; y < IOConfig.overworldBottomBlocks.length; y++) {
			String[] bits = IOConfig.overworldBottomBlocks[y].split(":");
			Block block = Block.REGISTRY.getObject(new ResourceLocation(bits[0], bits[1]));
			IBlockState state = block.getStateFromMeta(bits.length == 3 ? Integer.parseInt(bits[2]) : 0);

			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					primer.setBlockState(i, y, j, state);
				}
			}
		}

		IBlockState water = Blocks.WATER.getDefaultState();
		for (int y = IOConfig.overworldBottomBlocks.length; y < IOConfig.overworldOceanHeight; y++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					primer.setBlockState(i, y, j, water);
				}
			}
		}

		Chunk chunk = new Chunk(world, primer, x, z);

		int oceanId = Biome.getIdForBiome(Biome.REGISTRY.getObject(new ResourceLocation("minecraft", "ocean")));
		Arrays.fill(chunk.getBiomeArray(), (byte)oceanId);

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z)
	{
		BlockPos spawn = world.getSpawnPoint();
		if (x == spawn.getX() / 16 && z == spawn.getZ() / 16) {
			Structure structure = StructureManager.INSTANCE.getValue(InfiniOcean.SPAWN_OVERWORLD);
			BlockPos pos = spawn.add(-structure.xSize() / 2, 0, -structure.zSize() / 2);
			structure.generate(world, pos, 2);
		}
	}

}
