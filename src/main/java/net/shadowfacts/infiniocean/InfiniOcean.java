package net.shadowfacts.infiniocean;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.shadowfacts.shadowlib.util.IOUtils;
import net.shadowfacts.shadowmc.structure.StructureManager;

import java.io.*;

/**
 * @author shadowfacts
 */
@Mod(modid = InfiniOcean.modId, name = InfiniOcean.name, version = InfiniOcean.version, acceptedMinecraftVersions = "[1.9.4]", dependencies = "required-after:shadowmc@[3.3.4,);")
public class InfiniOcean {

	public static final String modId = "InfiniOcean";
	public static final String name = "InfiniOcean";
	public static final String version = "@VERSION@";

	public static ResourceLocation SPAWN_OVERWORLD = new ResourceLocation(modId, "spawn-overworld");
	public static ResourceLocation SPAWN_NETHER = new ResourceLocation(modId, "spawn-nether");
	public static ResourceLocation SPAWN_END = new ResourceLocation(modId, "spawn-end");

	@Mod.Instance(modId)
	public static InfiniOcean instance;

	private OceanWorldType worldType;
	private File spawnOverworldFile;
	private File spawnNetherFile;
	private File spawnEndFile;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException {
		IOConfig.init(event.getModConfigurationDirectory());
		IOConfig.load();

//		Overworld
		spawnOverworldFile = new File(event.getModConfigurationDirectory(), "shadowfacts/InfiniOcean/" + IOConfig.overworldSpawnStructure);

		if (!spawnOverworldFile.exists()) {
			spawnOverworldFile.createNewFile();
			FileOutputStream out = new FileOutputStream(spawnOverworldFile);
			IOUtils.copy(getClass().getResourceAsStream("/assets/infiniocean/spawn-overworld.json"), out);
			out.close();
		}

		StructureManager.INSTANCE.register(SPAWN_OVERWORLD, new FileInputStream(spawnOverworldFile));
		StructureManager.INSTANCE.registerReloadHandler(SPAWN_OVERWORLD, registryName -> {
			try {
				return StructureManager.INSTANCE.load(registryName, new FileInputStream(spawnOverworldFile));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		});

//		Nether
		spawnNetherFile = new File(event.getModConfigurationDirectory(), "shadowfacts/InfiniOcean/" + IOConfig.netherSpawnStructure);

		if (!spawnNetherFile.exists()) {
			spawnNetherFile.createNewFile();
			FileOutputStream out = new FileOutputStream(spawnNetherFile);
			IOUtils.copy(getClass().getResourceAsStream("/assets/infiniocean/spawn-nether.json"), out);
			out.close();
		}

		StructureManager.INSTANCE.register(SPAWN_NETHER, new FileInputStream(spawnNetherFile));
		StructureManager.INSTANCE.registerReloadHandler(SPAWN_NETHER, registryName -> {
			try {
				return StructureManager.INSTANCE.load(registryName, new FileInputStream(spawnNetherFile));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		});

//		End
		spawnEndFile = new File(event.getModConfigurationDirectory(), "shadowfacts/InfiniOcean/" + IOConfig.endSpawnStructure);

		if (!spawnEndFile.exists()) {
			spawnEndFile.createNewFile();
			FileOutputStream out = new FileOutputStream(spawnEndFile);
			IOUtils.copy(getClass().getResourceAsStream("/assets/infiniocean/spawn-end.json"), out);
			out.close();
		}

		StructureManager.INSTANCE.register(SPAWN_END, new FileInputStream(spawnEndFile));
		StructureManager.INSTANCE.registerReloadHandler(SPAWN_END, registryName -> {
			try {
				return StructureManager.INSTANCE.load(registryName, new FileInputStream(spawnEndFile));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		});


		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		worldType = new OceanWorldType();
	}

	public boolean isEnabled(World world) {
		switch (world.provider.getDimension()) {
			case 0:
			case -1:
				return world.getWorldType() == worldType || IOConfig.force;
			case 1:
				return (world.getWorldType() == worldType && IOConfig.endEnabled) || IOConfig.force;
			default:
				return false;
		}
	}

}
