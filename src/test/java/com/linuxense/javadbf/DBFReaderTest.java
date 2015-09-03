package com.linuxense.javadbf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.linuxsense.javadbf.mocks.FailInputStream;

public class DBFReaderTest {
	
	@Test
	public void testReadContinents() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile("continents", 1, 7);
	}
	
	@Test
	public void testReadBooks() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile("books", 11, 10);
	}
	
	@Test
	public void testReadCountries() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile("countries", 29, 177);
	}
	/**
	 * Open a file generated with javadbf
	 * @throws DBFException
	 * @throws IOException
	 */
	@Test
	public void testReadJavaDbf() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile("javadbf", 3, 3);
	}
	
	@Test
	public void testReadProvinciasES() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile("provincias_es", 5, 52);
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
