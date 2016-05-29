package net.shadowfacts.infiniocean;

import net.minecraft.client.gui.GuiScreen;
import net.shadowfacts.shadowmc.config.GUIConfig;

/**
 * @author shadowfacts
 */
public class IOGUIConfig extends GUIConfig {

	public IOGUIConfig(GuiScreen parent) {
		super(parent, InfiniOcean.modId, IOConfig.config);
	}

}
