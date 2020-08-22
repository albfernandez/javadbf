package com.linuxense.javadbf.bug_dates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
		try {
			DBFWriter dbfWriter = new DBFWriter();
			DBFField field = new DBFField();
			field.setName("date"); // give a name to the field
			field.setType(DBFDataType.DATE); // and set its type
			dbfWriter.setFields(new DBFField[] {field});
			GregorianCalendar calendar = new GregorianCalendar(2,1,1);
			Timestamp date = new Timestamp(calendar.getTime().getTime());
			dbfWriter.addRecord(new Object[] {date});
			
			OutputStream os = new FileOutputStream(dbfFile);
			dbfWriter.write(os);
			dbfWriter.close();
			os.close();
			
			
			InputStream is = new FileInputStream(dbfFile);
			DBFReader reader = new DBFReader(is);
			Object[] o = reader.nextRecord();
			is.close();
			Object value = o[0]; 
			System.out.println(value);
			Assert.assertEquals(calendar.getTime(), value);
		}
		finally {
			dbfFile.delete();
		}
	}

}