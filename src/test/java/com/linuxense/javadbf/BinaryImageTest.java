package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;

public class BinaryImageTest {
	@Test
	public void testBinaryImage() throws Exception {
		File file = new File("src/test/resources/inventory.dbf");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			DBFReader reader = new DBFReader(inputStream);
			reader.setMemoFile(new File("src/test/resources/inventory.dbt"));

			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(6, header.fieldArray.length);
			Assert.assertEquals(12, header.numberOfRecords);

			DBFField[] fieldArray = header.fieldArray;
			int i = 0;
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Item ID", DBFDataType.AUTOINCREMENT, 4, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Supplier ID", DBFDataType.LONG, 4, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Description", DBFDataType.CHARACTER, 40, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Qty", DBFDataType.LONG, 4, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "Picture", DBFDataType.BINARY, 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CacheID", DBFDataType.CHARACTER, 35, 0);
			
			Object[] row = null;
			
			row = reader.nextRecord();
			Assert.assertEquals(16777344, ((Number) row[0]).intValue());
			Assert.assertEquals(16777344, ((Number) row[1]).intValue());
			Assert.assertEquals("Dartboard", row[2]);
			Assert.assertEquals(889192576, ((Number) row[3]).intValue());
			
			
//			16777344;16777344;Dartboard;889192576;null;;
			while ((row = reader.nextRecord()) != null) {
				for (Object o : row) {
					System.out.print(o + ";");
				}
				if (row[4] != null) {
					System.out.print(((byte[])row[4]).length);
				}
				System.out.println("");
				
			}
			/*
			Object[] row = null;

			while ((row = reader.nextRecord()) != null) {
				for (Object o : row) {
					System.out.print(o + ";");
				}
				System.out.println("");
			}*/

		} finally {
			DBFUtils.close(inputStream);
		}
	}
}
