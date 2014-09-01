package sevenpixel.taxes.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.Files;

public class FileUtils {

	public static final Logger logger = Logger.getLogger(FileUtils.class.getName());

	public FileReader createFileReader(String fileName) {
		File fileWithInput = new File(fileName);
		try {
			return new FileReader(fileWithInput);
		} catch (FileNotFoundException e) {
			logger.log(Level.FINE, "Cannot filewith name: " + fileName, e);
			return null;
		}
	}

	public FileWriter createFileWriterAndBackup(String fileLocation) {
		File outputFile = new File(fileLocation);
		try {
			if(outputFile.exists()) { 
				backupFile(outputFile);
			}
			outputFile.createNewFile();
			return new FileWriter(outputFile);
		} catch (IOException e) {
			logger.log(Level.FINE, "Cannot crerate file with name: " + outputFile.getName(), e);
			return null;
		}
	}

	private void backupFile(File outputFile) throws IOException {
		File toBackupIn = new File(outputFile.getAbsolutePath() + "_" + Calendar.getInstance().getTimeInMillis());
		toBackupIn.createNewFile();
		Files.copy(outputFile, toBackupIn);
	}

	public void writeToFile(FileWriter writer, String content) {
		try {
			writer.write(content);
		} catch (IOException e) {
			logger.log(Level.FINE, "Could not write file", e);
		} finally {
			closeSilently(writer);
		}
	}

	private void closeSilently(FileWriter writer) {
		try {
			writer.close();
		} catch (IOException e) {
			logger.log(Level.FINE, "Could not close file", e);
		}
	}

}
