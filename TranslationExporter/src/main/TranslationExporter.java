package main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TranslationExporter {
	private static final String OUTPUT_DIR_NAME = "output";
	
	
	public static void main(String[] args) throws IOException {
		createOutputDirIfNecessary();
		String subOutputDir = createNewDirectory();
		
		File localisationDir = new File("..");
		for (File f : localisationDir.listFiles(new FrenchFileFilter()))
		{
			Files.copy(f.toPath(), new File(subOutputDir + "/" + f.getName()).toPath());
		}
	}
	
	private static void createOutputDirIfNecessary()
	{
		File outputDir = new File(OUTPUT_DIR_NAME);
		if (outputDir.exists())
		{
			return;
		}
		if (!outputDir.mkdir())
		{
			throw new RuntimeException("Output directory not created");
		}
	}
	
	/**
	 * Create a new output sub directory and return its relative path
	 * @return
	 */
	private static String createNewDirectory()
	{
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		File outputDir = new File(OUTPUT_DIR_NAME + "/Kaiserreich_FR_" + dateFormat.format(currentDate));
		if (!outputDir.mkdir())
		{			
			System.err.println("Directory not created!");
			throw new RuntimeException("Output sub-directory not created");
		}
		return outputDir.getPath();
	}
	
	private static class FrenchFileFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith("l_french.yml");
		}		
	}
}
