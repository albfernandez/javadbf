package com.linuxense.javadbf;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class BinaryImageTest {
	@Test
	public void testBinaryImage() throws Exception {
		File file = new File("src/test/resources/inventory.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
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
			
			
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));


		} finally {
			DBFUtils.close(reader);
		}
	}
}
