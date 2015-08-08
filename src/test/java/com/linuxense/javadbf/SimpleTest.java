package com.linuxense.javadbf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

	@Test
	public void testContinents() throws DBFException, IOException {
		testSize("continents", 1, 7);
	}
	
	@Test
	public void testBooks() throws DBFException, IOException {
		testSize("books", 11, 10);
	}
	
	@Test
	public void testCountries() throws DBFException, IOException {
		testSize("countries", 29, 177);
	}

	private void testSize(String fileName, int columns, int rows) throws DBFException, IOException {
		InputStream inputStream = null;
		
		try {
			inputStream  = new FileInputStream("src/test/resources/" + fileName + ".dbf");
		      DBFReader reader = new DBFReader( inputStream); 
		      
		      int numberOfFields = reader.getFieldCount();
		      Assert.assertEquals(columns, numberOfFields);
		      for( int i=0; i<numberOfFields; i++) {
			        DBFField field = reader.getField(i);
			        Assert.assertNotNull(field.getName());
			   }
		      Object[] rowObject;
		      int countedRows = 0;
		      while( (rowObject = reader.nextRecord()) != null) {
		    	  Assert.assertEquals(numberOfFields, rowObject.length);
		    	  countedRows++;
		      }
		      Assert.assertEquals(rows, countedRows);
		      
		}
		finally {
			inputStream.close();
		}		
		
	}
}
