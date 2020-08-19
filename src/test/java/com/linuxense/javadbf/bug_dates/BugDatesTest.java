package com.linuxense.javadbf.bug_dates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

public class BugDatesTest {
	
	public BugDatesTest() {
		super();
	}
	
	@Test
	public void test1() throws Exception {
		File dbfFile = File.createTempFile("dates", ".dbf");
		DBFWriter dbfWriter = new DBFWriter(new FileOutputStream(dbfFile));
		DBFField field = new DBFField();
		field.setName("date"); // give a name to the field
		field.setType(DBFDataType.DATE); // and set its type
		dbfWriter.setFields(new DBFField[] {field});
		GregorianCalendar calendar = new GregorianCalendar(2,1,1);
		Timestamp date = new Timestamp(calendar.getTime().getTime());
		dbfWriter.addRecord(new Object[] {date});
		dbfWriter.close();
		
		DBFReader reader = new DBFReader(new FileInputStream(dbfFile));
		Object[] o = reader.nextRecord();
		Object value = o[0]; 
		reader.close();		
		System.out.println(value);
		Assert.assertEquals(calendar.getTime(), value);
	}

}
