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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;




public class UtilsTest {

	private static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	private static final Charset UTF8 = StandardCharsets.UTF_8;
	public UtilsTest () {
		super();
	}
	@Test
	public void testIsPureAscii() {
		assertTrue(DBFUtils.isPureAscii("abcd"));
		assertFalse(DBFUtils.isPureAscii("á"));
		assertFalse(DBFUtils.isPureAscii("ñ"));
		assertTrue(DBFUtils.isPureAscii(""));
		assertTrue(DBFUtils.isPureAscii((String) null));
	}
	@Test
	public void testToBoolean() {
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 't'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'T'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'y'));
		assertEquals(Boolean.TRUE, DBFUtils.toBoolean((byte) 'Y'));
		
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'f'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'F'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'n'));
		assertEquals(Boolean.FALSE, DBFUtils.toBoolean((byte) 'N'));
		
		assertNull(DBFUtils.toBoolean((byte) '?'));
		
	}
	
	@Test 
	public void testContains () {
		assertTrue(DBFUtils.contains("test?test".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains("testtest".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains("".getBytes(), (byte) '?'));
		assertFalse(DBFUtils.contains(null, (byte)'?'));
	}
	@Test
	public void doubleFormating() {
		Assert.assertEquals(
			" 0.00", 
			new String(DBFUtils.doubleFormating(new Double(0.0), Charset.defaultCharset(), 5, 2))
		);
		
		Assert.assertEquals(
				"10.00", 
				new String(DBFUtils.doubleFormating(new Double(10.0), Charset.defaultCharset(), 5, 2))
			);
		Assert.assertEquals(
				" 5.05", 
				new String(DBFUtils.doubleFormating(new Double(5.05), Charset.defaultCharset(), 5, 2))
			);
	}
	@Test
	public void testLittleEndian() {
		// TODO
	}
	@Test
	public void testreadLittleEndianInt() {
		// TODO
	}
	@Test
	public void testReadLittleEndianShort() {
		// TODO
	}
	@Test
	public void testTextPadding()  {
		assertEquals(
			"abc       ",
			new String(DBFUtils.textPadding("abc", ISO_8859_1, 10), ISO_8859_1)
		);
		
		assertEquals(
			"a",
			new String(DBFUtils.textPadding("abc", ISO_8859_1, 1), ISO_8859_1)
		);
		assertEquals(
			"001",
			new String(DBFUtils.textPadding("1", ISO_8859_1, 3, DBFAlignment.RIGHT, (byte) '0'), ISO_8859_1)
		);
		
		//TODO Test extreme cases (null, negative, etc)

	}
	
	@Test
	public void testTextPaddingUTF8() {
		assertEquals(
				"Simón    ",
				new String(DBFUtils.textPadding("Simón", UTF8, 10), UTF8)
		);		
	}

	public void testInvalidUT8Padding() {
		assertEquals(
				"est ",
				new String(DBFUtils.textPadding("está", UTF8, 4), UTF8));
	}
	@Test
	public void testRemoveSpaces() {
		assertEquals("123", new String(DBFUtils.removeSpaces("   123".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("   123   ".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("123   ".getBytes())));
		assertEquals("123", new String(DBFUtils.removeSpaces("123".getBytes())));
		assertEquals("", new String(DBFUtils.removeSpaces("".getBytes())));
	}

	@Test
	public void testRemoveNullBytes() {
		byte[] byteArray = {0x00, 0x31, 0x00, 0x32, 0x33, 0x00}; // will nulls
		assertEquals("123", new String(DBFUtils.removeNullBytes(byteArray)));
	}


	@Test
	public void testTrimRightSpaces() {
		assertEquals("123", new String(DBFUtils.trimRightSpaces("123".getBytes())));
		assertEquals("123", new String(DBFUtils.trimRightSpaces("123  ".getBytes())));
		assertEquals("  123", new String(DBFUtils.trimRightSpaces("  123  ".getBytes())));
		assertEquals("  123", new String(DBFUtils.trimRightSpaces("  123".getBytes())));
		assertEquals("", new String(DBFUtils.trimRightSpaces("    ".getBytes())));
		assertEquals("", new String(DBFUtils.trimRightSpaces(null)));
		
	}

}
