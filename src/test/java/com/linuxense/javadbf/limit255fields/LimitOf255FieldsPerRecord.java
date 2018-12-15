package com.linuxense.javadbf.limit255fields;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFWriter;

public class LimitOf255FieldsPerRecord {

	private static int NUMBER_OF_FIELDS_TO_CREATE = 315;
	private static int NUMBER_OF_RECORDS_TO_CREATE = 5000;
	
	public LimitOf255FieldsPerRecord() {
		super();
	}
	
	@Test
	public void testLimit() throws IOException {
		byte[] data = writeFile();
		Assert.assertNotNull(data);
		Assert.assertTrue(data.length > 0);
		try (DBFReader reader = new DBFReader(new ByteArrayInputStream(data))) {
			Assert.assertEquals(NUMBER_OF_FIELDS_TO_CREATE, reader.getFieldCount());
			Assert.assertEquals(NUMBER_OF_RECORDS_TO_CREATE, reader.getRecordCount());
			DBFRow row;
			int countRows = 0;
			while ((row = reader.nextRow()) != null) {
				for (int i = 0; i < reader.getFieldCount(); i++) {
					Assert.assertEquals("data_" + i , row.getString(i));
				}
				countRows++;
			}
			Assert.assertEquals(NUMBER_OF_RECORDS_TO_CREATE, countRows);
		}
	}
	
	
	private byte[] writeFile() throws IOException {
		DBFField[] fields = new DBFField[NUMBER_OF_FIELDS_TO_CREATE];
		for (int i = 0; i < fields.length; i++) {
			DBFField field = new DBFField("field_" + i, DBFDataType.CHARACTER);
			field.setLength(15);
			fields[i] = field;
		}
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			DBFWriter writer = new DBFWriter(baos);
			writer.setFields(fields);
			Object[] record = new Object[NUMBER_OF_FIELDS_TO_CREATE];
			for (int i = 0; i < record.length; i++) {
				record[i] = "data_" + i;
			}
			for (int i = 0; i < NUMBER_OF_RECORDS_TO_CREATE; i++) {
				writer.addRecord(record);
			}
			writer.close();
			return baos.toByteArray();
		}
	}
	

}
