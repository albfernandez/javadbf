package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ReadAndWriteTest {


	
	@Test
	public void testWriteAndReadAgain() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField fields[] = new DBFField[3];

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
		DBFWriter writer = null;
		ByteArrayInputStream bis = null;
		try {
			writer = new DBFWriter();
			writer.setFields(fields);
	
			// now populate DBFWriter
			//
	
			Object rowData[] = new Object[3];
			rowData[0] = "1000";
			rowData[1] = "John";
			rowData[2] = new Double(5000.00);
	
			writer.addRecord(rowData);
	
			rowData = new Object[3];
			rowData[0] = "1001";
			rowData[1] = "Lalit";
			rowData[2] = new Double(3400.00);
	
			writer.addRecord(rowData);
	
			rowData = new Object[3];
			rowData[0] = "1002";
			rowData[1] = "Rohit";
			rowData[2] = new Double(7350.00);
	
			writer.addRecord(rowData);
	
			ByteArrayOutputStream out = null;
			try {
				out = new ByteArrayOutputStream();
				writer.write(out);
			} finally {
				DBFUtils.close(out);
			}
			
			byte[] data = out.toByteArray();
			Assert.assertEquals(259, data.length);
		

			bis = new ByteArrayInputStream(data);
			ReadDBFAssert.testReadDBFFile(bis, 3, 3);
		}
		finally {
			DBFUtils.close(bis);
			DBFUtils.close(writer);
		}

		
	}


}
