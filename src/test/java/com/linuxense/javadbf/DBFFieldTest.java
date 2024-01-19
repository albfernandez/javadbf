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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DBFFieldTest {

	public DBFFieldTest() {
		super();
	}
	
	@Test
	public void testInvalidNullName() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setName(null);
		});
	}
	
	@Test
	public void testInvalidEmptyName() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setName("");
		});
	}
	
	@Test
	@Disabled("now supporting length")
	public void testInvalidLongName() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setName("12345678901");
		});
	}
	
	@Test
	@Disabled("Now we allow unicode names")
	public void testInvalidNotAsciiName() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setName("aááa");
		});
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
	
	@Test
	public void testSetLengthNotValidInDate() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.DATE);
			field.setLength(5);
		});
	}
	
	@Test
	public void testInvalidZeroFieldLength() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.NUMERIC);
			field.setLength(0);
		});
	}
	
	@Test
	public void testValidFieldLength() {
		DBFField field = new DBFField();
		field.setType(DBFDataType.NUMERIC);
		field.setLength(10);
		assertEquals(DBFDataType.NUMERIC, field.getType());
		assertEquals(10, field.getLength());
	}
	
	@Test
	public void testTypeNotWriteSupport() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.MEMO);
		});
	}
	
	@Test
	public void testNegativeDecimalCount() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.NUMERIC);
			field.setLength(15);
			field.setDecimalCount(-1);
		});
	}
	@Test
	public void testExcecsiveDecimalCount() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.NUMERIC);
			field.setLength(15);
			field.setDecimalCount(16);
		});
	}
	
	@Test
	public void testInvalidDecimalCountForNoNumeric() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			DBFField field = new DBFField();
			field.setType(DBFDataType.CHARACTER);
			field.setLength(15);
			field.setDecimalCount(5);
		});
	}
}
