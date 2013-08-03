package org.kt3k.straw;

public class PluginWithoutCorrectConstructor extends StrawPlugin {

	private PluginWithoutCorrectConstructor() {
	}

	@Override
	public String getName() {
		return null;
	}

}
