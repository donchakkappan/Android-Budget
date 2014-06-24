package com.kites.budget;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import android.os.Environment;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVReadWrite {


	
	public static void readCSV() throws Exception {
		System.out.println("\n**** readAllExample ****");
		String[] row = null;
		String csvFilename = Environment.getExternalStorageDirectory()+"/output.csv";
		CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		List content = csvReader.readAll();

		for (Object object : content) {
			row = (String[]) object;

			System.out.println(row[0] + " # " + row[1] + " #  " + row[2]+ " #  " + row[3]+ " #  " + row[4]);
		}

		csvReader.close();

	}
	
	public static void writeCSV(String data) throws Exception {
		System.out.println("\n**** writeCSVExample ****");

		String csv = Environment.getExternalStorageDirectory()+"/output.csv";

		CSVWriter writer = new CSVWriter(new FileWriter(csv,true));

		String[] country =data.split("#");
		
		writer.writeNext(country);
		System.out.println("CSV written successfully.");
		writer.close();
	}
	
}
