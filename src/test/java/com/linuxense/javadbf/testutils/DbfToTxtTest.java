package com.linuxense.javadbf.testutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;

public final class DbfToTxtTest {

	private DbfToTxtTest() {
		throw new AssertionError("no instances");
	}
	
	public static void export(DBFReader reader, File file) {
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
			Object[] row = null;
	
			while ((row = reader.nextRecord()) != null) {
				for (Object o : row) {
					writer.print(o + ";");
				}
				writer.println("");
			}
		}
		catch (IOException e) {
			// nop
		}
		finally {
			DBFUtils.close(writer);
		}
	}
}
