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
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

public class ReadDBFAssert {

	private ReadDBFAssert() {
		throw new AssertionError("No instances");
	}
	
	public static void testReadDBFFile(String fileName, int expectedColumns, int expectedRows) throws DBFException, IOException {
		testReadDBFFile(fileName, expectedColumns, expectedRows, false);
	}

	public static void testReadDBFFile(String fileName, int expectedColumns, int expectedRows, Boolean showDeletedRows) throws DBFException, IOException {
		testReadDBFFile(new File("src/test/resources/" + fileName + ".dbf"), expectedColumns, expectedRows, showDeletedRows);
	}

	public static void testReadDBFFile(File file, int expectedColumns, int expectedRows, Boolean showDeletedRows) throws DBFException, IOException {
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)), showDeletedRows);
			testReadDBFFile(reader, expectedColumns, expectedRows);
		} finally {
			DBFUtils.close(reader);
		}

	}
	public static void testReadDBFFile(InputStream is, int expectedColumns, int expectedRows) throws DBFException {
		testReadDBFFile(new DBFReader(is), expectedColumns, expectedRows);
	}

	public static void testReadDBFFile(DBFReader reader, int expectedColumns, int expectedRows) throws DBFException {
		

		int numberOfFields = reader.getFieldCount();
		Assert.assertEquals(expectedColumns, numberOfFields);
		for (int i = 0; i < numberOfFields; i++) {
			DBFField field = reader.getField(i);
			Assert.assertNotNull(field.getName());
		}
		Object[] rowObject;
		int countedRows = 0;
		while ((rowObject = reader.nextRecord()) != null) {
			Assert.assertEquals(numberOfFields, rowObject.length);
			countedRows++;
		}
		Assert.assertEquals(expectedRows, countedRows);
		Assert.assertEquals(expectedRows, reader.getRecordCount());
	}

	public static void testReadDBFFileDeletedRecords(String fileName, int expectedRows, int expectedDeleted) throws DBFException, IOException {

		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(new File("src/test/resources/" + fileName + ".dbf"))), true);
			Object[] rowObject;

			int countedRows = 0;
			int countedDeletes = 0;
			int headerStoredRows = reader.getHeader().numberOfRecords;
			while ((rowObject = reader.nextRecord()) != null) {
				if(rowObject[0] == Boolean.TRUE) {
					countedDeletes++;
				}
				countedRows++;
			}

			Assert.assertEquals(expectedRows, countedRows);
			Assert.assertEquals(expectedDeleted, countedDeletes);
			Assert.assertEquals(expectedRows, headerStoredRows);
		} finally {
			DBFUtils.close(reader);
		}


	}
}
