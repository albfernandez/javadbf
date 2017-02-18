package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

public class ReadDBFAssert {

	private ReadDBFAssert() {
		throw new AssertionError("No instances");
	}
	
	public static void testReadDBFFile(String fileName, int expectedColumns, int expectedRows) throws DBFException, IOException {
		testReadDBFFile(new File("src/test/resources/" + fileName + ".dbf"), expectedColumns, expectedRows);
	}

	public static void testReadDBFFile(File file, int expectedColumns, int expectedRows) throws DBFException, IOException {
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			testReadDBFFile(reader, expectedColumns, expectedRows);
		} finally {
			DBFUtils.close(reader);
		}

	}
	public static void testReadDBFFile(InputStream is, int expectedColumns, int expectedRows) throws DBFException {
		testReadDBFFile(new DBFReader(is), expectedColumns, expectedRows);
	}

	public static void testReadDBFFile(DBFReader reader, int expectedColumns, int expectedRows) throws DBFException {
		

		int numberOfFields = reader.getFieldCount();
		Assert.assertEquals(expectedColumns, numberOfFields);
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
		Assert.assertEquals(expectedRows, countedRows);
		Assert.assertEquals(expectedRows, reader.getRecordCount());
	}
}
