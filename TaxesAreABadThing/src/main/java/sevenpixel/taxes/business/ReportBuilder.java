package sevenpixel.taxes.business;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import sevenpixel.taxes.model.Report;
import sevenpixel.taxes.model.SoldItems;
import sevenpixel.taxes.utils.FileUtils;
import sevenpixel.taxes.utils.GsonHelper;

/**
 * Generates report and it is the main executable file in the program. As first
 * parameter it takes the input information and as second parameter the location
 * where the report has to be dumped. The report is in json format in order to
 * 
 */
public class ReportBuilder {
	public static final Logger logger = Logger.getLogger(ReportBuilder.class.getName());

	public static void main(String[] args) {
		String configFile = System.getProperty("config.file");
		String inputData = System.getProperty("input.file");
		String outpuFile = System.getProperty("output.file");
		validateInput(configFile, inputData, outpuFile);
		GsonHelper gsonHelper = new GsonHelper();
		TaxesCalculator taxesCalculator = new TaxesCalculator(configFile);
		FileUtils fileUtils = new FileUtils();
		FileReader inputFileReader = fileUtils.createFileReader(inputData);
		SoldItems soldItems = gsonHelper.convertJsontoToObject(inputFileReader, SoldItems.class);
		Report report = taxesCalculator.getTaxesReport(soldItems.getSales());
		FileWriter fileWriter = fileUtils.createFileWriterAndBackup(outpuFile);
		if (fileWriter == null) {
			logger.log(Level.SEVERE, "Output file could not be created");
			return;
		}
		String jsonString = gsonHelper.toJson(report, Report.class);
		fileUtils.writeToFile(fileWriter, jsonString);
	}

	private static void validateInput(String... files) {
		for (String filename : files) {
			Preconditions.checkArgument(!Strings.isNullOrEmpty(filename), "Config, input and output file names cannot be empty");
		}
	}

}
