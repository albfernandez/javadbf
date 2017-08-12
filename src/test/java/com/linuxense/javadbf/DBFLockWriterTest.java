package com.linuxense.javadbf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class DBFLockWriterTest {

	public DBFLockWriterTest() {
		super();
	}
	
	@Test
	public void testAppendingToExistingFile() throws IOException {
	
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFWriter writer = null;
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(outputFile);
			writer = new DBFWriter(fos);
	        writer.setFields(fields);	        
		}
		finally {
			DBFUtils.close(writer);
			DBFUtils.close(fos);			
		}
        
		DBFLockWriter writerRandomAcces = new DBFLockWriter(outputFile);
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writerRandomAcces.addRecord(rowData);
        }
        DBFUtils.close(writerRandomAcces);
        Assert.assertEquals(259L, outputFile.length());
	}
	
	@Test
	public void testCreatingFile() throws Exception {
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFLockWriter writer = new DBFLockWriter(outputFile);
        writer.setFields(fields);
        
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writer.addRecord(rowData);
        }
        writer.close();
        Assert.assertEquals(259L, outputFile.length());
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


	

	@Test(expected=DBFException.class)
	public void testFailOpenFile() throws DBFException {
		DBFLockWriter writer = null;
		try {
			writer = new DBFLockWriter(new File("/this/file/doesnont/exists"));
		}
		finally {
			DBFUtils.close(writer);
		}
	}

}
