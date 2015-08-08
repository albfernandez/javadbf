package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

	@Test
	public void testReadContinents() throws DBFException, IOException {
		testReadDBFFile("continents", 1, 7);
	}
	
	@Test
	public void testReadBooks() throws DBFException, IOException {
		testReadDBFFile("books", 11, 10);
	}
	
	@Test
	public void testReadCountries() throws DBFException, IOException {
		testReadDBFFile("countries", 29, 177);
	}
	/**
	 * Open a file generated with javadbf
	 * @throws DBFException
	 * @throws IOException
	 */
	@Test
	public void testReadJavaDbf() throws DBFException, IOException {
		testReadDBFFile("javadbf", 3, 3);
	}
	
	@Test
	public void testReadProvinciasES() throws DBFException, IOException {
		testReadDBFFile("provincias_es", 5, 52);
	}
	
	@Test
	public void testWriteAndReadAgain() throws DBFException, IOException {
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

		DBFWriter writer = new DBFWriter();
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
			if (out != null) {
				out.close();
			}
		}
		
		byte[] data = out.toByteArray();
		Assert.assertEquals(259, data.length);
		
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(data);
			testReadDBFFile(bis, 3, 3);
		}
		finally {
			if (bis != null) {
				bis.close();
			}
		}
		
	}

	private void testReadDBFFile(String fileName, int columns, int rows) throws DBFException, IOException {
		testReadDBFFile(new File("src/test/resources/" + fileName + ".dbf"), columns, rows);
	}

	private void testReadDBFFile(File file, int columns, int rows) throws DBFException, IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			testReadDBFFile(inputStream, columns, rows);
		} finally {
			inputStream.close();
		}

	}

	private void testReadDBFFile(InputStream inputStream, int columns, int rows) throws DBFException {
		DBFReader reader = new DBFReader(inputStream);

		int numberOfFields = reader.getFieldCount();
		Assert.assertEquals(columns, numberOfFields);
		for (int i = 0; i < numberOfFields; i++) {
			DBFField field = reader.getField(i);
			Assert.assertNotNull(field.getName());
		}
		Object[] rowObject;
		int countedRows = 0;
		while ((rowObject = reader.nextRecord()) != null) {
			Assert.assertEquals(numberOfFields, rowObject.length);
			countedRows++;
		}
		Assert.assertEquals(rows, countedRows);
	}
}
