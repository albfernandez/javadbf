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

import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.mocks.FailInputStream;

public class DBFReaderTest {
	
	@Test
	public void testReadContinents() throws  IOException {
		ReadDBFAssert.testReadDBFFile("continents", 1, 7);
	}
	
	@Test
	public void testReadBooks() throws  IOException {
		ReadDBFAssert.testReadDBFFile("books", 11, 10);
	}
	
	@Test
	public void testReadCountries() throws  IOException {
		ReadDBFAssert.testReadDBFFile("countries", 29, 177);
	}

	@Test
	public void testReadBirthDays() throws IOException {
		ReadDBFAssert.testReadDBFFile("bdays", 3, 1);
	}

	/**
	 * Open a file generated with javadbf
	 * @throws DBFException
	 * @throws IOException
	 */
	@Test
	public void testReadJavaDbf() throws  IOException {
		ReadDBFAssert.testReadDBFFile("javadbf", 3, 3);
	}
	
	@Test
	public void testReadProvinciasES() throws  IOException {
		ReadDBFAssert.testReadDBFFile("provincias_es", 5, 52);
	}
	
	@Test
	public void testReadReservedFields() throws IOException {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("TITLE", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[1].getType());
			Assert.assertEquals(0, header.fieldArray[1].getDecimalCount());
			Assert.assertEquals(50, header.fieldArray[1].getLength());
			Assert.assertEquals(5, header.fieldArray[1].getReserv1());
			Assert.assertEquals(0, header.fieldArray[1].getReserv2());
			Assert.assertEquals(0, header.fieldArray[1].getReserv3());
			byte[] reserv4 =  header.fieldArray[1].getReserv4();
			Assert.assertNotNull(reserv4);
			Assert.assertArrayEquals(new byte[]{0,0,0,0,0,0,0}, reserv4);
			Assert.assertEquals(0, header.fieldArray[1].getIndexFieldFlag());
			Assert.assertEquals(0, header.fieldArray[1].getSetFieldsFlag());
			Assert.assertEquals(0, header.fieldArray[1].getWorkAreaId());
			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	@Test
	public void testToString () throws IOException {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
		
			String expected = "1996/7/25\n" +
				"Total records: 10\n" + 
				"Header length: 648\n" + 
				"Columns:\n" + 
				"BOOK_ID\n" + 
				"TITLE\n" + 
				"TOPIC_ID\n" + 
				"COPYRIGHT_\n" + 
				"ISBN_NUMBE\n" + 
				"PUBLISHER_\n" + 
				"PURCHASE_P\n" + 
				"COVERTYPE\n" + 
				"DATE_PURCH\n" + 
				"PAGES\n" + 
				"NOTES\n";
			Assert.assertEquals(expected, reader.toString());
		}
		finally {
			DBFUtils.close(reader);
		}
	}

	@Test
	public void testReadBooksAddedDeleteField() throws  IOException {
		ReadDBFAssert.testReadDBFFile("books", 12, 10, true);
	}

	@Test
	public void testReadDBFFileDeletedRecords() throws  IOException {
		ReadDBFAssert.testReadDBFFileDeletedRecords("test_delete",3,1);
	}
	
	@Test(expected=DBFException.class)
	public void testFailStream() throws DBFException, IOException{
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FailInputStream());
			assertNull(reader);
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	
	
	@Test
	public void testSkipRecords() throws Exception {
		DBFReader reader = null;
		try {
			File file = new File("src/test/resources/books.dbf");
			reader = new DBFReader(new FileInputStream(file));
			reader.skipRecords(4);
			DBFRow row = reader.nextRow();
			Assert.assertEquals(5, row.getInt("BOOK_ID"));
			Assert.assertEquals("My Family", row.getString("TITLE"));
		}
		finally {
			DBFUtils.close(reader);
		}
	}

}
