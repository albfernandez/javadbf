package com.linuxense.javadbf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


public class DBFWriterRandomAccesTest {
	
	

	@Test
	public void testAppendingToExistingFile() throws IOException {
	
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFWriter writer = new DBFWriter();
        writer.setFields(fields);
        FileOutputStream fos = new FileOutputStream(outputFile);
        writer.write(fos);
        fos.close();
        
        DBFWriter writerRandomAcces = new DBFWriter(outputFile);
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writerRandomAcces.addRecord(rowData);
        }
        writerRandomAcces.write();
        Assert.assertEquals(259L, outputFile.length());
	}
	
	@Test
	public void testCreatingFile() throws Exception {
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFWriter writer = new DBFWriter(outputFile);
        writer.setFields(fields);
        
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writer.addRecord(rowData);
        }
        writer.write();
        Assert.assertEquals(259L, outputFile.length());
	}
	
	private DBFField[] createFields() {
		DBFField[] fields = new DBFField[3];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.CHARACTER);
		fields[0].setFieldLength(10);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setFieldLength(20);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.NUMERIC);
		fields[2].setFieldLength(12);
		fields[2].setDecimalCount(2);
		return fields;
	}


	
	@SuppressWarnings("unused")
	@Test(expected=DBFException.class)
	public void testFailOpenFile() throws DBFException {
		new DBFWriter(new File("/this/file/doesnont/exists"));
	}
}
