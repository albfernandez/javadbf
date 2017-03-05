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
	
	
	@Test
	public void testNullFlagsEmployees() throws Exception {
		File file = new File("src/test/resources/fixtures/foxpro-xsource/employees.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			reader.setMemoFile(new File("src/test/resources/fixtures/foxpro-xsource/employees.fpt"));
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(16, header.fieldArray.length);
			Assert.assertEquals(5, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			
			
//			for(DBFField field: fieldArray) {
//				System.out.println(field.toString());
//			}
			
			
			
			int i = 0;
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EMPLOYEEID",  DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EMPLOYEENU", DBFDataType.fromCode((byte) 'C'), 30 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "FIRSTNAME", DBFDataType.fromCode((byte) 'C'), 50  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "LASTNAME", DBFDataType.fromCode((byte) 'C'), 50  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "TITLE", DBFDataType.fromCode((byte) 'C'), 50 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EMAILNAME",  DBFDataType.fromCode((byte) 'C'), 50  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "EXTENSION", DBFDataType.fromCode((byte) 'C'), 30  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "ADDRESS", DBFDataType.fromCode((byte) 'M'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CITY", DBFDataType.fromCode((byte) 'C'), 50  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "STATEORPRO", DBFDataType.fromCode((byte) 'C'), 20  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "POSTALCODE", DBFDataType.fromCode((byte) 'C'), 20  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "COUNTRY", DBFDataType.fromCode((byte) 'C'), 50  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "HOMEPHONE", DBFDataType.fromCode((byte) 'C'), 30  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "WORKPHONE", DBFDataType.fromCode((byte) 'C'), 30  ,0);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "BILLINGRAT", DBFDataType.fromCode((byte) 'Y'), 8  ,4);
			AssertUtils.assertColumnDefinition(fieldArray[i++], "_NullFlags", DBFDataType.NULL_FLAGS, 2  ,0);
			
			
			DBFField nullFlagsField = fieldArray[fieldArray.length -1];
			
			Assert.assertTrue(nullFlagsField.isSystem());
			
			Assert.assertEquals(15, reader.getFieldCount());
//			for (DBFField field: fieldArray) {
//				System.out.println(field.getName() + ":" + field.isNullable()+ ":" + field.isSystem() + ":" + field.isBinary());
//			}

//			
//			Object[] row = null;
//			
//			while ((row = reader.nextRecord()) != null) {
//				Assert.assertEquals(10, row.length);
//				Object o = row[row.length-1];
//				System.out.println(o);
//			}
			                                                                                                          
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		} finally {
			DBFUtils.close(reader);
		}
		
	}
	
	@Test
	public void testNullFlagsPayments() throws Exception {
		File file = new File("src/test/resources/fixtures/foxpro-xsource/payments.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(10, header.fieldArray.length);
			Assert.assertEquals(5, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			
			
//			for(DBFField field: fieldArray) {
//				System.out.println(field.toString());
//			}
			
			
			
			int i = 0;
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAYMENTID",  DBFDataType.fromCode((byte) 'I'), 4  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PROJECTID", DBFDataType.fromCode((byte) 'I'), 4 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAYMENTAMO", DBFDataType.fromCode((byte) 'Y'), 8  ,4);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAYMENTDAT", DBFDataType.fromCode((byte) 'T'), 8  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CREDITCARD", DBFDataType.fromCode((byte) 'C'), 30 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CARDHOLDER",  DBFDataType.fromCode((byte) 'C'), 50  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CREDITCAR2", DBFDataType.fromCode((byte) 'T'), 8  ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "CREDITCAR3", DBFDataType.fromCode((byte) 'C'), 30 ,0);         
			AssertUtils.assertColumnDefinition(fieldArray[i++], "PAYMENTMET", DBFDataType.fromCode((byte) 'I'), 4  ,0);        
			
			AssertUtils.assertColumnDefinition(fieldArray[i++], "_NullFlags", DBFDataType.NULL_FLAGS, 2  ,0);
			
			
			DBFField nullFlagsField = fieldArray[fieldArray.length -1];
			
			Assert.assertTrue(nullFlagsField.isSystem());
			
			Assert.assertEquals(9, reader.getFieldCount());
//			for (DBFField field: fieldArray) {
//				System.out.println(field.getName() + ":" + field.isNullable()+ ":" + field.isSystem() + ":" + field.isBinary());
//			}

//			
//			Object[] row = null;
//			
//			while ((row = reader.nextRecord()) != null) {
//				Assert.assertEquals(10, row.length);
//				Object o = row[row.length-1];
//				System.out.println(o);
//			}
			                                                                                                          
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		} finally {
			DBFUtils.close(reader);
		}
		
	}
		
	
	
	
	
}
