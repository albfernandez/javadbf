package com.linuxense.javadbf;

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
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			testReadDBFFile(inputStream, expectedColumns, expectedRows);
		} finally {
			inputStream.close();
		}

	}

	public static void testReadDBFFile(InputStream inputStream, int expectedColumns, int expectedRows) throws DBFException {
		DBFReader reader = new DBFReader(inputStream);

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
	}
}
