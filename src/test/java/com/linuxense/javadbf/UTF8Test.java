package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UTF8Test {

	public UTF8Test () {
		super();
	}
	
	@Test
	@Ignore
	public void testUTF8() throws Exception {
		String testString = "Cộng hòa xã hội";
		DBFField fields[] = new DBFField[1];
		fields[0] = new DBFField();
		fields[0].setName("abc");
		fields[0].setType(DBFDataType.CHARACTER);
		fields[0].setFieldLength(10);

		DBFWriter writer = null;
		ByteArrayOutputStream fos = null;
		try {
			writer = new DBFWriter(Charset.forName("UTF-8"));
			writer.setFields(fields);
			Object rowData[] = new Object[1];
			
			rowData[0] = testString;
	
			writer.addRecord(rowData);
			fos = new ByteArrayOutputStream();
			writer.write(fos);
			
		}
		finally {
			DBFUtils.close(fos);
			DBFUtils.close(writer);
		}
		byte[] data = fos.toByteArray();
		
		
		DBFReader reader = null;
		try {
			reader = new DBFReader(new ByteArrayInputStream(data));
			Object[] rowObjects = reader.nextRecord();
			Assert.assertEquals(testString, rowObjects[0]);
		}
		finally {
			DBFUtils.close(reader);			
		}
	}
}
