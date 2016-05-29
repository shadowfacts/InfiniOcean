package net.shadowfacts.infiniocean.nether;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.shadowfacts.infiniocean.IOConfig;
import net.shadowfacts.infiniocean.InfiniOcean;
import net.shadowfacts.shadowmc.structure.Structure;
import net.shadowfacts.shadowmc.structure.StructureManager;

import java.util.Arrays;

/**
 * @author shadowfacts
 */
public class ChunkProviderNether extends ChunkProviderHell {

	private World world;
	private MapGenNetherBridge genNetherBridge;

	public ChunkProviderNether(World world, boolean spawnFortresses, long seed) {
		super(world, spawnFortresses, seed);
		this.world = world;

		genNetherBridge = ReflectionHelper.getPrivateValue(ChunkProviderHell.class, this, "genNetherBridge", "field_73172_c");
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();

		for (int y = 0; y < IOConfig.netherBottomBlocks.length; y++) {
			String[] bits = IOConfig.netherBottomBlocks[y].split(":");
			Block block = Block.REGISTRY.getObject(new ResourceLocation(bits[0], bits[1]));
			IBlockState state = block.getStateFromMeta(bits.length == 3 ? Integer.parseInt(bits[2]) : 0);

			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {

					primer.setBlockState(i, y, j, state);

				}
			}
		}

		IBlockState lava = Blocks.LAVA.getDefaultState();
		for (int y = 0; y < IOConfig.netherOceanHeight; y++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					primer.setBlockState(i, y, j, lava);
				}
			}
		}

		Chunk chunk = new Chunk(world, primer, x, z);

		int hellId = Biome.getIdForBiome(Biome.REGISTRY.getObject(new ResourceLocation("minecraft", "hell")));
		Arrays.fill(chunk.getBiomeArray(), (byte)hellId);

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {
		if (IOConfig.netherSpawnFortresses) {
			genNetherBridge.generateStructure(world, world.rand, new ChunkPos(x, z));
		}

		int spawnX = world.getWorldInfo().getSpawnX() / 8;
		int spawnY = world.getWorldInfo().getSpawnY();
		int spawnZ = world.getWorldInfo().getSpawnZ() / 8;
		if (x == spawnX / 16 && z == spawnZ / 16) {
			Structure structure = StructureManager.INSTANCE.getValue(InfiniOcean.SPAWN_NETHER);
			structure.generate(world, new BlockPos(spawnX - structure.xSize() / 2, spawnY, spawnX - structure.zSize() / 2));
		}
	}
}
