package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class BufferedInputStreamTest {

	public BufferedInputStreamTest() {
		super();
	}
	
	@Test
	public void testBuffered()  throws IOException {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream("src/test/resources/books.dbf"));
			ReadDBFAssert.testReadDBFFile(is, 11, 10);
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
