package com.linuxense.javadbf.bug98;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.utils.DBFUtils;

public class ReadMemoAsBinaryTest {

	public ReadMemoAsBinaryTest() {
		super();
	}
	
	@Test
	public void testReadMemoAsBinary() throws Exception {
		File file = new File("src/test/resources/fixtures/dbase_8b.dbf");
		DBFReader reader  = null;
		try {
			reader = new DBFReader( new BufferedInputStream(new FileInputStream(file)));
			reader.setMemoFile(new File("src/test/resources/fixtures/dbase_8b.dbt"));
			reader.setReadMemoFieldsAsBinary("MEMO");
			DBFRow row = null;
			while ((row = reader.nextRow()) != null) {
				byte[] data = row.getBytes("MEMO");
				Assertions.assertTrue(data == null || data instanceof byte[]);
			}
			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
			
}
