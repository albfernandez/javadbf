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
package com.linuxense.javadbf.docexamples;

import com.linuxense.javadbf.*;
import java.io.*;

public class JavaDBFWriterTest {

	public static void main(String args[]) throws IOException {

		// let us create field definitions first
		// we will go for 3 fields

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
		
		// Create writer
		DBFWriter writer = new DBFWriter(new FileOutputStream(args[0]));
		writer.setFields(fields);

		// now populate DBFWriter

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

		// write to file
		writer.close();
	}
}