package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class NullFlagsTest {

	
	@Test
	public void testNullFlags() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_31.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(11, header.fieldArray.length);
			Assert.assertEquals(77, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			
			int i = 0;
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PRODUCTID",  DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PRODUCTNAM", DBFDataType.fromCode((byte) 'C'), 40 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "SUPPLIERID", DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CATEGORYID", DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "QUANTITYPE", DBFDataType.fromCode((byte) 'C'), 20 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UNITPRICE",  DBFDataType.fromCode((byte) 'Y'), 8  ,4);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UNITSINSTO", DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "UNITSONORD", DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "REORDERLEV", DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "DISCONTINU", DBFDataType.fromCode((byte) 'L'), 1  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "_NullFlags", DBFDataType.NULL_FLAGS, 1  ,0);
			
			
			DBFField nullFlagsField = fieldArray[fieldArray.length -1];
			
			Assert.assertTrue(nullFlagsField.isSystem());
			
			Assert.assertEquals(10, reader.getFieldCount());
//			for (DBFField field: fieldArray) {
//				System.out.println(field.getName() + ":" + field.isNullable()+ ":" + field.isSystem() + ":" + field.isBinary());
//			}

			
			Object[] row = null;
			
			while ((row = reader.nextRecord()) != null) {
				Assert.assertEquals(10, row.length);
//				Object o = row[row.length-1];
//				System.out.println(o);
			}
			                                                                                                          
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		} finally {
			DBFUtils.close(reader);
		}

	}

}
