package com.linuxense.javadbf;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyTest {

	public CurrencyTest() {
		super();
	}
	
	@Test
	public void testCurrency () throws IOException {
		
		BigDecimal[] values = new BigDecimal[]{
			new BigDecimal("23.5000"),
			new BigDecimal("22.9500"),
			new BigDecimal("9.9500"),
			new BigDecimal("25.9500"),
			new BigDecimal("17.9500"),
			new BigDecimal("8.9500"),
			new BigDecimal("0.0000"),
			new BigDecimal("24.0000"),
			new BigDecimal("0.0000"),
			new BigDecimal("7.5900")
		};	
		
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream("src/test/resources/books.dbf"));

			int numberOfFields = reader.getFieldCount();
			Assert.assertEquals(11, numberOfFields);
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				Assert.assertNotNull(field.getName());
			}
			Object[] rowObject;
			int countedRows = 0;
			while ((rowObject = reader.nextRecord()) != null) {
				Assert.assertEquals(numberOfFields, rowObject.length);
				Assert.assertTrue(rowObject[6] instanceof BigDecimal);
				Assert.assertEquals(values[countedRows], rowObject[6]);
				countedRows++;
			}
			Assert.assertEquals(10, countedRows);
		}
		finally {
			DBFUtils.close(reader);
		}

	}
}
