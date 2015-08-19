package com.linuxense.javadbf;

import org.junit.Test;

public class DBFieldTest {

	public DBFieldTest() {
		super();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidNullName() {
		DBFField field = new DBFField();
		field.setName(null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidEmptyName() {
		DBFField field = new DBFField();
		field.setName("");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidLongName() {
		DBFField field = new DBFField();
		field.setName("12345678901");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidNotAsciiName() {
		DBFField field = new DBFField();
		field.setName("aááa");
	}
	@Test
	public void testValidNames() {
		DBFField field = new DBFField();
		field.setName("valid");
		field.setName("demo");
		field.setName("    ");
		field.setName(" 1 ");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testSetFieldLengthNotValidInDate() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.DATE);
		field.setFieldLength(5);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInvalidZeroFieldLength() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setFieldLength(0);
	}
	
	@Test
	public void testValidFieldLength() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setFieldLength(10);
	}
}
