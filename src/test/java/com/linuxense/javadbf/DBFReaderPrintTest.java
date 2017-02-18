package com.linuxense.javadbf;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.linuxense.javadbf.testutils.DbfToTxtTest;

public class DBFReaderPrintTest {
	
	@Test
	public void printFile() throws Exception {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(file));
			DbfToTxtTest.export(reader, File.createTempFile("javadbf-test", ".txt"));
			
		}
		finally {
			DBFUtils.close(reader);
		}
	}

}
