package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;



public class DBFRowTest {

	public DBFRowTest() {
		super();
	}
	
	@Test
	public void testBasicDBFRow() throws Exception {
		File dbfFile = new File("src/test/resources/provincias_es.dbf");
		
		DBFReader dbfReader = null;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(dbfFile));
			dbfReader = new DBFReader(in);
			DBFRow row = null;
			int rows = 0;
			for (int i = 0; i < dbfReader.getFieldCount(); i++) {
				System.out.println(dbfReader.getField(i));
			}
			while ((row = dbfReader.nextRow()) != null) {				
				rows++;
				System.out.println(row.getString("codigo") + row.getString("Texto"));
			}
			Assert.assertEquals(52, rows);
		}
		finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}

}
