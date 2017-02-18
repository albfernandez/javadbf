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
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class DeprecatedMethodsTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void testWriteAndReadAgain() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField fields[] = new DBFField[3];

		fields[0] = new DBFField();
		
		Assert.assertEquals((byte) 0,  fields[0].getDataType());
		
		fields[0].setFieldName("emp_code");
		fields[0].setDataType(DBFField.FIELD_TYPE_C);
		fields[0].setFieldLength(10);
		
		Assert.assertEquals(DBFField.FIELD_TYPE_C,fields[0].getDataType());

		fields[1] = new DBFField();
		fields[1].setFieldName("emp_name");
		fields[1].setDataType(DBFField.FIELD_TYPE_C);
		fields[1].setFieldLength(20);

		fields[2] = new DBFField();
		fields[2].setFieldName("salary");
		fields[2].setDataType(DBFField.FIELD_TYPE_N);
		fields[2].setFieldLength(12);
		fields[2].setDecimalCount(2);

		ByteArrayInputStream bis = null;
		DBFWriter writer = null;
		try {
			writer = new DBFWriter();
			writer.setFields(fields);
	
			// now populate DBFWriter
			//
	
			Object rowData[] = new Object[3];
			rowData[0] = "1000";
			rowData[1] = "John";
			rowData[2] = new Double(5000.00);
	
			writer.addRecord(rowData);
	
			rowData = new Object[3];
			rowData[0] = "1001";
			rowData[1] = "Lalit";
			rowData[2] = new Double(3400.00);
	
			writer.addRecord(rowData);
	
			rowData = new Object[3];
			rowData[0] = "1002";
			rowData[1] = "Rohit";
			rowData[2] = new Double(7350.00);
	
			writer.addRecord(rowData);
	
			ByteArrayOutputStream out = null;
			try {
				out = new ByteArrayOutputStream();
				writer.write(out);
			} finally {
				if (out != null) {
					out.close();
				}
			}
			
			byte[] data = out.toByteArray();
			Assert.assertEquals(259, data.length);
		

			bis = new ByteArrayInputStream(data);
			ReadDBFAssert.testReadDBFFile(bis, 3, 3);
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
		
	}
	

}
