package com.automation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
/*
 * this class will help to write log
 */
public class CreateLogger
{

	/*
	 * this method is responsible to write log
	 */
	public static void enterLogData(String destination, String infoToWrite)
	{

		File file = new File(destination);

		try {
			if ( !file.exists() ) {
				file.createNewFile();
			}
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(destination, true));
			fileWriter.write(infoToWrite);
			fileWriter.close();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
	}
	
	/*
	 * this method is responsible to write log
	 */
	public static void refreshLogData(String destination)
	{
		File file = new File(destination);
		file.delete();
	}
	
	/*
	 * this method is responsible to write log
	 */
	public static String getFileData(String sourcePath)
	{
		String fileContent = "";
		File file = new File(sourcePath);
		try {
			if ( file.exists() ) {
				BufferedReader fileReader = new BufferedReader(new FileReader(sourcePath));
				
				String dataRow = fileReader.readLine();
				fileContent = dataRow;
				//int i = 0;
				while (dataRow != null) {
					//i++;
					fileContent = fileContent + dataRow+"\n";
					dataRow = fileReader.readLine();
				}
				fileReader.close();
			}
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
			fileContent = "\nNo test result found\n";
		}
		return fileContent;
	}
	
	public static void fileWrite(String str, String path) {
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
