package sevenpixel.taxes.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import sevenpixel.taxes.model.SoldItems;
import sevenpixel.taxes.utils.GsonHelper;

public class TestBase {

	protected BigDecimal getBigDecimal(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}

	protected SoldItems prepareItems(String filename) throws FileNotFoundException {
		File file = new File(filename);
		assertTrue(String.format("File with path %s does not exist", file.getAbsolutePath()), file.exists());
		FileReader json = new FileReader(file);
		GsonHelper helper = new GsonHelper();
		SoldItems allItems = helper.convertJsontoToObject(json, SoldItems.class);
		return allItems;
	}
}
