package com.linuxense.javadbf.issues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.linuxense.javadbf.DBFFieldNotFoundException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

/**
 * Test class for issue 45
 * https://github.com/albfernandez/javadbf/issues/45
 *
 */
public class FieldNotFoundTest {

	public FieldNotFoundTest() {
		super();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFieldDefinitionNotFound() throws IOException {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			reader.getField(0);
			reader.getField(255);
		}
		finally {
			DBFUtils.close(reader);
		}		
	}
	@Test(expected=IllegalArgumentException.class)
	public void testFielInRowByIndexdNotFound() throws IOException {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			DBFRow row = reader.nextRow();
			row.getString(155);
		}
		finally {
			DBFUtils.close(reader);
		}		
	}
	@Test(expected=DBFFieldNotFoundException.class)
	public void testFielInRowByNamedNotFound() throws IOException {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			DBFRow row = reader.nextRow();
			row.getString("NOT_EXIST");
		}
		finally {
			DBFUtils.close(reader);
		}		
	}

}
