package com.linuxense.javadbf.bug117buffered;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import com.linuxense.javadbf.mocks.NullOutputStream;
import com.linuxense.javadbf.utils.DBFUtils;

public class Bug107UseBufferedStreams {
	
	public Bug107UseBufferedStreams() {
		super();
	}
	
	@Test
	public void testBufferedInputStream() throws Exception {
		File file = new File("src/test/resources/books.dbf");
		DBFReader reader = null;
		try {
			reader = new DBFReader(new BufferedInputStream(new FileInputStream(file)));
		
			String expected = "1996/7/25\n" +
				"Total records: 10\n" + 
				"Header length: 648\n" + 
				"Columns:\n" + 
				"BOOK_ID\n" + 
				"TITLE\n" + 
				"TOPIC_ID\n" + 
				"COPYRIGHT_\n" + 
				"ISBN_NUMBE\n" + 
				"PUBLISHER_\n" + 
				"PURCHASE_P\n" + 
				"COVERTYPE\n" + 
				"DATE_PURCH\n" + 
				"PAGES\n" + 
				"NOTES\n";
			Assertions.assertEquals(expected, reader.toString());
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	
	
	
	@Test
	public void testBufferedOutputStream() {
		NullOutputStream output = new NullOutputStream();
		BufferedOutputStream buffered = new BufferedOutputStream(output);
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(buffered); 
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", 10001.10, new Date(), true });
			writer.addRecord(new Object[] { 2, "Morfeo", 1000.0, new Date(), true });
			writer.addRecord(new Object[] { 2, "Smith", null, new Date(), false });
			writer.addRecord(new Object[] { null, null, null, null, null });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assertions.assertEquals(562L, output.getCount());
	}
	
	private DBFField[] generateFields() {
		DBFField[] fields = new DBFField[5];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.NUMERIC);
		fields[0].setLength(10);
		fields[0].setDecimalCount(0);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setLength(60);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.FLOATING_POINT);
		fields[2].setLength(12);
		fields[2].setDecimalCount(2);

		fields[3] = new DBFField();
		fields[3].setName("hire_date");
		fields[3].setType(DBFDataType.DATE);

		fields[4] = new DBFField();
		fields[4].setName("human");
		fields[4].setType(DBFDataType.LOGICAL);

		return fields;
	}

}
