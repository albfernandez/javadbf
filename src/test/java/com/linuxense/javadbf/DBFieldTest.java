package com.linuxense.javadbf;

import static org.junit.Assert.*;

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
		assertEquals("valid", field.getName());
		field.setName("demo");
		assertEquals("demo", field.getName());
		field.setName("    ");
		assertEquals("    ", field.getName());
		field.setName(" 1 ");
		assertEquals(" 1 ", field.getName());
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
		assertEquals(DBFDataType.NUMERIC, field.getType());
		assertEquals(10, field.getFieldLength());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testTypeNotWriteSupport() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.MEMO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeDecimalCount() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setFieldLength(15);
		field.setDecimalCount(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testExcecsiveDecimalCount() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setFieldLength(15);
		field.setDecimalCount(16);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInvalidDecimalCountForNoNumeric() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.CHARACTER);
		field.setFieldLength(15);
		field.setDecimalCount(5);
	}
}
