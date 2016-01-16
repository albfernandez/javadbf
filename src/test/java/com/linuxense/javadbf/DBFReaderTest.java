package com.linuxense.javadbf;

import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.linuxsense.javadbf.mocks.FailInputStream;

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
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			DBFReader reader = new DBFReader(is);
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("TITLE", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[1].getType());
			Assert.assertEquals(0, header.fieldArray[1].getDecimalCount());
			Assert.assertEquals(50, header.fieldArray[1].getFieldLength());
			Assert.assertEquals(5, header.fieldArray[1].getReserv1());
			Assert.assertEquals(0, header.fieldArray[1].getReserv2());
			Assert.assertEquals(0, header.fieldArray[1].getReserv3());
			byte[] reserv4 =  header.fieldArray[1].getReserv4();
			Assert.assertNotNull(reserv4);
			Assert.assertEquals(7, reserv4.length);
			Assert.assertArrayEquals(new byte[]{0,0,0,0,0,0,0}, reserv4);
			Assert.assertEquals(0, header.fieldArray[1].getIndexFieldFlag());
			Assert.assertEquals(0, header.fieldArray[1].getSetFieldsFlag());
			Assert.assertEquals(0, header.fieldArray[1].getWorkAreaId());
			
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
	}
	@Test
	public void testToString () throws IOException {
		File file = new File("src/test/resources/books.dbf");
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			DBFReader reader = new DBFReader(is);
		
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
			if (is != null) {
				is.close();
			}
		}
	}
	
	
	
	@Test(expected=DBFException.class)
	public void testFailStream() throws DBFException, IOException{
		InputStream is = null;
		try {
			is = new FailInputStream();
			DBFReader reader = new DBFReader(is);
			assertNull(reader);
		}
		finally {
			is.close();
		}
	}

}
