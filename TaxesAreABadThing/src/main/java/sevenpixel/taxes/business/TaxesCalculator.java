package sevenpixel.taxes.business;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import sevenpixel.taxes.model.Config;
import sevenpixel.taxes.model.Report;
import sevenpixel.taxes.model.SoldItem;
import sevenpixel.taxes.model.TaxConfig;
import sevenpixel.taxes.utils.FileUtils;
import sevenpixel.taxes.utils.GsonHelper;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * 
 * Provides methods for calculating an input List of SoldItems and also for
 * providing reports after calculation.
 * 
 */
public class TaxesCalculator {

	private Config configurations;

	public TaxesCalculator(String configFileName) {
		initConfiguration(configFileName);

	}

	public Report getTaxesReport(List<SoldItem> soldItems) {
		Report result = new Report();
		result.setSoldItems(soldItems);
		BigDecimal taxesAmount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SoldItem soldItem : soldItems) {
			BigDecimal quantity = new BigDecimal(soldItem.getQuantity());
			taxesAmount = addTaxes(taxesAmount, soldItem, quantity);
			totalAmount = totalAmount.add(soldItem.getPrice().multiply(quantity));
		}
		result.setSalesTaxes(taxesAmount);
		result.setTotalAmount(totalAmount.add(result.getSalesTaxes()));
		return result;
	}

	private BigDecimal addTaxes(BigDecimal taxesAmount, SoldItem soldItem, BigDecimal quantity) {
		BigDecimal price = soldItem.getPrice();
		final String itemType = soldItem.getType();
		TaxConfig found = Iterables.find(configurations.getConfig(), new Predicate<TaxConfig>() {
			@Override
			public boolean apply(TaxConfig taxConfig) {
				return taxConfig.getKey().equals(itemType);
			}
		});
		BigDecimal newValue = price.multiply(quantity).multiply(new BigDecimal(found.getTaxPercantage()));
		taxesAmount = taxesAmount.add(newValue);
		return roundWithFactor(taxesAmount, configurations.getRoundingFactor());
	}

	private void initConfiguration(String configFileName) {
		GsonHelper gsonHelper = new GsonHelper();
		FileUtils fileUtils = new FileUtils();
		FileReader fileReader = fileUtils.createFileReader(configFileName);
		if(fileReader == null) { 
			throw new RuntimeException(String.format("File with name %s not found", new File(configFileName).getAbsoluteFile()));
		}
		Config taxesConfiguration = gsonHelper.convertJsontoToObject(fileReader, Config.class);
		this.configurations = taxesConfiguration;
	}

	private BigDecimal roundWithFactor(BigDecimal value, double roundingFactor) {
		double rounded = Math.round(value.doubleValue() * roundingFactor) / roundingFactor;
		return new BigDecimal(rounded).setScale(2, RoundingMode.HALF_DOWN);
	}

}