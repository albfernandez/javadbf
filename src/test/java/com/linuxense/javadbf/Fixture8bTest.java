package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class Fixture8bTest {

	@Test
	public void test8b() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_8b.dbf");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			DBFReader reader = new DBFReader(inputStream);
			
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("CHARACTER", header.fieldArray[0].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[0].getType());
			Assert.assertEquals(0, header.fieldArray[0].getDecimalCount());
			Assert.assertEquals(100, header.fieldArray[0].getFieldLength());		
			
			Assert.assertEquals("NUMERICAL", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.NUMERIC, header.fieldArray[1].getType());
			Assert.assertEquals(2, header.fieldArray[1].getDecimalCount());
			Assert.assertEquals(20, header.fieldArray[1].getFieldLength());	
			
			Assert.assertEquals("DATE", header.fieldArray[2].getName());
			Assert.assertEquals(DBFDataType.DATE, header.fieldArray[2].getType());
			Assert.assertEquals(0, header.fieldArray[2].getDecimalCount());
			Assert.assertEquals(8, header.fieldArray[2].getFieldLength());
			
			Assert.assertEquals("LOGICAL", header.fieldArray[3].getName());
			Assert.assertEquals(DBFDataType.LOGICAL, header.fieldArray[3].getType());
			Assert.assertEquals(0, header.fieldArray[3].getDecimalCount());
			Assert.assertEquals(1, header.fieldArray[3].getFieldLength());	
			
			Assert.assertEquals("FLOAT", header.fieldArray[4].getName());
			Assert.assertEquals(DBFDataType.FLOATING_POINT, header.fieldArray[4].getType());
			Assert.assertEquals(18, header.fieldArray[4].getDecimalCount());
			Assert.assertEquals(20, header.fieldArray[4].getFieldLength());
			
			Assert.assertEquals("MEMO", header.fieldArray[5].getName());
			Assert.assertEquals(DBFDataType.MEMO, header.fieldArray[5].getType());
			Assert.assertEquals(0, header.fieldArray[5].getDecimalCount());
			Assert.assertEquals(10, header.fieldArray[5].getFieldLength());	
			
			Assert.assertEquals(10, header.numberOfRecords);
			
			
			Object[] row = null;
			
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("One"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(1, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(1.23456789012346, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Two"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(2, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(2.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Three"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(3, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(3.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Four"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(4, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(4.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Five"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(5, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(5.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Six"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(6, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(6.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Seven"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(7, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(7.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Eight"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(8, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(8.0, ((Number)row[4]).doubleValue(), 0.0001);
			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Nine"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(9, ((Number)row[1]).intValue());
			Assert.assertNull(row[2]);
			Assert.assertNull(row[4]);			

			
			row = reader.nextRecord();
			Assert.assertTrue(row[0].toString().startsWith("Ten records stored in this database"));
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(10, ((Number)row[1]).intValue());
			Assert.assertNull(row[2]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(0.1, ((Number)row[4]).doubleValue(), 0.0001);
			
			


			
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}
}


/*
Database: dbase_8b.dbf
Type: (8b) dBase IV with memo file
Memo File: true
Records: 10

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
CHARACTER        C          100        0         
NUMERICAL        N          20         2         
DATE             D          8          0         
LOGICAL          L          1          0         
FLOAT            F          20         18        
MEMO             M          10         0         
*/

/*
One                                                                                                 ;1.0;Thu Jan 01 00:00:00 CET 1970;true;1.23456789012346;null;
Two                                                                                                 ;2.0;Thu Dec 31 00:00:00 CET 1970;true;2.0;null;
Three                                                                                               ;3.0;Tue Jan 01 00:00:00 CET 1980;null;3.0;null;
Four                                                                                                ;4.0;Mon Jan 01 00:00:00 CET 1900;null;4.0;null;
Five                                                                                                ;5.0;Mon Dec 31 00:00:00 CET 1900;null;5.0;null;
Six                                                                                                 ;6.0;Tue Jan 01 00:14:44 CET 1901;null;6.0;null;
Seven                                                                                               ;7.0;Fri Dec 31 00:00:00 CET 1999;null;7.0;null;
Eight                                                                                               ;8.0;Wed Dec 31 00:00:00 CET 1919;null;8.0;null;
Nine                                                                                                ;9.0;null;null;null;null;
Ten records stored in this database                                                                 ;10.0;null;null;0.1;null;
*/