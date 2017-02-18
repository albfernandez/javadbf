package com.linuxense.javadbf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class CreateEmptyFileFromTemplateTest {

	
	@Test
	public void testCreate () throws IOException {
		File inputFile = new File("src/test/resources/books.dbf");
		File outputFile = File.createTempFile("example", ".dbf");
		emptyFile(inputFile, outputFile);
	}

	private void emptyFile(File inputFile, File outputFile) throws IOException, DBFException {
		OutputStream os = null;
		DBFReader reader = null;
		try {		
			reader = new DBFReader(new FileInputStream(inputFile));
			
			DBFField[] fields = new DBFField[reader.getFieldCount()];
			for (int i = 0; i < reader.getFieldCount(); i++) {
				fields[i] = reader.getField(i);
			}
			
			DBFWriter writer = new DBFWriter(reader.getCharset());
			writer.setFields(fields);
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
			writer.write(os);
			DBFUtils.close(writer);
			
			
		}
		finally {
			DBFUtils.close(reader);
			DBFUtils.close(os);
		}
	
	}
}
