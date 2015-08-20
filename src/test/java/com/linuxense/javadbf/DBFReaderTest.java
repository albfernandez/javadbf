package com.linuxense.javadbf;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.linuxsense.javadbf.mocks.FailInputStream;

public class DBFReaderTest {
	
	@Test(expected=DBFException.class)
	public void testFailStream() throws DBFException, IOException{
		InputStream is = null;
		try {
			is = new FailInputStream();
			DBFReader reader = new DBFReader(is);
			assertNull(reader);
		}
		finally {
			is.close();
		}
	}

}
