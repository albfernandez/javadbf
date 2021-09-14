package com.linuxense.javadbf.bug95memory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Ignore;
import org.junit.Test;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;

public class Bug95BigFilesTest {
	
	public Bug95BigFilesTest() {
		super();
	}
	
	@Test
	@Ignore
	public void testBigFile() throws IOException {
		File f = Files.createTempFile("bigfile", ".dbf").toFile();
		System.out.println("creating file: " + f.getAbsolutePath());
		createBigFile(f);
		System.out.println("total size: " + (f.length() / (1024*1024)) + " MB");
		System.out.println("reading file");
		readBigFile(f);
		f.delete();
		System.out.println("finished");
	}

	private void readBigFile(File f) throws FileNotFoundException {
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(f)));
			// reads the file to the end
			while (reader.nextRecord() !=null) ;
		}
		finally {
			DBFUtils.close(reader);
		}
	}

	private void createBigFile(File f) throws FileNotFoundException {
		DBFWriter writer = null;
		int rows = 35000000;
		try {
			writer = new DBFWriter(f);
			DBFField[] fields = createFields();
			writer.setFields(fields);
	        
	        for (int i = 0; i < rows; i++) {
	        	Object rowData[] = new Object[fields.length];
	        	rowData[0] = Integer.toString(i);
	        	rowData[1] = "John Smith " + i;
	        	rowData[2] = 1000 * (i+1) + 0.25;
	        	writer.addRecord(rowData);
	        }
	        writer.close();
		}
		finally {
			DBFUtils.close(writer);
		}
	}
	private DBFField[] createFields() {
		DBFField[] fields = new DBFField[3];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.CHARACTER);
		fields[0].setLength(10);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setLength(20);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.NUMERIC);
		fields[2].setLength(12);
		fields[2].setDecimalCount(2);
		return fields;
	}

}
