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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;


public class DBFWriterRandomAccesTest {
	
	

	@Test
	public void testAppendingToExistingFile() throws IOException {
	
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFWriter writer = null;
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(outputFile);
			writer = new DBFWriter(fos);
	        writer.setFields(fields);	        
		}
		finally {
			DBFUtils.close(writer);
			DBFUtils.close(fos);			
		}
        
        DBFWriter writerRandomAcces = new DBFWriter(outputFile);
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writerRandomAcces.addRecord(rowData);
        }
        DBFUtils.close(writerRandomAcces);
        Assert.assertEquals(259L, outputFile.length());
	}
	
	@Test
	public void testCreatingFile() throws Exception {
		DBFField[] fields = createFields();
		
		File outputFile = File.createTempFile("example", ".dbf");
		DBFWriter writer = new DBFWriter(outputFile);
        writer.setFields(fields);
        
        for (int i = 0; i < 3; i++) {
        	Object rowData[] = new Object[fields.length];
        	rowData[0] = Integer.toString(i);
        	rowData[1] = "John Smith " + i;
        	rowData[2] = 1000 * (i+1) + 0.25;
        	writer.addRecord(rowData);
        }
        writer.close();
        Assert.assertEquals(259L, outputFile.length());
	}
	
	private DBFField[] createFields() {
		DBFField[] fields = new DBFField[3];

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
		return fields;
	}


	

	@Test(expected=DBFException.class)
	public void testFailOpenFile() throws DBFException {
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(new File("/this/file/doesnont/exists"));
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
