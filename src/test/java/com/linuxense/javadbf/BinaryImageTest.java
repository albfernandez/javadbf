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
