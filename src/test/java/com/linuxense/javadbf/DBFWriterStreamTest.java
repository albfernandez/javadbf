package com.linuxense.javadbf;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.linuxsense.javadbf.mocks.NullOutputStream;

public class DBFWriterStreamTest {

	@Test
	public void testAllDataTypes() throws IOException {

		try (NullOutputStream output = new NullOutputStream()) {
			DBFWriter writer = new DBFWriter();
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", 10001.10, new Date(), true });
			writer.addRecord(new Object[] { 2, "Morfeo", 1000.0, new Date(), true });
			writer.addRecord(new Object[] { 2, "Smith", null, new Date(), false });
			writer.addRecord(new Object[] { null, null, null, null, null});
			writer.write(output);
			Assert.assertEquals(562L, output.getCount());
			
		}
	}
	@Test(expected=DBFException.class)
	public void testFieldsCannotSetTwice() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.setFields(generateFields());
	}
	
	@Test(expected=DBFException.class)
	public void testFieldsCannotBeNull() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(null);
	}
	@Test(expected=DBFException.class)
	public void testFieldsCannotBeEmpty() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(new DBFField[0]);
	}
	@Test(expected=DBFException.class)
	public void testFieldsCannotContainNull() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(new DBFField[]{null});
	}

	
	
	@Test(expected = DBFException.class)
	public void testFieldsSetBeforeRecordData() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.addRecord(new Object[] { 1, "John Smith", 1000.10, new Date(), false });
	}

	@Test(expected=DBFException.class)
	public void testFieldsValuesNotNull() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(null);
	}
	@Test(expected=DBFException.class)
	public void testRecordLengthMustMastFieldLength() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[0]);
	}
	@Test(expected=DBFException.class)
	public void testCheckCharacterType() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[] { 1, 5, 10001.10, new Date(), true });
	}
	@Test(expected=DBFException.class)
	public void testCheckNumericType() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[] { "1", "Neo", 10001.10, new Date(), true });
	}
	@Test(expected=DBFException.class)
	public void testCheckFloatingPointType() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[] { 1, "Neo", "10001.10", new Date(), true });
	}
	
	@Test(expected=DBFException.class)
	public void testCheckDateType() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[] { 1, "Neo", 10001.10, "new Date()", true });
	}
	@Test(expected=DBFException.class)
	public void testCheckBooleanType() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.setFields(generateFields());
		writer.addRecord(new Object[] { 1, "Neo", 10001.10, new Date(), "true" });
	}

	
	@Test(expected = DBFException.class)
	public void testFieldsLengthSameAsHeaders() throws DBFException {
		DBFWriter writer = new DBFWriter();
		writer.addRecord(new Object[] { 1, "John Smith", 1000.10, new Date()});
	}
	
	private DBFField[] generateFields() {
		DBFField[] fields = new DBFField[5];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.NUMERIC);
		fields[0].setFieldLength(10);
		fields[0].setDecimalCount(0);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setFieldLength(60);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.FLOATING_POINT);
		fields[2].setFieldLength(12);
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
