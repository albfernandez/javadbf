package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;



public class CopyExampleTest {

	public CopyExampleTest() {
		super();
	}
	
	@Test
	public void test() throws Exception {
		
		File inputFile = new File("src/test/resources/continents.dbf");
		
		DBFReader reader = null;
		DBFWriter writer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int recordCount = 0;
		int recordCountResultFile = 0;
		try {
			reader = new DBFReader(new FileInputStream(inputFile));
			writer = new DBFWriter(baos, StandardCharsets.UTF_8);
			
			// Copy fields definition from reader to writer
			DBFField[] fieldsDefinition = new DBFField[reader.getFieldCount()];
			for (int i = 0; i < fieldsDefinition.length; i++) {
				fieldsDefinition[i] = reader.getField(i);
			}
			writer.setFields(fieldsDefinition);
			
			// Copy data from reader to writer.
			recordCount = reader.getRecordCount();
			Object[] row = null;
			while ((row = reader.nextRecord()) != null) {
				writer.addRecord(row);
			}
		}
		finally {
			DBFUtils.close(reader);
			DBFUtils.close(writer);
		}
		
		DBFReader check = null;
		try {
			check = new DBFReader(new ByteArrayInputStream(baos.toByteArray()));
			recordCountResultFile = check.getRecordCount();
		}
		finally {
			DBFUtils.close(check);
		}
		Assert.assertEquals(recordCount, recordCountResultFile);
		
	}
}
