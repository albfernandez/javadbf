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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UTF8Test {

	public UTF8Test () {
		super();
	}
	
	@Test
	@Ignore
	public void testUTF8() throws Exception {
		String testString = "Cộng hòa xã hội";
		DBFField fields[] = new DBFField[1];
		fields[0] = new DBFField();
		fields[0].setName("abc");
		fields[0].setType(DBFDataType.CHARACTER);
		fields[0].setLength(10);

		DBFWriter writer = null;
		ByteArrayOutputStream fos = null;
		try {
			writer = new DBFWriter(Charset.forName("UTF-8"));
			writer.setFields(fields);
			Object rowData[] = new Object[1];
			
			rowData[0] = testString;
	
			writer.addRecord(rowData);
			fos = new ByteArrayOutputStream();
			writer.write(fos);
			
		}
		finally {
			DBFUtils.close(fos);
			DBFUtils.close(writer);
		}
		byte[] data = fos.toByteArray();
		
		
		DBFReader reader = null;
		try {
			reader = new DBFReader(new ByteArrayInputStream(data));
			Object[] rowObjects = reader.nextRecord();
			Assert.assertEquals(testString, rowObjects[0]);
		}
		finally {
			DBFUtils.close(reader);			
		}
	}
}
