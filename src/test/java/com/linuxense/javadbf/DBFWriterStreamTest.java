/*

(C) Copyright 2017 Alberto Fernández <infjaf@gmail.com>

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.  If not, see <http://www.gnu.org/licenses/>.

*/
package com.linuxense.javadbf;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.linuxense.javadbf.mocks.NullOutputStream;

public class DBFWriterStreamTest {

	@Test
	public void testAllDataTypes() throws IOException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output); 
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", 10001.10, new Date(), true });
			writer.addRecord(new Object[] { 2, "Morfeo", 1000.0, new Date(), true });
			writer.addRecord(new Object[] { 2, "Smith", null, new Date(), false });
			writer.addRecord(new Object[] { null, null, null, null, null });
		}
		finally {
			DBFUtils.close(writer);
		}
		Assert.assertEquals(562L, output.getCount());
	}

	@Test(expected = DBFException.class)
	public void testFieldsCannotSetTwice() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.setFields(generateFields());
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsCannotBeNull() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(null);
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsCannotBeEmpty() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(new DBFField[0]);
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsCannotContainNull() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(new DBFField[] { null });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsSetBeforeRecordData() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.addRecord(new Object[] { 1, "John Smith", 1000.10, new Date(), false });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsValuesNotNull() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(null);
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testRecordLengthMustMastFieldLength() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try  {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[0]);
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testCheckCharacterType() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try  {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, 5, 10001.10, new Date(), true });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testCheckNumericType() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { "1", "Neo", 10001.10, new Date(), true });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testCheckFloatingPointType() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", "10001.10", new Date(), true });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testCheckDateType() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", 10001.10, "new Date()", true });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testCheckBooleanType() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.setFields(generateFields());
			writer.addRecord(new Object[] { 1, "Neo", 10001.10, new Date(), "true" });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	@Test(expected = DBFException.class)
	public void testFieldsLengthSameAsHeaders() throws DBFException {
		NullOutputStream output = new NullOutputStream();
		DBFWriter writer = null;
		try {
			writer = new DBFWriter(output);
			writer.addRecord(new Object[] { 1, "John Smith", 1000.10, new Date() });
		}
		finally {
			DBFUtils.close(writer);
		}
	}

	private DBFField[] generateFields() {
		DBFField[] fields = new DBFField[5];

		fields[0] = new DBFField();
		fields[0].setName("emp_code");
		fields[0].setType(DBFDataType.NUMERIC);
		fields[0].setLength(10);
		fields[0].setDecimalCount(0);

		fields[1] = new DBFField();
		fields[1].setName("emp_name");
		fields[1].setType(DBFDataType.CHARACTER);
		fields[1].setLength(60);

		fields[2] = new DBFField();
		fields[2].setName("salary");
		fields[2].setType(DBFDataType.FLOATING_POINT);
		fields[2].setLength(12);
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
