package com.linuxense.javadbf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

public class DBCDATASUSTest {

	public DBCDATASUSTest() {
		super();
	}
	
	@Test
	public void testSidsBDF() throws FileNotFoundException {
		System.out.println("---- DBF ---");;
		File file = new File("src/test/resources/dbc-files/sids.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("PERIMETER", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.NUMERIC, header.fieldArray[1].getType());
			
			DBFRow row = null;
			
			int count = 0;
			while ( (row = reader.nextRow()) != null) {	
				System.out.println(row.getString(0));
				count++;
			}
			Assert.assertEquals(100, count);

			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	

	
	@Test
	public void testSidsDBCDATASUSReader() throws FileNotFoundException {
		File file = new File("src/test/resources/dbc-files/sids.dbc");
		DBCDATASUSReader reader = null;
		try {
			System.out.println("---- DBC ---");;
			reader = new DBCDATASUSReader(new BufferedInputStream(new FileInputStream(file)));
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("PERIMETER", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.NUMERIC, header.fieldArray[1].getType());
			
			DBFRow row = null;
			
			int count = 0;
			while ( (row = reader.nextRow()) != null) {	
				System.out.println(row.getString(0));
				count++;
			}
			Assert.assertEquals(100, count);

			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	
	@Test
	public void testsStormDBF() throws FileNotFoundException {
		System.out.println("---- DBF ---");;
		File file = new File("src/test/resources/dbc-files/storm.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("COUNTYNAME", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[1].getType());
			
			DBFRow row = null;
			
			int count = 0;
			while ( (row = reader.nextRow()) != null) {	
				System.out.println(row.getString(1));
				count++;
			}
			Assert.assertEquals(100, count);

			
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	@Test
	public void testStormDBCDATASUSReader() throws FileNotFoundException {
		File file = new File("src/test/resources/dbc-files/storm.dbc");
		DBCDATASUSReader reader = null;
		try {
			System.out.println("---- DBC ---");;
			reader = new DBCDATASUSReader(new BufferedInputStream(new FileInputStream(file)));
			DBFHeader header = reader.getHeader();
			Assert.assertEquals("COUNTYNAME", header.fieldArray[1].getName());
			Assert.assertEquals(DBFDataType.CHARACTER, header.fieldArray[1].getType());
			
			DBFRow row = null;
			
			int count = 0;
			while ( (row = reader.nextRow()) != null) {	
				System.out.println(row.getString(1));
				count++;
			}
			Assert.assertEquals(100, count);

			
		}
		finally {
			DBFUtils.close(reader);
		}
	}

	
	@Test
	public void testExploder() throws IOException {
		File file = new File("src/test/resources/dbc-files/test-implode.pk");
		byte[] data = Files.readAllBytes(file.toPath());
		byte[] outputDatae = new byte[8096];
		int size = DBFExploder.pkexplode(data, DBFExploder.createInMemoryStorage(outputDatae), outputDatae.length);
		byte[] result = new byte[size];
		System.arraycopy(outputDatae, 0, result, 0, size);
		String s = new String(result);
		Assert.assertEquals("AIAIAIAIAIAIA", s);

	}
	
	
	@Test
	public void testExploderInputStream() throws IOException {
		File file = new File("src/test/resources/dbc-files/test-implode.pk");
		InputStream in = null;
		byte[] data = new byte[4096];
		try {
			in = new DBFExploderInputStream(new FileInputStream(file));
			int readed = in.read(data);
			String s = new String(data,0, readed);
			Assert.assertEquals("AIAIAIAIAIAIA", s);
		}
		finally {
			DBFUtils.close(in);;
		}
		
		
	}

}
