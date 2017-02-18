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

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.testutils.AssertUtils;

public class FixtureDBase7Test {

	@Test
	public void test7 () throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_7.dbf");
		DBFReader reader  = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			
			DBFHeader header = reader.getHeader();
			Assert.assertNotNull(header);
			Assert.assertEquals(4, header.fieldArray.length);
			Assert.assertEquals(3, header.numberOfRecords);
			DBFField []fieldArray = header.fieldArray;
			AssertUtils.assertColumnDefinition(fieldArray[0], "ACTION", DBFDataType.MEMO, 10, 0);
			AssertUtils.assertColumnDefinition(fieldArray[1], "DATE", DBFDataType.DATE, 8, 0);
			AssertUtils.assertColumnDefinition(fieldArray[2], "USER", DBFDataType.CHARACTER, 20, 0);
			AssertUtils.assertColumnDefinition(fieldArray[3], "ID", DBFDataType.CHARACTER, 12, 0);
			
			Object[] row = null;
			
			row = reader.nextRecord();
			Assert.assertEquals(createDate(2015,2,23), row[1]);		
			Assert.assertEquals("DFG", row[2]);
			Assert.assertEquals("1", row[3]);
			
			row = reader.nextRecord();
			Assert.assertEquals(createDate(2013,1,31), row[1]);		
			Assert.assertEquals("GFS", row[2]);
			Assert.assertEquals("2", row[3]);
			
			row = reader.nextRecord();
			Assert.assertEquals(createDate(2014,10,28), row[1]);		
			Assert.assertEquals("HSJ", row[2]);
			Assert.assertEquals("3", row[3]);
			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
}
