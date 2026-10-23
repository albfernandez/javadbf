package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class DBFRowLocaleTest {
	
	public DBFRowLocaleTest() {
		super();
	}
	
	@Test
	public void testFieldNamesIgnoreDefaultLocale() throws Exception {
		Locale original = Locale.getDefault();
		try {
			Locale.setDefault(Locale.forLanguageTag("tr-TR"));
			byte[] dbf = createDBF();
			DBFReader reader = null;
			try {
				reader = new DBFReader(new ByteArrayInputStream(dbf));
				DBFRow row = reader.nextRow();
				Assert.assertEquals("value", row.getString("id"));
				Assert.assertEquals("value", row.getString("ID"));
			}
			finally {
				reader.close();
			}
		} finally {
			Locale.setDefault(original);
		}
	}

	@Test
	public void testFieldNamesAfterDefaultLocaleChanges() throws Exception {
		Locale original = Locale.getDefault();
		try {
			Locale.setDefault(Locale.ENGLISH);
			DBFReader reader = null;
			try {
				reader = new DBFReader(new ByteArrayInputStream(createDBF()));
				DBFRow row = reader.nextRow();
				Locale.setDefault(Locale.forLanguageTag("tr-TR"));
				Assert.assertEquals("value", row.getString("ID"));
			}
			finally {
				reader.close();
			}
		} finally {
			Locale.setDefault(original);
		}
	}

	private byte[] createDBF() throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DBFField field = new DBFField();
		field.setName("ID");
		field.setType(DBFDataType.CHARACTER);
		field.setLength(10);
		DBFWriter writer = null;	 
		try {
			writer = new DBFWriter(output);
			writer.setFields(new DBFField[] { field });
			writer.addRecord(new Object[] { "value" });
		}
		finally {
			writer.close();
		}
		return output.toByteArray();
	}
}
