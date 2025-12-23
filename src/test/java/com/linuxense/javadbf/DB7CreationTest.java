package com.linuxense.javadbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.linuxense.javadbf.utils.DBFStandardCharsets;
import com.linuxense.javadbf.utils.DBFUtils;

public class DB7CreationTest {

	public DB7CreationTest() {
		super();
	}
	
	@Test
	@Disabled
	public void simpleTest() throws Exception {
		File tmp = File.createTempFile("test", ".dbf");
    	DBFWriter writer = null;
    	try {
    		writer = new DBFWriter(new FileOutputStream(tmp), DBFStandardCharsets.ISO_8859_1, DBFFileFormat.ADVANCED);
    		Date d = new Date(-855067500000L);
    		writer.setFields(new DBFField[] {
    				new DBFField("NAME", DBFDataType.CHARACTER, 10),
    				new DBFField("DATE", DBFDataType.TIMESTAMP),
    				new DBFField("NAME2", DBFDataType.CHARACTER, 10)
    		});
    		writer.addRecord(new Object[] { "jimi" , d, "hendrix"});
    	}
    	finally {
    		DBFUtils.close(writer);
    	}
    	read(tmp);
	}
	
	@Test
	@Disabled
	public void testLongNames() throws Exception{
		
		File tmp = File.createTempFile("test", ".dbf");
    	DBFWriter writer = null;
    	try {
    		writer = new DBFWriter(new FileOutputStream(tmp), DBFStandardCharsets.ISO_8859_1, DBFFileFormat.ADVANCED);
    		Date d = new Date(-855067500000L);
    		writer.setFields(new DBFField[] {
    				new DBFField("MY_LONG_FIELD_NAME_FOR_TESTING", DBFDataType.CHARACTER, 10),
    				new DBFField("DATE", DBFDataType.TIMESTAMP),
    				new DBFField("NAME2", DBFDataType.CHARACTER, 10)
    		});
    		writer.addRecord(new Object[] { "jimi" , d, "hendrix"});
    	}
    	finally {
    		DBFUtils.close(writer);
    	}
    	read(tmp);
		
	}
	@Test
	@Disabled
	public void testLongChar() throws Exception{
		File tmp = File.createTempFile("test", ".dbf");
    	DBFWriter writer = null;
    	try {
    		writer = new DBFWriter(new FileOutputStream(tmp), DBFStandardCharsets.ISO_8859_1, DBFFileFormat.ADVANCED);
    		Date d = new Date(-855067500000L);
    		writer.setFields(new DBFField[] {
    				new DBFField("MY_LONG_FIELD_NAME_FOR_TESTING", DBFDataType.CHARACTER, 3000),
    				new DBFField("DATE", DBFDataType.TIMESTAMP),
    				new DBFField("NAME2", DBFDataType.CHARACTER, 10)
    		});
    		writer.addRecord(new Object[] { "jimi" , d, "hendrix"});
    	}
    	finally {
    		DBFUtils.close(writer);
    	}
    	read(tmp);
	}
	
	private void read(File f) throws IOException {
    	DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(f));

            int numberOfFields = reader.getFieldCount();
            Assertions.assertEquals(3, numberOfFields);

            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                Assertions.assertNotNull(field.getName());
            }

            Object[] rowObject;
            int countedRows = 0;
            while ((rowObject = reader.nextRecord()) != null) {
                Assertions.assertEquals(numberOfFields, rowObject.length);
                Assertions.assertTrue(rowObject[1] instanceof java.util.Date);

                Calendar calendar = new GregorianCalendar();
                calendar.set(1942,Calendar.NOVEMBER,27,10,15,0);
                calendar.clear(Calendar.MILLISECOND);

                Assertions.assertTrue(rowObject[0].toString().trim().equals("jimi"));
                Assertions.assertEquals(calendar.getTime(),(Date)rowObject[1]);
                Assertions.assertTrue(rowObject[2].toString().trim().equals("hendrix"));

                countedRows++;
            }
            Assertions.assertEquals(1, countedRows);
        }
        finally {
        	DBFUtils.close(reader);
        }
    }
	
}
