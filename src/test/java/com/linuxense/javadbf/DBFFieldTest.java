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

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class DBFFieldTest {

	public DBFFieldTest() {
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
	@Ignore("Now we allow unicode names")
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
	public void testsetLengthNotValidInDate() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.DATE);
		field.setLength(5);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInvalidZeroFieldLength() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setLength(0);
	}
	
	@Test
	public void testValidFieldLength() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setLength(10);
		assertEquals(DBFDataType.NUMERIC, field.getType());
		assertEquals(10, field.getLength());
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
		field.setLength(15);
		field.setDecimalCount(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testExcecsiveDecimalCount() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setLength(15);
		field.setDecimalCount(16);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testInvalidDecimalCountForNoNumeric() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.CHARACTER);
		field.setLength(15);
		field.setDecimalCount(5);
	}
}
