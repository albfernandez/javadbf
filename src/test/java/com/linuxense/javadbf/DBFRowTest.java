package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.utils.DBFUtils;



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
			int rows = 0;
			
			while (dbfReader.nextRow() != null) {				
				rows++;
			}
			Assertions.assertEquals(52, rows);
		}
		finally {
			DBFUtils.close(dbfReader);
			DBFUtils.close(in);
		}
	}

}
