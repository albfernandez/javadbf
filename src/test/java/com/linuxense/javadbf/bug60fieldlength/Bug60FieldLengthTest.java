package com.linuxense.javadbf.bug60fieldlength;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.testutils.DbfToTxtTest;



public class Bug60FieldLengthTest {
	File testFile = new File("src/test/resources/bug-60-fieldlength/060.dbf");
	public Bug60FieldLengthTest() {
		super();
	}
	
	@Test
	public void test1() throws Exception  {
		File testFile = new File("src/test/resources/bug-60-fieldlength/060.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(testFile));
			reader.setTrimRightSpaces(false);
			DBFRow row = reader.nextRow();
			String result = row.getString("DESCRIPTIO");
			Assert.assertEquals(100, result.length());
		}
		finally {
			DBFUtils.close(reader);
		}
		
	}
	
	
	@Test
	public void testPrint() throws FileNotFoundException {
		File testFile = new File("src/test/resources/bug-60-fieldlength/060.dbf");
		DbfToTxtTest.writeToConsole(testFile);
	}
	
}
