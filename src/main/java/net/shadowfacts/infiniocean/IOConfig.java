package net.shadowfacts.infiniocean;

import net.minecraftforge.common.config.Configuration;
import net.shadowfacts.config.Config;
import net.shadowfacts.config.ConfigManager;
import net.shadowfacts.shadowmc.config.ForgeConfigAdapter;

import java.io.File;

/**
 * @author shadowfacts
 */
@Config(name = "InfiniOcean")
public class IOConfig {

	public static File configFile;
	public static Configuration config;

//	General
	@Config.Prop(description = "Force InfiniOcean generation no matter the world type")
	public static boolean force = false;

//	Overworld
	@Config.Prop(category = "overworld", name = "bottomBlocks", description = "The blocks to be generated at the bottom of the world.\nIn the form of modid:name:meta")
	public static String[] overworldBottomBlocks = {"minecraft:bedrock", "minecraft:gravel", "minecraft:gravel", "minecraft:gravel"};

	@Config.Prop(category = "overworld", name = "spawnStructure", description = "Path (relative to config/shadowfacts/InfiniOcean/) to locate the spawn structure JSON")
	public static String overworldSpawnStructure = "spawn-overworld.json";

	@Config.Prop(category = "overworld", name = "oceanHeight", description = "Up to what height to fill with the ocean")
	public static int overworldOceanHeight = 64;

//	Nether
	@Config.Prop(category = "nether", name = "enabled", description = "Enable InfiniOcean nether generation")
	public static boolean netherEnabled = true;

	@Config.Prop(category = "nether", name = "spawnFortresses", description = "Generate nether fortresses")
	public static boolean netherSpawnFortresses = true;

	@Config.Prop(category = "nether", name = "bottomBlocks", description = "The blocks to be generated at the bottom of the world.\nIn the form of modid:name:meta")
	public static String[] netherBottomBlocks = {"minecraft:bedrock", "minecraft:gravel", "minecraft:gravel", "minecraft:gravel"};

	@Config.Prop(category = "nether", name = "spawnStructure", description = "Path (relative to config/shadowfacts/InfiniOcean/) to locate the spawn structure JSON")
	public static String netherSpawnStructure = "spawn-nether.json";

	@Config.Prop(category = "nether", name = "oceanHeight", description = "Up to what height to fill with the ocean")
	public static int netherOceanHeight = 64;

//	End
	@Config.Prop(category = "end", name = "enabled", description = "Enable InfiniOcean end generation")
	public static boolean endEnabled = false;

	@Config.Prop(category = "end", name = "spawnStructure", description = "Path (relative to config/shadowfacts/InfiniOcean/) to locatre the spawn structure JSON")
	public static String endSpawnStructure = "spawn-end.json";

	public static void init(File configDir) {
		configFile = new File(configDir, "shadowfacts/InfiniOcean/InfiniOcean.cfg");
		config = new Configuration(configFile);
		ForgeConfigAdapter.init();
	}

	public static void load() {
		ConfigManager.load(IOConfig.class, Configuration.class, config);
		if (config.hasChanged()) config.save();
	}

}
