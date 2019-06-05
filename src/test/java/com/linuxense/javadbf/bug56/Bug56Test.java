package com.linuxense.javadbf.bug56;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;

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
			System.out.println("" + rows + " rows and " + columns + " columns");
			for (int i = 0; i < columns; i++) {
				DBFField field = dbfReader.getField(i);
				System.out.println("" + i + " " + field.getName() + " " + field.getType() + " system:" + field.isSystem());
			}
			while ( ( row = dbfReader.nextRow() )  != null) {
				System.out.println("row=" + row);
				
				for (int i = 0; i < columns; i++) {
					System.out.println(row.getObject(i));
				}
			}
			
		}
		finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}
}
