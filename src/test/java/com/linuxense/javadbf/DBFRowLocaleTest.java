package com.linuxense.javadbf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DBFRowLocaleTest {
	@Test
	public void testFieldNamesIgnoreDefaultLocale() throws Exception {
		Locale original = Locale.getDefault();
		try {
			Locale.setDefault(Locale.forLanguageTag("tr-TR"));
			byte[] dbf = createDBF();
			try (DBFReader reader = new DBFReader(new ByteArrayInputStream(dbf))) {
				DBFRow row = reader.nextRow();
				Assertions.assertEquals("value", row.getString("id"));
				Assertions.assertEquals("value", row.getString("ID"));
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
			try (DBFReader reader = new DBFReader(new ByteArrayInputStream(createDBF()))) {
				DBFRow row = reader.nextRow();
				Locale.setDefault(Locale.forLanguageTag("tr-TR"));
				Assertions.assertEquals("value", row.getString("ID"));
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
		try (DBFWriter writer = new DBFWriter(output)) {
			writer.setFields(new DBFField[] { field });
			writer.addRecord(new Object[] { "value" });
		}
		return output.toByteArray();
	}
}
