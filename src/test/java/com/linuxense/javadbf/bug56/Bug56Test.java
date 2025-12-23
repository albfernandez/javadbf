package com.linuxense.javadbf.bug56;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.utils.DBFUtils;

public class Bug56Test {

	public Bug56Test() {
		super();
	}
	
	@Test
	public void test() throws Exception {
		DBFReader dbfReader = null;
		InputStream in = null;
		File input = new File ("src/test/resources/bug-56-index-out-of-bounds/56-testdata.dbf");
		try {
			in = new BufferedInputStream(new FileInputStream(input));
			dbfReader = new DBFReader(in,Charset.forName("UTF-16"));
			DBFRow row = null;
			int columns = dbfReader.getFieldCount();
			int rows = dbfReader.getRecordCount();
			Assertions.assertEquals(1, rows);
			Assertions.assertEquals(13, columns);
			for (int i = 0; i < columns; i++) {
				DBFField field = dbfReader.getField(i);
				Assertions.assertFalse(field.isSystem());
			}
			while ( ( row = dbfReader.nextRow() )  != null) {
				
				for (int i = 0; i < columns; i++) {
					row.getObject(i);
				}
			}
			
		}
		finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}
}
