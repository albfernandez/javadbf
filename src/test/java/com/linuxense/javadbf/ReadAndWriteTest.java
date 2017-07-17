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
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class ReadAndWriteTest {


	
	@Test
	public void testWriteAndReadAgain() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField fields[] = new DBFField[3];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.CHARACTER);
		fields[0].setLength(10);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setLength(20);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.NUMERIC);
		fields[2].setLength(12);
		fields[2].setDecimalCount(2);
		DBFWriter writer = null;
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			writer = new DBFWriter(out);
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
	
			DBFUtils.close(writer);

			
			byte[] data = out.toByteArray();
			Assert.assertEquals(259, data.length);
		

			bis = new ByteArrayInputStream(data);
			ReadDBFAssert.testReadDBFFile(bis, 3, 3);
		}
		finally {
			DBFUtils.close(bis);
			DBFUtils.close(writer);
		}		
	}
	
	@Test
	public void testWriteAndReadAgainBug31() throws DBFException, IOException {
		 // let us create field definitions first
		// we will go for 3 fields
		//
		DBFField[] fields = new DBFField[1];

		fields[0] = new DBFField();
		fields[0].setName("VAL");
		fields[0].setType(DBFDataType.NUMERIC);
		fields[0].setLength(12);
		fields[0].setDecimalCount(2);


		DBFWriter writer = null;
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream out = null;
		DBFReader reader = null;
		try {
			out = new ByteArrayOutputStream();
			writer = new DBFWriter(out);
			writer.setFields(fields);
	
			// now populate DBFWriter
			//
	

			Object[] rowData = new Object[1];
			rowData[0] = new BigDecimal("123.80");
	
			writer.addRecord(rowData);
	
			DBFUtils.close(writer);

			
			byte[] data = out.toByteArray();
			Assert.assertEquals(79, data.length);
		

			bis = new ByteArrayInputStream(data);
			reader = new DBFReader(bis);
			int numberOfColumns = reader.getFieldCount();
			Assert.assertEquals(1, numberOfColumns);
			Object[] rowObject = reader.nextRecord();
			Assert.assertEquals(new BigDecimal("123.80"), rowObject[0]);
		}
		finally {
			DBFUtils.close(bis);
			DBFUtils.close(writer);
			DBFUtils.close(reader);
		}		
	}


}
