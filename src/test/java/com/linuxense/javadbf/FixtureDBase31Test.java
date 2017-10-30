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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class FixtureDBase31Test {

	@Test
	public void test31 () throws Exception {
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
			
			                                                                                                          
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		} finally {
			DBFUtils.close(reader);
		}

	}
	
}



/*
 Database: dbase_31.dbf
Type: (31) Visual FoxPro with AutoIncrement field
Memo File: false
Records: 77

Fields:
Name             Type       Length     Decimal
------------------------------------------------------------------------------
PRODUCTID        I          4          0         
PRODUCTNAM       C          40         0         
SUPPLIERID       I          4          0         
CATEGORYID       I          4          0         
QUANTITYPE       C          20         0         
UNITPRICE        Y          8          4         
UNITSINSTO       I          4          0         
UNITSONORD       I          4          0         
REORDERLEV       I          4          0         
DISCONTINU       L          1          0         
_NullFlags       0          1          0         
*/
