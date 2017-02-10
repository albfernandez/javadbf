package com.linuxense.javadbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class DBFReaderPrintTest {
	
	@Test
	public void printFile() throws Exception {
		File file = new File("src/test/resources/books.dbf");
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			DBFReader reader = new DBFReader(is);
			System.out.println(reader.getCharset());
			Object[] row = null;
			while ( (row = reader.nextRecord()) != null ){
				for (Object o: row) {
					System.out.print(o+";");
				}
				System.out.println("");
			}
			
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
