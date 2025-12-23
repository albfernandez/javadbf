package com.linuxense.javadbf.issues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFFieldNotFoundException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.utils.DBFUtils;

/**
 * Test class for issue 45
 * https://github.com/albfernandez/javadbf/issues/45
 *
 */
public class FieldNotFoundTest {

	public FieldNotFoundTest() {
		super();
	}
	
	@Test
	public void testFieldDefinitionNotFound() throws IOException {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
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
		});
	}
	
	@Test
	public void testFielInRowByIndexdNotFound() throws IOException {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
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
		});
	}
	
	@Test
	public void testFielInRowByNamedNotFound() throws IOException {		
		Assertions.assertThrows(DBFFieldNotFoundException.class, () -> {
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
		});
	}

}
