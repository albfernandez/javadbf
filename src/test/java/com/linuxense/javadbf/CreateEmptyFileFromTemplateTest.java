package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class CreateEmptyFileFromTemplateTest {

	
	@Test
	public void testCreate () throws IOException {
		File inputFile = new File("src/test/resources/books.dbf");
		File outputFile = File.createTempFile("example", ".dbf");
		System.out.println(outputFile.getAbsolutePath());
		emptyFile(inputFile, outputFile);
	}

	private void emptyFile(File inputFile, File outputFile) throws IOException, DBFException {
		InputStream is = null;
		OutputStream os = null;
		try {		
			is = new BufferedInputStream(new FileInputStream(inputFile));
			DBFReader reader = new DBFReader(is);
			
			DBFField[] fields = new DBFField[reader.getFieldCount()];
			for (int i = 0; i < reader.getFieldCount(); i++) {
				fields[i] = reader.getField(i);
			}
			
			DBFWriter writer = new DBFWriter(reader.getCharset());
			writer.setFields(fields);
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
			writer.write(os);
			writer.close();
			
			
		}
		finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
	
	}
}
