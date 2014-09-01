package sevenpixel.taxes.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import sevenpixel.taxes.business.TaxesCalculator;
import sevenpixel.taxes.model.Report;
import sevenpixel.taxes.model.SoldItem;
import sevenpixel.taxes.model.SoldItems;

public class TaxesCalculatorTest extends TestBase {
	public static List<String> itemsNames = Arrays.asList("book", "music CD", "chocolate bar");
	private List<SoldItem> soldItems;
	private static TaxesCalculator underTest;

	@BeforeClass
	public static void prepare() {
		underTest = new TaxesCalculator("src/main/resources/taxes_config.json");
	}

	@Test
	public void testTaxesCalculationNormalAndExemptItems() throws Exception {
		SoldItems allItems = prepareItems("resources/test/input-normal-and-exempt.json");
		this.soldItems = allItems.getSales();
		Report result = underTest.getTaxesReport(soldItems);
		assertNotNull(result);
		BigDecimal salesTaxes = result.getSalesTaxes();
		assertEquals(getBigDecimal(1.50), salesTaxes);
		BigDecimal totalAmount = result.getTotalAmount();
		assertEquals(getBigDecimal(29.83), totalAmount);
		assertEquals(3, result.getSoldItems().size());
		for (SoldItem soldItem : result.getSoldItems()) {
			String itemName = soldItem.getName();
			assertTrue(itemsNames.contains(itemName));
			if (itemName.equals("book")) {
				String type = soldItem.getType();
				assertEquals("EXEMPT", type);
				assertEquals(1, soldItem.getQuantity().intValue());
				assertEquals(getBigDecimal(12.49), soldItem.getPrice());
			}
		}
		assertEquals(getBigDecimal(29.83), result.getTotalAmount());
	}

	@Test
	public void testTaxesCalculationImportedOnlyItems() throws Exception {
		SoldItems allItems = prepareItems("resources/test/input-imported-only.json");
		this.soldItems = allItems.getSales();
		Report result = underTest.getTaxesReport(soldItems);
		assertNotNull(result);
		BigDecimal salesTaxes = result.getSalesTaxes();
		assertEquals(getBigDecimal(7.65), salesTaxes);
		BigDecimal totalAmount = result.getTotalAmount();
		assertEquals(getBigDecimal(65.15), totalAmount);
	}

	@Test
	public void testInputAllTypes() throws Exception {
		SoldItems allItems = prepareItems("resources/test/input-alltypes.json");
		this.soldItems = allItems.getSales();
		Report result = underTest.getTaxesReport(soldItems);
		assertNotNull(result);
		BigDecimal salesTaxes = result.getSalesTaxes();
		assertEquals(getBigDecimal(7.55), salesTaxes);
		BigDecimal totalAmount = result.getTotalAmount();
		assertEquals(getBigDecimal(82.23), totalAmount);
	}
	
	@Test(expected = RuntimeException.class)
	public void testConfiguNotFoundException() throws Exception {
		new TaxesCalculator("somefakefile");
	}
}
