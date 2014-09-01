package sevenpixel.taxes.model;

import java.util.Set;

public class Config {
	private Set<TaxConfig> config;
	private double roundingFactor;

	public Set<TaxConfig> getConfig() {
		return config;
	}

	public double getRoundingFactor() {
		return roundingFactor;
	}
}
