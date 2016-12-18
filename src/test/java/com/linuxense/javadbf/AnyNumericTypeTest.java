package com.linuxense.javadbf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class AnyNumericTypeTest {
	@Test
	public void testNumericInteger() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField fields[] = new DBFField[3];

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

		DBFWriter writer = null; 

		ByteArrayOutputStream out = null;
		try {
			writer = new DBFWriter();
			writer.setFields(fields);

			// now populate DBFWriter
			//

			Object rowData[] = new Object[3];
			rowData[0] = "1000";
			rowData[1] = "John";
			rowData[2] = new Integer(5000);

			writer.addRecord(rowData);

			rowData = new Object[3];
			rowData[0] = "1001";
			rowData[1] = "Lalit";
			rowData[2] = new Long(3400);

			writer.addRecord(rowData);

			rowData = new Object[3];
			rowData[0] = "1002";
			rowData[1] = "Rohit";
			rowData[2] = 7350;

			writer.addRecord(rowData);
			out = new ByteArrayOutputStream();
			writer.write(out);
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}
		}
		
		byte[] data = out.toByteArray();
		Assert.assertEquals(259, data.length);
	}
	
	@Test
	public void testNumericBigDecimal() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField fields[] = new DBFField[3];

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

		DBFWriter writer = null;

		ByteArrayOutputStream out = null;
		try {
			writer =  new DBFWriter();
			writer.setFields(fields);

			// now populate DBFWriter
			//

			Object rowData[] = new Object[3];
			rowData[0] = "1000";
			rowData[1] = "John";
			rowData[2] = new BigDecimal("5000.00");

			writer.addRecord(rowData);

			rowData = new Object[3];
			rowData[0] = "1001";
			rowData[1] = "Lalit";
			rowData[2] = new Float(3400);

			writer.addRecord(rowData);

			rowData = new Object[3];
			rowData[0] = "1002";
			rowData[1] = "Rohit";
			rowData[2] = new BigInteger("7350");

			writer.addRecord(rowData);
			out = new ByteArrayOutputStream();
			writer.write(out);
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}
		}
		
		byte[] data = out.toByteArray();
		Assert.assertEquals(259, data.length);
	}
}
