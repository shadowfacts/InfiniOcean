package net.shadowfacts.infiniocean.transformer;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * @author shadowfacts
 */
@IFMLLoadingPlugin.MCVersion("1.9.4")
@IFMLLoadingPlugin.Name("InfiniOceanPlugin")
@IFMLLoadingPlugin.SortingIndex(1001)
public class IOPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"net.shadowfacts.infiniocean.transformer.DimensionTypeTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
