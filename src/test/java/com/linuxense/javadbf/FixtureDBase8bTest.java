/*

(C) Copyright 2017 Alberto Fernández <infjaf@gmail.com>

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.  If not, see <http://www.gnu.org/licenses/>.

*/
package com.linuxense.javadbf;

import static com.linuxense.javadbf.testutils.DateUtils.createDate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class FixtureDBase8bTest {

	@Test
	public void test8b() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_8b.dbf");
		DBFReader reader  = null;
		try {
			reader = new DBFReader( new BufferedInputStream(new FileInputStream(file)));
			reader.setMemoFile(new File("src/test/resources/fixtures/dbase_8b.dbt"));
			
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(6, header.fieldArray.length);
			
			Assert.assertEquals("CHARACTER", header.fieldArray[0].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[0].getType());
			Assert.assertEquals(0, header.fieldArray[0].getDecimalCount());
			Assert.assertEquals(100, header.fieldArray[0].getLength());		
			
			Assert.assertEquals("NUMERICAL", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.NUMERIC, header.fieldArray[1].getType());
			Assert.assertEquals(2, header.fieldArray[1].getDecimalCount());
			Assert.assertEquals(20, header.fieldArray[1].getLength());	
			
			Assert.assertEquals("DATE", header.fieldArray[2].getName());
			Assert.assertEquals(DBFDataType.DATE, header.fieldArray[2].getType());
			Assert.assertEquals(0, header.fieldArray[2].getDecimalCount());
			Assert.assertEquals(8, header.fieldArray[2].getLength());
			
			Assert.assertEquals("LOGICAL", header.fieldArray[3].getName());
			Assert.assertEquals(DBFDataType.LOGICAL, header.fieldArray[3].getType());
			Assert.assertEquals(0, header.fieldArray[3].getDecimalCount());
			Assert.assertEquals(1, header.fieldArray[3].getLength());	
			
			Assert.assertEquals("FLOAT", header.fieldArray[4].getName());
			Assert.assertEquals(DBFDataType.FLOATING_POINT, header.fieldArray[4].getType());
			Assert.assertEquals(18, header.fieldArray[4].getDecimalCount());
			Assert.assertEquals(20, header.fieldArray[4].getLength());
			
			Assert.assertEquals("MEMO", header.fieldArray[5].getName());
			Assert.assertEquals(DBFDataType.MEMO, header.fieldArray[5].getType());
			Assert.assertEquals(0, header.fieldArray[5].getDecimalCount());
			Assert.assertEquals(10, header.fieldArray[5].getLength());	
			
			Assert.assertEquals(10, header.numberOfRecords);
			
			
			Object[] row = null;
			
			
			row = reader.nextRecord();
			Assert.assertEquals("One", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(1, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1970,1,1), row[2]);
			Assert.assertTrue(row[3] instanceof Boolean);
			Assert.assertTrue((Boolean) row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(1.23456789012346, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("First memo\r\n", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Two", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(2, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1970,12,31), row[2]);
			Assert.assertTrue(row[3] instanceof Boolean);
			Assert.assertTrue((Boolean) row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(2.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Second memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Three", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(3, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1980,1,1), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(3.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Thierd memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Four", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(4, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1900,1,1), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(4.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Fourth memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Five", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(5, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1900,12,31), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(5.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Fifth memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Six", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(6, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1901,1,1), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(6.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Sixth memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Seven", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(7, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1999,12,31), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(7.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Seventh memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Eight", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(8, ((Number)row[1]).intValue());
			Assert.assertTrue(row[2] instanceof Date);
			Assert.assertEquals(createDate(1919,12,31), row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(8.0, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertEquals("Eigth memo", row[5]);
			
			row = reader.nextRecord();
			Assert.assertEquals("Nine", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(9, ((Number)row[1]).intValue());
			Assert.assertNull(row[2]);
			Assert.assertNull(row[3]);
			Assert.assertNull(row[4]);			
			Assert.assertEquals("Nineth memo", row[5]);	

			
			row = reader.nextRecord();
			Assert.assertEquals("Ten records stored in this database", row[0]);
			Assert.assertTrue(row[1] instanceof Number);
			Assert.assertEquals(10, ((Number)row[1]).intValue());
			Assert.assertNull(row[2]);
			Assert.assertNull(row[3]);
			Assert.assertTrue(row[4] instanceof Number);			
			Assert.assertEquals(0.1, ((Number)row[4]).doubleValue(), 0.0001);
			Assert.assertNull(row[5]);
			
			row = reader.nextRecord();
			
			Assert.assertNull(row);
			


			
		} finally {
			DBFUtils.close(reader);
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