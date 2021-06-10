package com.linuxense.javadbf.bug93nullfield;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

public class Bug99NullFieldTest {

	public Bug99NullFieldTest() {
		super();
	}
	
	@Test
	public void testBug() throws FileNotFoundException {
		File testFile = new File("src/test/resources/bug93_null_field/testfile.dbf");

		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(testFile));
			reader.setTrimRightSpaces(false);
			DBFRow row = reader.nextRow();
			String value = row.getString("REQNAME");
			Assert.assertNotNull(value);
		}
		finally {
			DBFUtils.close(reader);
		}
	}
}
