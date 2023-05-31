package com.linuxense.javadbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DB7CreationTest {

	public DB7CreationTest() {
		super();
	}
	
	@Test
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
            Assert.assertEquals(3, numberOfFields);

            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                Assert.assertNotNull(field.getName());
            }

            Object[] rowObject;
            int countedRows = 0;
            while ((rowObject = reader.nextRecord()) != null) {
                Assert.assertEquals(numberOfFields, rowObject.length);
                Assert.assertTrue(rowObject[1] instanceof java.util.Date);

                Calendar calendar = new GregorianCalendar();
                calendar.set(1942,Calendar.NOVEMBER,27,10,15,0);
                calendar.clear(Calendar.MILLISECOND);

                Assert.assertTrue(rowObject[0].toString().trim().equals("jimi"));
                Assert.assertEquals(calendar.getTime(),(Date)rowObject[1]);
                Assert.assertTrue(rowObject[2].toString().trim().equals("hendrix"));

                countedRows++;
            }
            Assert.assertEquals(1, countedRows);
        }
        finally {
        	DBFUtils.close(reader);
        }
    }
	
}
