package com.linuxense.javadbf.bug94_error_f5;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.ReadDBFAssert;
import com.linuxense.javadbf.testutils.FileUtils;

public class Bug94ErrorF5Test {
	
	private File testFile = new File("src/test/resources/fixtures/dbase_f5.dbf");
	
	public Bug94ErrorF5Test()  {
		super();
	}
	
	@Test
	public void testSimpleRead() throws DBFException, IOException {
		ReadDBFAssert.testReadDBFFile(testFile, 60, 975, true);
	}
	
	@Test
	public void testWrite() throws Exception {
		
		File tmpFile = Files.createTempFile("tmp", ".dbf").toFile();
		FileUtils.copyFile(testFile, tmpFile);
		DBFWriter dbfWriter = new DBFWriter(tmpFile);
		dbfWriter.close();
		ReadDBFAssert.testReadDBFFile(tmpFile, 60, 975, true);
	}
	@Test
	public void providedExample() throws IOException {
		 DataInputStream dataInputStream = null;
		 try { 
		 	dataInputStream = new DataInputStream(this.getClass().getResourceAsStream("/fixtures/dbase_f5.dbf"));
		    byte b = dataInputStream.readByte();
		    // Bad: byte vs int
//		    Assert.assertTrue(b == 0xF5);
		    // Correct: byte vs byte
		    Assert.assertTrue(b == ((byte) 0xF5));
		    dataInputStream.close();
		 }
		 finally {
			 DBFUtils.close(dataInputStream);
		 }
	}
}
